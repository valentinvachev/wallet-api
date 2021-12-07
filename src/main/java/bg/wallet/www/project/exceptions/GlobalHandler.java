package bg.wallet.www.project.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@ControllerAdvice
public class GlobalHandler {
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<?> notFoundException(EntityNotFoundException ex,HttpServletRequest request) {
        return error(HttpStatus.NOT_FOUND, request.getServletPath(), ex.getMessage());
    }

    @ExceptionHandler(DuplicateEntityException.class)
    public ResponseEntity<?> duplicateEntity(DuplicateEntityException ex,HttpServletRequest request) {
        return error(HttpStatus.CONFLICT, request.getServletPath(), ex.getMessage());
    }

    @ExceptionHandler(InvalidInputException.class)
    public ResponseEntity<?> invalidInput(InvalidInputException ex,HttpServletRequest request) {
        return error(HttpStatus.CONFLICT, request.getServletPath(), ex.getMessage());
    }

    @ExceptionHandler(NotAuthorizedException.class)
    public ResponseEntity<?> authorization(NotAuthorizedException ex, HttpServletRequest request) {
        return error(HttpStatus.UNAUTHORIZED, request.getServletPath(), ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> methodArgumentNotValidException(MethodArgumentNotValidException ex,HttpServletRequest request) {
        BindingResult result = ex.getBindingResult();
        List<FieldError> fieldErrors = result.getFieldErrors();
        return error(HttpStatus.NOT_FOUND, request.getServletPath(), String.format("%s: %s",fieldErrors.get(0).getField(),fieldErrors.get(0).getDefaultMessage()));
    }

    private ResponseEntity <?> error(HttpStatus httpStatus,String path,String message) {
        return ResponseEntity.status(httpStatus).body(new ErrorBodyInfo(path,message));
    }

}