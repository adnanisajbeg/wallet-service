package playground.test.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import playground.test.model.Action;
import playground.test.model.Player;
import playground.test.model.Status;
import playground.test.model.TransactionHistory;
import playground.test.repository.PlayerRepository;
import playground.test.repository.TransactionHistoryRepository;

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
}
