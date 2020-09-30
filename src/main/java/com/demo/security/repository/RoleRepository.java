package com.demo.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.security.model.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {

}
