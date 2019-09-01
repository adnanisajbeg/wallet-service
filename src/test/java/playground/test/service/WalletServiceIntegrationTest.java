package playground.test.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import playground.test.exceptions.InvalidInputException;
import playground.test.exceptions.PlayerNotFoundException;
import playground.test.model.CreditSubmitDTO;
import playground.test.model.Player;
import playground.test.model.PlayerDTO;
import playground.test.repository.PlayerRepository;

import java.util.UUID;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static playground.test.utils.PlayerUtils.createPlayerWithRandomUsername;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class WalletServiceIntegrationTest {
    @Autowired
    PlayerRepository playerRepository;

    @Autowired
    WalletService walletService;

    @Test
    public void when_adding_credit_to_player_correct_value_will_be_set_in_record() {
        // Given
        Player player = playerRepository.save(new Player(createPlayerWithRandomUsername()));
        assertThat(player).isNotNull();
        UUID id = UUID.randomUUID();

        // When
        walletService.addCreditForPlayer(new CreditSubmitDTO(id, player.getUsername(), 15L));

        // Then
        Player playerStatus = playerRepository.findByUsername(player.getUsername());
        assertThat(playerStatus).isNotNull();
        assertThat(playerStatus.getBalance()).isEqualTo(15L);
    }

    @Test(expected = PlayerNotFoundException.class)
    public void when_adding_credit_to_non_existing_player_exception_is_thrown() {
        // Given
        PlayerDTO player = createPlayerWithRandomUsername();
        assertThat(player).isNotNull();
        UUID id = UUID.randomUUID();

        // When
        walletService.addCreditForPlayer(new CreditSubmitDTO(id, player.getUsername(), 15L));
    }

    @Test
    public void when_adding_multiple_credits_to_player_correct_sum_will_be_in_db() {
        // Given
        Player player = playerRepository.save(new Player(createPlayerWithRandomUsername()));
        assertThat(player).isNotNull();
        UUID id = UUID.randomUUID();

        // When
        walletService.addCreditForPlayer(new CreditSubmitDTO(id, player.getUsername(), 15L));
        walletService.addCreditForPlayer(new CreditSubmitDTO(id, player.getUsername(), 11L));
        walletService.addCreditForPlayer(new CreditSubmitDTO(id, player.getUsername(), 23L));
        walletService.addCreditForPlayer(new CreditSubmitDTO(id, player.getUsername(), 56L));

        // Then
        Player playerStatus = playerRepository.findByUsername(player.getUsername());
        assertThat(playerStatus).isNotNull();
        assertThat(playerStatus.getBalance()).isEqualTo(15L + 11L + 23L + 56L);
    }

    @Test(expected = InvalidInputException.class)
    public void when_adding_credit_to_null_username_invalidDataException_is_thrown() {
        // Given
        UUID id = UUID.randomUUID();

        // When
        walletService.addCreditForPlayer(new CreditSubmitDTO(id, null, 15L));
    }

    @Test(expected = InvalidInputException.class)
    public void when_adding_negative_credit_to_existing_player_invalidDataException_is_thrown() {
        // Given
        Player player = playerRepository.save(new Player(createPlayerWithRandomUsername()));
        assertThat(player).isNotNull();
        UUID id = UUID.randomUUID();

        // When
        walletService.addCreditForPlayer(new CreditSubmitDTO(id, player.getUsername(), -15L));
    }

}