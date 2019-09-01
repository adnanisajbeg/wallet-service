package playground.test.validator;

import org.springframework.stereotype.Component;
import playground.test.exceptions.InvalidInputException;
import playground.test.model.CreditSubmitDTO;

import static playground.test.utils.Messages.*;

@Component
public class PlayerValidator {
    public void validateCreditSubmitDTO(CreditSubmitDTO creditSubmitDTO) {
        if (creditSubmitDTO == null) {
            throw new InvalidInputException(INPUT_OBJECT_IS_NULL_ERROR_MESSAGE);
        }

        if (creditSubmitDTO.getUsername() == null || creditSubmitDTO.getUsername().trim().isEmpty()) {
            throw new InvalidInputException(USERNAME_MUST_BE_PROVIDED_ERROR_MESSAGE);
        }

        if (creditSubmitDTO.getCredit() <= 0L) {
            throw new InvalidInputException(CREDIT_NON_POSITIVE_ERROR_MESSAGE);
        }

    }
}
