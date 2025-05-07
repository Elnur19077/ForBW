package bw.black.exception;

import lombok.Data;

@Data
public final class ExceptionConstant {
    public static final Integer INTERNAL_EXCEPTION = 100;

    public static final Integer INVALID_REQUEST_DATA = 101;

    public static final Integer CONTACTS_NOT_FOUND = 102;

    public static final Integer USERNAME_OR_PASSWORD_ARE_INCORRECT = 103;

    public static final Integer EMPLOYEE_NOT_FOUND =104 ;
}
