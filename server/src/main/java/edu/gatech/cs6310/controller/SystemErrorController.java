package edu.gatech.cs6310.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import edu.gatech.cs6310.dto.SystemErrorUpdateDto;
import edu.gatech.cs6310.model.SystemError;
import edu.gatech.cs6310.repository.SystemErrorRepository;
import org.springframework.security.access.prepost.*;

@RestController
public class SystemErrorController extends DeliveryServiceController {
	@Autowired SystemErrorRepository systemErrorRepository;

	private SystemError lookupError(String errorName) throws Exception {
		SystemError error = systemErrorRepository.findByName(errorName);
		if(error == null) {
			throw new Exception("error_not_found");
		}

		return error;
	}

	@GetMapping("errors")
	public Iterable<SystemError> getErrorMessages() throws Exception {
		return systemErrorRepository.findAll();
	}

	@GetMapping("errors/{error}")
	public SystemError getErrorMessage(@PathVariable String error) throws Exception {
		return lookupError(error);
	}

	@PostMapping("errors/{error}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public SystemError updateErrorText(@PathVariable String error, @RequestBody SystemErrorUpdateDto dto) throws Exception {
		SystemError err = lookupError(error);
		if(dto.errorText != null) {
			err.setErrorText(dto.errorText);
		}
		return systemErrorRepository.save(err);
	}
}
