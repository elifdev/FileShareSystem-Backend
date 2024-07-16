package fileProject.business.concretes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import fileProject.business.abstracts.SignUpService;
import fileProject.dataAccess.UserRepository;
import fileProject.entities.User;

@Service
public class SignUpManager implements SignUpService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public String signUp(String name, String email, String password) {

		if (userRepository.getUserByEmail(email).equals(email)) {
			throw new RuntimeException("User with this email already exists");
		}

		User newUser = new User();
		newUser.setName(name);
		newUser.setEmail(email);
		newUser.setPassword(passwordEncoder.encode(password));

		userRepository.save(newUser);

		return "User registered successfully";
	}
}
