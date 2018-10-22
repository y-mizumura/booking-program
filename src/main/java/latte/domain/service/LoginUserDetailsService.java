package latte.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import latte.domain.model.User;
import latte.domain.repository.UserRepository;

@Service
public class LoginUserDetailsService implements UserDetailsService{

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String userId) {
		User user = userRepository.findByUserId(userId);
		if (user == null) {
			throw new UsernameNotFoundException(userId + "is not found!");
		}
		return new LoginUserDetails(user);
	}

}
