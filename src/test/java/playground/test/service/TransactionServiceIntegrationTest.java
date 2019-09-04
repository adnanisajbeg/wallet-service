package playground.test.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import playground.test.exceptions.InvalidInputException;
import playground.test.model.*;
import playground.test.repository.PlayerRepository;
import playground.test.repository.TransactionHistoryRepository;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static playground.test.utils.PlayerUtils.createPlayerWithRandomUsername;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TransactionServiceIntegrationTest {
    @Autowired
    private TransactionHistoryRepository transactionHistoryRepository;

    @Autowired
    private TransactionHistoryService transactionHistoryService;

    @Autowired
    private PlayerRepository playerRepository;

    @Test
    public void when_saving_new_transaction_status_is_requested() {
        // Given
        Player player = playerRepository.save(new Player(createPlayerWithRandomUsername()));
        UUID uuid = UUID.randomUUID();

        // When
        transactionHistoryService.addHistory(uuid, player, Action.CREDIT, 101L);

        // Then
        TransactionHistory transactionHistory = transactionHistoryRepository.findByUuid(uuid);
        assertThat(transactionHistory).isNotNull();
        assertThat(transactionHistory.getAction()).isNotNull().isEqualTo(Action.CREDIT);
        assertThat(transactionHistory.getStatus()).isNotNull().isEqualTo(Status.REQUESTED);
        assertThat(transactionHistory.getAmount()).isEqualTo(101L);
    }

    @Test
    public void when_updating_to_successful_status_record_will_be_correctly_updated() {
        // Given
        Player player = playerRepository.save(new Player(createPlayerWithRandomUsername()));
        UUID uuid = UUID.randomUUID();
        transactionHistoryService.addHistory(uuid, player, Action.DEBIT, 1223L);

        // When
        transactionHistoryService.success(uuid);

        // Then
        TransactionHistory transactionHistory = transactionHistoryRepository.findByUuid(uuid);
        assertThat(transactionHistory).isNotNull();
        assertThat(transactionHistory.getAction()).isNotNull().isEqualTo(Action.DEBIT);
        assertThat(transactionHistory.getStatus()).isNotNull().isEqualTo(Status.SUCCESS);
        assertThat(transactionHistory.getAmount()).isEqualTo(1223L);
    }

    @Test
    public void when_updating_to_failed_status_record_will_be_correctly_updated() {
        // Given
        Player player = playerRepository.save(new Player(createPlayerWithRandomUsername()));
        UUID uuid = UUID.randomUUID();
        transactionHistoryService.addHistory(uuid, player, Action.DEBIT, 1223L);

        // When
        transactionHistoryService.failed(uuid);

        // Then
        TransactionHistory transactionHistory = transactionHistoryRepository.findByUuid(uuid);
        assertThat(transactionHistory).isNotNull();
        assertThat(transactionHistory.getAction()).isNotNull().isEqualTo(Action.DEBIT);
        assertThat(transactionHistory.getStatus()).isNotNull().isEqualTo(Status.FAILED);
        assertThat(transactionHistory.getAmount()).isEqualTo(1223L);
    }

    @Test(expected = InvalidInputException.class)
    public void when_asked_for_history_for_non_existing_player_exception_is_thrown() {
        // When
        List<TransactionHistoryDTO> history = transactionHistoryService.getHistory(createPlayerWithRandomUsername().getUsername());
    }

    @Test
    public void when_requesting_history_for_existing_usser_correct_records_is_return() {
        // Given
        PlayerDTO playerDTO = createPlayerWithRandomUsername();
        Player player = playerRepository.save(new Player(playerDTO));
        UUID uuid1 = UUID.randomUUID();
        UUID uuid2 = UUID.randomUUID();
        transactionHistoryService.addHistory(uuid1, player, Action.CREDIT, 54545L);
        transactionHistoryService.addHistory(uuid2, player, Action.DEBIT, 344L);

        // When
        List<TransactionHistoryDTO> history = transactionHistoryService.getHistory(player.getUsername());

        // Then
        assertThat(history)
                .isNotNull()
                .isNotEmpty()
                .hasSize(2)
                .contains(new TransactionHistoryDTO(uuid1, Action.CREDIT, 54545L, Status.REQUESTED),
                        new TransactionHistoryDTO(uuid2, Action.DEBIT, 344L, Status.REQUESTED));
    }
}
