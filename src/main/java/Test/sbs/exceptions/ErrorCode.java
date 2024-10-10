package Test.sbs.exceptions;

import lombok.Getter;

@Getter
public enum ErrorCode {
    ORDER_NOT_FOUND("Order with this ID hasn't been found"),
    PERSON_NOT_FOUND("Person with this ID hasn't been found"),
    GOOD_NOT_FOUND("Good with this ID hasn't been found"),
    ORDER_GOOD_NOT_FOUND("Match with these order ID and good ID hasn't been found");

    private final String message;

    ErrorCode(String message) {
        this.message = message;
    }
}
