package com.repgen.inventorycloud.exception;

import com.repgen.inventorycloud.modal.ResponseMessages;
import com.repgen.inventorycloud.modal.ResponseValues;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class DefaultExceptionHandler extends ResponseEntityExceptionHandler{

	public final String responseFailed = "Failed";

	@Autowired
	ResponseValues responseValues;

	@Autowired
	ResponseMessages responseMessages;

	@ExceptionHandler(MessageBodyConstraintViolationException.class)
	public ResponseEntity<?> serviceException(MessageBodyConstraintViolationException e){
		responseValues.setStatus(responseMessages.getResponseFailed());
		responseValues.setMessage(e.getMessage());
		responseValues.setCode("#1200003");
		return new ResponseEntity<>(responseValues,HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
