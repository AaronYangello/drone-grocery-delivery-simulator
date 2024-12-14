package edu.gatech.cs6310.controller;

import java.util.*;
import java.util.regex.*;

import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.*;

@RestController
public class DeliveryServiceController {

	@ExceptionHandler(DataIntegrityViolationException.class)
	public HashMap handleError(DataIntegrityViolationException ex) {
		String field = "field";
		Pattern r = Pattern.compile(" : edu\\.gatech\\.cs6310\\.model\\.(.*);");
		Matcher m = r.matcher(ex.getMessage());
		if (m.find()) {
			field = m.group(1);
		}
		HashMap resp = new HashMap();
		resp.put("error", field.toLowerCase().replace(".", "_") + "_is_invalid");
		return resp;
	}

	@ExceptionHandler(JpaObjectRetrievalFailureException.class)
	public HashMap handleError(JpaObjectRetrievalFailureException ex) {
		String entity = "entity";
		Pattern r = Pattern.compile("Unable to find edu\\.gatech\\.cs6310\\.model\\.(\\w*) ");
		Matcher m = r.matcher(ex.getMessage());
		if (m.find()) {
			entity = m.group(1);
		}
		HashMap resp = new HashMap();
		resp.put("error", entity.toLowerCase() + "_not_found");
		return resp;
	}

	@ExceptionHandler(Exception.class)
	public HashMap handleError(Exception ex) {
		HashMap resp = new HashMap();
		resp.put("error", ex.getMessage());
		return resp;
	}
}
