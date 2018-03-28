package net.proselyte.springsecurityapp.dao.interfaces;

import net.proselyte.springsecurityapp.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleDao extends JpaRepository<Role, Long> {
}
