package org.access;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@SuppressWarnings("unused")
@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleGenericException(RuntimeException err) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(err.getMessage());
    }

    @ExceptionHandler(EavException.class)
    public ResponseEntity<String> handleEavException() {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Err: Could not connect to DB");
    }

}
