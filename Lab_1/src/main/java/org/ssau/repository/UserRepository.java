package org.ssau.repository;

import org.springframework.data.repository.CrudRepository;
import org.ssau.model.User;

public interface UserRepository extends CrudRepository<User, Long> {
}
