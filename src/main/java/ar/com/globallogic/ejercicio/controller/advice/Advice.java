package ar.com.globallogic.ejercicio.controller.advice;

import ar.com.globallogic.ejercicio.dto.ErrorDto;
import ar.com.globallogic.ejercicio.exception.InternalServerException;
import ar.com.globallogic.ejercicio.exception.PasswordNotValidException;
import ar.com.globallogic.ejercicio.exception.UserNotFoundException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ValidationException;
import java.time.LocalDateTime;

@ControllerAdvice
public class Advice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ValidationException.class)
    protected ResponseEntity<Object> handleValidation(RuntimeException ex, WebRequest request) {

        ErrorDto errorDto = new ErrorDto();
        errorDto.setCodigo(HttpStatus.BAD_REQUEST.value());
        errorDto.setTimestamp(LocalDateTime.now());
        errorDto.setDetail(ex.getMessage());

        return new ResponseEntity(errorDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(JWTVerificationException.class)
    protected ResponseEntity<Object> handleTokenNotValid(RuntimeException ex, WebRequest request) {

        ErrorDto errorDto = new ErrorDto();
        errorDto.setCodigo(HttpStatus.UNAUTHORIZED.value());
        errorDto.setTimestamp(LocalDateTime.now());
        errorDto.setDetail("Token no válido");

        return new ResponseEntity(errorDto, HttpStatus.UNAUTHORIZED);

    }

    @ExceptionHandler(UserNotFoundException.class)
    protected ResponseEntity<Object> handleUserNotFound(RuntimeException ex, WebRequest request) {

        ErrorDto errorDto = new ErrorDto();
        errorDto.setCodigo(HttpStatus.NOT_FOUND.value());
        errorDto.setTimestamp(LocalDateTime.now());
        errorDto.setDetail("Usuario no encontrado");

        return new ResponseEntity<>(errorDto, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(PasswordNotValidException.class)
    protected ResponseEntity<Object> handlePasswordNotValid(RuntimeException ex, WebRequest request) {

        ErrorDto errorDto = new ErrorDto();
        errorDto.setCodigo(HttpStatus.UNAUTHORIZED.value());
        errorDto.setTimestamp(LocalDateTime.now());
        errorDto.setDetail("Contraseña incorrecta");

        return new ResponseEntity<>(errorDto, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(InternalServerException.class)
    protected ResponseEntity<Object> handleInternalServerError(RuntimeException ex, WebRequest request) {

        ErrorDto errorDto = new ErrorDto();
        errorDto.setCodigo(HttpStatus.INTERNAL_SERVER_ERROR.value());
        errorDto.setTimestamp(LocalDateTime.now());
        errorDto.setDetail("Ocurrió un error");

        return new ResponseEntity<>(errorDto, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        ErrorDto errorDto = new ErrorDto();
        errorDto.setCodigo(HttpStatus.BAD_REQUEST.value());
        errorDto.setTimestamp(LocalDateTime.now());
        errorDto.setDetail(ex.getBindingResult().getFieldError().getDefaultMessage());
        System.out.println(ex.getMessage());

        return new ResponseEntity(errorDto, HttpStatus.BAD_REQUEST);
    }
}
