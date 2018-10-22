package latte.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import latte.domain.model.User;

public interface UserRepository extends JpaRepository<User, String>{
	
	public User findByUserId(String userId);
	
}
