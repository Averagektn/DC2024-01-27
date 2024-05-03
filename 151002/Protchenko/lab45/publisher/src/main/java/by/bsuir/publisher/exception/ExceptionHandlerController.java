package by.bsuir.publisher.exception;

import by.bsuir.publisher.model.dto.response.ErrorResponseDto;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleResourceNotFoundException(
            final ResourceNotFoundException ex){
        return ErrorUtil.errorResponseEntity(HttpStatus.NOT_FOUND, 1, ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDto> handleMethodArgumentNotValidException(
            final MethodArgumentNotValidException ex) {
        List<String> errors = ex.getFieldErrors()
                .stream()
                .map(e ->  e.getField() + " : " + e.getDefaultMessage())
                .toList();
        return ErrorUtil.errorResponseEntity(HttpStatus.BAD_REQUEST, 3,
                "Argument validation error: " + errors);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponseDto> handleDataIntegrityViolationException(
            final DataIntegrityViolationException ex) {
        Throwable cause = ex.getCause();
        if (cause instanceof ConstraintViolationException c){
            return ErrorUtil.errorResponseEntity(HttpStatus.FORBIDDEN, 1, "Invalid request data. Error: "
                    + c.getErrorCode() + ", State: " + c.getSQLState());
        }
        return ErrorUtil.errorResponseEntity(HttpStatus.FORBIDDEN, 0, "Invalid request data");
    }

    @ExceptionHandler(PostExchangeTimeoutException.class)
    public ResponseEntity<ErrorResponseDto> handlePostExchangeTimeoutException(
            final PostExchangeTimeoutException ex) {
        return ErrorUtil.errorResponseEntity(HttpStatus.NOT_FOUND, 2, ex.getMessage());
    }

    @ExceptionHandler(PostExchangeFailedException.class)
    public ResponseEntity<ErrorResponseDto> handlePostExchangeFailedException(
            final PostExchangeFailedException ex) {
        return ErrorUtil.errorResponseEntity(HttpStatus.NOT_FOUND, 3, ex.getMessage());
    }

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<ErrorResponseDto> handleRuntimeException(Throwable t){
        return ErrorUtil.errorResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, 1,
                "Internal server error. Try later again");
    }
}
