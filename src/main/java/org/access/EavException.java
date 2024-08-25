package org.access;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.INTERNAL_SERVER_ERROR, reason="Database could not connect")
public class EavException extends RuntimeException {
    EavException() { super(); }
    EavException(String message) { super(message); }
}
