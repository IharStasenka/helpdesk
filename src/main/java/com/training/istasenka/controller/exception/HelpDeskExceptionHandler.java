package com.training.istasenka.controller.exception;

import com.training.istasenka.converter.message.MessageConverter;
import com.training.istasenka.dto.messages.MessagesDto;
import com.training.istasenka.exception.CustomIllegalArgumentException;
import com.training.istasenka.exception.IllegalTicketStatusTransitionException;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.keycloak.authorization.client.util.HttpResponseException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import static com.training.istasenka.util.MessageType.ERROR;

@ControllerAdvice
@AllArgsConstructor
public class HelpDeskExceptionHandler {
    private static final Logger logger = LogManager.getLogger("DebugLevelLog");

    private final MessageConverter messageConverter;

    @ExceptionHandler(value = BadCredentialsException.class)
    public ResponseEntity<MessagesDto> getBadCredential(Exception exception) {
        var errorMessage = exception.getMessage();
        var errorMessageDto = messageConverter.fromMessage(errorMessage, ERROR);
        logger.debug(String.format("in the bed credential with exception: %s", errorMessage));
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).contentType(MediaType.APPLICATION_JSON).body(errorMessageDto);
    }


    @ExceptionHandler(value = {HttpResponseException.class})
    public ResponseEntity<MessagesDto> getJWTVerification(HttpResponseException exception) {
        var errorMessage = exception.getMessage();
        var errorMessageDto = messageConverter.fromMessage(errorMessage, ERROR);
        logger.info(String.format("in the keycloak response with exception: %s", errorMessage));
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).contentType(MediaType.APPLICATION_JSON).body(errorMessageDto);
    }

    @ExceptionHandler(value = {MethodArgumentNotValidException.class, IllegalStateException.class, HttpMessageNotReadableException.class})
    public ResponseEntity<MessagesDto> getAuthorizationError(MethodArgumentNotValidException exception) {
        var result = exception.getBindingResult();
        var errorMessages = result.getFieldErrors();
        var errorMessageDto = messageConverter.fromErrorList(errorMessages);
        logger.info(String.format("in the isAuthorizationError with exception: %s", errorMessageDto));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.APPLICATION_JSON).body(errorMessageDto);
    }

    @ExceptionHandler(value = {IllegalTicketStatusTransitionException.class})
    public ResponseEntity<MessagesDto> getIllegalTicketStatusChangeException(IllegalTicketStatusTransitionException exception) {
        var errorMessage = exception.getMessage();
        var errorMessageDto = messageConverter.fromMessage(errorMessage, ERROR);
        logger.debug(String.format("in the  getIllegalTicketStatusChange with exception: %s", errorMessageDto));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.APPLICATION_JSON).body(errorMessageDto);
    }

    @ExceptionHandler(value = {MethodArgumentTypeMismatchException.class})
    public ResponseEntity<MessagesDto> getMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException exception) {
        var errorMessage = "";
        if (exception.getRootCause() != null) {
            errorMessage = exception.getRootCause().getMessage();
        }
        var errorMessageDto = messageConverter.fromMessage(errorMessage, ERROR);
        logger.debug(String.format("in the  getIllegalTicketStatusChange with exception: %s", errorMessageDto));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.APPLICATION_JSON).body(errorMessageDto);
    }


    @ExceptionHandler(value = {CustomIllegalArgumentException.class})
    public ResponseEntity<MessagesDto> getCustomIllegalArgumentException(CustomIllegalArgumentException exception) {
        var errorMessage = exception.getMessage();
        var errorMessageDto = messageConverter.fromMessage(errorMessage, ERROR);
        logger.debug(String.format("in the  getIllegalTicketStatusChange with exception: %s", errorMessageDto));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.APPLICATION_JSON).body(errorMessageDto);
    }


    @ExceptionHandler(value = DataIntegrityViolationException.class)
    public ResponseEntity<MessagesDto> getConstraintViolation(Exception exception) {
        var errorMessage = "";
        if (exception.getMessage().toLowerCase().contains("ticket(name)")) {
            errorMessage = "There are existed ticket with the name";
        }
        var errorMessageDto = messageConverter.fromMessage(errorMessage, ERROR);
        logger.debug(String.format("in the  getAccessDenied with exception: %s", errorMessageDto));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.APPLICATION_JSON).body(errorMessageDto);
    }

    @ExceptionHandler(value = AccessDeniedException.class)
    public ResponseEntity<MessagesDto> getAccessDenied(Exception exception) {
        var errorMessage = exception.getMessage();
        var errorMessageDto = messageConverter.fromMessage(errorMessage, ERROR);
        logger.debug(String.format("in the  getAccessDenied with exception: %s", errorMessageDto));
        return ResponseEntity.status(HttpStatus.FORBIDDEN).contentType(MediaType.APPLICATION_JSON).body(errorMessageDto);
    }

    @ExceptionHandler(value = NoSuchElementException.class)
    public ResponseEntity<MessagesDto> getNotFound(Exception exception) {
        var errorMessage = exception.getMessage();
        var errorMessageDto = messageConverter.fromMessage(errorMessage, ERROR);
        logger.debug(String.format("in the  getNotFound with exception: %s", errorMessageDto));
        return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON).body(errorMessageDto);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<MessagesDto> getCrackedUrl(Exception exception) {
        var errorMessage = exception.getMessage();
        var errorMessageDto = messageConverter.fromMessage(errorMessage, ERROR);
        logger.debug(String.format("in the getCrackedUrl with exception: %s", errorMessageDto));
        return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON).body(errorMessageDto);
    }

    @ExceptionHandler(value = ConstraintViolationException.class)
    public ResponseEntity<MessagesDto> isException(ConstraintViolationException exception) {
        var constraintMessages = getConstraintMessages(exception);
        var errorMessageDto = messageConverter.fromMessages(constraintMessages, ERROR);
        logger.debug(String.format("in the ConstraintViolationException with exception: %s", errorMessageDto));
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).contentType(MediaType.APPLICATION_JSON).body(errorMessageDto);
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<MessagesDto> isException(Exception exception) {
        exception.printStackTrace();
        var errorMessage = exception.getMessage();
        var errorMessageDto = messageConverter.fromMessage(errorMessage, ERROR);
        logger.debug(String.format("in the isException with exception: %s", errorMessageDto));
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).contentType(MediaType.APPLICATION_JSON).body(errorMessageDto);
    }

    private List<String> getConstraintMessages(ConstraintViolationException exception) {
        return exception.
                getConstraintViolations().
                stream().
                map(ConstraintViolation::getMessageTemplate)
                .collect(Collectors.toList());
    }
}
