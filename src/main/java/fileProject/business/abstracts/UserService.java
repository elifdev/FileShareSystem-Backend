package fileProject.business.abstracts;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import fileProject.entities.User;

public interface UserService extends BaseService<User> {

	List<User> getAllUsers();

	User findUserByName(String name);

	boolean changePassword(String oldPassword, String newPassword, String email);

	User getUserById(UUID userId);

	Optional<User> getUserByEmail(String email);

	User getAuthenticatedUser();
}
