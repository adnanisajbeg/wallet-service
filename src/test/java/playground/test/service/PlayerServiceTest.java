package playground.test.service;

import org.junit.Before;
import org.junit.Test;
import playground.test.model.Player;
import playground.test.model.PlayerDTO;
import playground.test.repository.PlayerRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static playground.test.utils.PlayerUtils.createPlayerWithRandomUsername;


public class PlayerServiceTest {
    private PlayerService playerService;
    PlayerRepository playerRepository = mock(PlayerRepository.class);

    @Before
    public void setup() {
        playerService = new PlayerService();
        playerService.playerRepository = playerRepository;
    }

    @Test
    public void when_adding_new_player_saved_object_contains_given_username() {
        // Given
        PlayerDTO playerDTO = createPlayerWithRandomUsername();
        when(playerRepository.save(new Player(playerDTO))).thenReturn(new Player(playerDTO));

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


}
