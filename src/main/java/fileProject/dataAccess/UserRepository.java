package fileProject.dataAccess;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import fileProject.entities.User;

public interface UserRepository extends JpaRepository<User, UUID> {

	Optional<User> getUserByEmail(String email);

	User findUserByName(String name);

	User getUserById(UUID userId);

	User findByEmail(String email);

}
