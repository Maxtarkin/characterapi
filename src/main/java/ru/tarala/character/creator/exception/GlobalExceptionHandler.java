package ru.tarala.character.creator.exception;

import javax.persistence.EntityNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.tarala.character.creator.model.BaseErrorResponse;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

  @Override
  @NonNull
  protected ResponseEntity<Object> handleExceptionInternal(
      @NonNull Exception ex,
      Object body,
      @NonNull HttpHeaders headers,
      @NonNull HttpStatus status,
      @NonNull WebRequest request) {
    return constructBaseErrorResponse(ex, status, request);
  }

  @ExceptionHandler(EntityNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ResponseEntity<Object> handleEntityNotFoundException(EntityNotFoundException ex) {
    return constructBaseErrorResponse(ex, HttpStatus.NOT_FOUND, null);
  }

  @ExceptionHandler(IllegalStateException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ResponseEntity<Object> handleIllegalStateException(IllegalStateException ex) {
    return constructBaseErrorResponse(ex, HttpStatus.BAD_REQUEST, null);
  }

  @ExceptionHandler(IllegalArgumentException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException ex) {
    return constructBaseErrorResponse(ex, HttpStatus.BAD_REQUEST, null);
  }

  private ResponseEntity<Object> constructBaseErrorResponse(
      Exception ex, HttpStatus status, WebRequest request) {
    if (status == null) {
      status = HttpStatus.INTERNAL_SERVER_ERROR;
    }
    StackTraceElement[] stackTraceElements = ex.getStackTrace();
    String className =
        stackTraceElements[0]
            .getClassName()
            .substring(0, stackTraceElements[0].getClassName().length() - ".java".length());
    String methodName = stackTraceElements[0].getMethodName();
    var errorResponse =
        new BaseErrorResponse()
            .className(className)
            .statusCode(status.value())
            .methodName(methodName)
            .message(ex.getLocalizedMessage());
    //    if (IncludeStacktrace.ALWAYS.equals(errorProperties.getIncludeStacktrace()))
    //      commonFault.stackTrace(GeneralUtil.getStackTrace(error));
    return ResponseEntity.status(status).body(errorResponse);
  }
}
