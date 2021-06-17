package guru.sfg.brewery.repositories.security;

import guru.sfg.brewery.domain.security.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by sousaJ on 17/06/2021
 * in package - guru.sfg.brewery.repositories.security
 **/
public interface RoleRepository extends JpaRepository<Role, Long> {

}
