package pl.internship.antologic.common.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class ErrorHandler {

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> notFoundException(NotFoundException exc){
        ErrorResponse error = new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                exc.getMessage(),
                System.currentTimeMillis());
        return new ResponseEntity<>(error,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> existsException(ExistsException exc){
        ErrorResponse error = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                exc.getMessage(),
                System.currentTimeMillis());
        return new ResponseEntity<>(error,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> fieldsConstraintsException(MethodArgumentNotValidException exc){
        BindingResult result = exc.getBindingResult();
        List<FieldError> fieldErrors = result.getFieldErrors();

        String errorMessage = fieldErrors.stream().
                                    map(error -> error.getDefaultMessage()).
                                    collect(Collectors.joining(", "));

        ErrorResponse error = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                errorMessage,
                System.currentTimeMillis());
        return new ResponseEntity<>(error,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> httpMessageNotReadable(HttpMessageNotReadableException exc){

        ErrorResponse error = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "Http message is not readable",
                System.currentTimeMillis());
        return new ResponseEntity<>(error,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> exception(Exception exc){
        ErrorResponse error = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                exc.getMessage(),
                System.currentTimeMillis());
        return new ResponseEntity<>(error,HttpStatus.BAD_REQUEST);
    }



}
