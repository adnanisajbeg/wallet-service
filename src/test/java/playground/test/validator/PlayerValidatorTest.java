package playground.test.validator;

import org.junit.Before;
import org.junit.Test;
import playground.test.exceptions.InvalidInputException;
import playground.test.model.CreditSubmitDTO;
import playground.test.model.PlayerDTO;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static playground.test.utils.Messages.*;
import static playground.test.utils.PlayerUtils.createPlayerWithRandomUsername;

public class PlayerValidatorTest {

    private PlayerValidator playerValidator;

    @Before
    public void setup() {
        playerValidator = new PlayerValidator();
    }

    @Test
    public void when_given_negative_amount_in_creditSubmitDTO_exception_is_thrown() {
        // Given
        PlayerDTO playerWithRandomUsername = createPlayerWithRandomUsername();
        CreditSubmitDTO creditSubmitDTO = new CreditSubmitDTO(UUID.randomUUID(), playerWithRandomUsername.getUsername(), -1L);

        // When
        assertThatExceptionOfType(InvalidInputException.class)
                .isThrownBy(() -> {
                    playerValidator.validateCreditSubmitDTO(creditSubmitDTO);

                    // Then
                }).withMessage(CREDIT_NON_POSITIVE_ERROR_MESSAGE);
    }

    @Test
    public void when_given_zero_amount_in_creditSubmitDTO_exception_is_thrown() {
        // Given
        PlayerDTO playerWithRandomUsername = createPlayerWithRandomUsername();
        CreditSubmitDTO creditSubmitDTO = new CreditSubmitDTO(UUID.randomUUID(), playerWithRandomUsername.getUsername(), 0L);

        // When
        assertThatExceptionOfType(InvalidInputException.class)
                .isThrownBy(() -> {
                    playerValidator.validateCreditSubmitDTO(creditSubmitDTO);

                    // Then
                }).withMessage(CREDIT_NON_POSITIVE_ERROR_MESSAGE);
    }

    @Test
    public void when_given_null_as_username_in_creditSubmitDTO_exception_is_thrown() {
        // Given
        CreditSubmitDTO creditSubmitDTO = new CreditSubmitDTO(UUID.randomUUID(), null, 112L);

        // When
        assertThatExceptionOfType(InvalidInputException.class)
                .isThrownBy(() -> {
                    playerValidator.validateCreditSubmitDTO(creditSubmitDTO);

                    // Then
                }).withMessage(USERNAME_MUST_BE_PROVIDED_ERROR_MESSAGE);
    }

    @Test
    public void when_given_empty_username_in_creditSubmitDTO_exception_is_thrown() {
        // Given
        CreditSubmitDTO creditSubmitDTO = new CreditSubmitDTO(UUID.randomUUID(), "     ", 112L);

        // When
        assertThatExceptionOfType(InvalidInputException.class)
                .isThrownBy(() -> {
                    playerValidator.validateCreditSubmitDTO(creditSubmitDTO);

                    // Then
                }).withMessage(USERNAME_MUST_BE_PROVIDED_ERROR_MESSAGE);
    }

    @Test
    public void when_given_null_creditSubmitDTO_exception_is_thrown() {
        // When
        assertThatExceptionOfType(InvalidInputException.class)
                .isThrownBy(() -> {
                    playerValidator.validateCreditSubmitDTO(null);

                    // Then
                }).withMessage(INPUT_OBJECT_IS_NULL_ERROR_MESSAGE);
    }
}
