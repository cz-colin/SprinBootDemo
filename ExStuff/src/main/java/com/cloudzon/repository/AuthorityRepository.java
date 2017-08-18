package com.cloudzon.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cloudzon.domain.Authority;

/**
 * Spring Data JPA repository for the Authority entity.
 */
// @Repository("authorityRepository")
public interface AuthorityRepository extends JpaRepository<Authority, String> {
}
