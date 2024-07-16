package fileProject.business.concretes;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import fileProject.business.abstracts.UserService;
import fileProject.core.utilities.AuthenticatedUser;
import fileProject.core.utilities.exception.BusinessException;
import fileProject.dataAccess.UserRepository;
import fileProject.entities.User;

@Service
public class UserManager implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public User create(User user) {
		User createdUser = userRepository.save(user);
		String authenticatedEmail = SecurityContextHolder.getContext().getAuthentication().getName();
		return createdUser;
	}

	@Override
	public User update(User user) {
		Optional<User> optionalDbUser = userRepository.getUserByEmail(user.getEmail());

		return optionalDbUser.map(dbUser -> {
			dbUser.setName(user.getName());
			dbUser.setEmail(user.getEmail());
			dbUser.setPassword(user.getPassword());

			User updated = userRepository.save(dbUser);
			String authenticatedEmail = SecurityContextHolder.getContext().getAuthentication().getName();

			return updated;
		}).orElseThrow(() -> new RuntimeException("User not found with email " + user.getEmail()));
	}

	@Override
	public void delete(UUID id) {
		Optional<User> oUser = userRepository.findById(id);

		if (oUser.isPresent()) {
			userRepository.deleteById(id);

		} else {
			throw new RuntimeException("User not found with id " + id);
		}
	}

	@Override
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	@Override
	public User findUserByName(String name) {
		return userRepository.findUserByName(name);
	}

	@Override
	public boolean changePassword(String oldPassword, String newPassword, String email) {
		Optional<User> oUser = userRepository.getUserByEmail(email);
		if (oUser.isPresent()) {
			User dbUser = oUser.get();

			if (passwordEncoder.matches(oldPassword, dbUser.getPassword())) {
				dbUser.setPassword(passwordEncoder.encode(newPassword));
				userRepository.save(dbUser);
				String authenticatedEmail = SecurityContextHolder.getContext().getAuthentication().getName();

				return true;
			}
		}
		return false;
	}

	@Override
	public User getUserById(UUID userId) {
		return userRepository.getUserById(userId);
	}

	@Override
	public Optional<User> getUserByEmail(String email) {
		return userRepository.getUserByEmail(email);
	}

	@Override
	public User getAuthenticatedUser() {
		User user = getCurrentUser();
		return user;
	}

	public User getCurrentUser() {
		String authenticatedEmail = AuthenticatedUser.getCurrentUser();
		User currentUser = userRepository.findByEmail(authenticatedEmail);
		if (currentUser.getEmail().equals(authenticatedEmail)) {
			return currentUser;
		} else {
			throw new BusinessException("User not found");
		}
	}
}
