package com.cloudzon.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cloudzon.domain.User;

@Repository("userRepository")
public interface UserRepository extends JpaRepository<User, Long> {

	User findByUsername(String username);

	@EntityGraph(attributePaths = "authorities")
	Optional<User> findOneWithAuthoritiesByUsername(String username);

	User findOneByMobileNumber(String mobileNumber);
}
