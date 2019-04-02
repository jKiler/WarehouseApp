package com.jerzykwiatkowski.warehouse.repository;

import com.jerzykwiatkowski.warehouse.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByName(String name);
}
