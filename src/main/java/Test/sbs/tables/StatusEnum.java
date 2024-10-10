package Test.sbs.tables;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

public enum StatusEnum {
    @JsonProperty("IS_NOT_READY")
    IS_NOT_READY("Is not ready"),
    @JsonProperty("IN_PROGRESS")
    IN_PROGRESS("In progress"),
    @JsonProperty("COMPLETE")
    COMPLETE("Complete"),
    @JsonProperty("CANCELED")
    CANCELED("Canceled");
    private final String value;
    StatusEnum(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }
}
