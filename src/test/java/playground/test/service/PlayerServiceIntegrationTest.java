package playground.test.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import playground.test.model.Player;
import playground.test.model.PlayerDTO;

import static org.assertj.core.api.Assertions.assertThat;
import static playground.test.utils.PlayerUtils.createPlayerWithRandomUsername;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PlayerServiceIntegrationTest {
    @Autowired
    PlayerService playerService;

    @Test
    public void saving_correct_player() {
        // Given
        PlayerDTO playerDTO = createPlayerWithRandomUsername();

        // When
        Player player = playerService.addNewPlayer(playerDTO);

        // Then
        assertThat(player).isNotNull();
        assertThat(player.getBalance()).isEqualTo(0);

        assertThat(player.getUsername())
                .isNotNull()
                .isNotBlank()
                .isEqualTo(playerDTO.getUsername());
    }

    @Test
    public void when_adding_new_player_saved_object_has_an_id() {
        // Given
        PlayerDTO playerDTO = createPlayerWithRandomUsername();

        // When
        Player player = playerService.addNewPlayer(playerDTO);

        // Then
        assertThat(player).isNotNull();
        assertThat(player.getId()).isGreaterThan(0);
    }
}
