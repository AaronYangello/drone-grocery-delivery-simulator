package edu.gatech.cs6310.security;

import java.util.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.*;

import edu.gatech.cs6310.model.User;
import edu.gatech.cs6310.repository.UserRepository;

@Service
public class AuthUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public AuthUserDetails loadUserByUsername(String account) {
		Optional<User> user = userRepository.findById(account);
		if (!user.isPresent()) {
			throw new UsernameNotFoundException(account);
		}
		return new AuthUserDetails(user.get());
	}
}
