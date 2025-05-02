package bw.black.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class ContactsException extends RuntimeException {
    private Integer code;

    public ContactsException(String message, Integer code) {
        super(message);
        this.code = code;

    }

    public Integer getErrorCode() {
        return code;
    }


}
