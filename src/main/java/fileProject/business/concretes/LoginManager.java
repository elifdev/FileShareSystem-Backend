package fileProject.business.concretes;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import fileProject.business.abstracts.LoginService;
import fileProject.business.abstracts.UserService;
import fileProject.core.utilities.config.jwt.JwtConfig;
import fileProject.entities.User;

@Service
public class LoginManager implements LoginService {

	@Autowired
	private UserService userService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private JwtConfig jwtConfig;

	@Override
	public String login(String email, String password) {
		Optional<User> user = userService.getUserByEmail(email);
		if (passwordEncoder.matches(password, user.get().getPassword())) {
			String token = jwtConfig.createToken(user.get());
			return token;
		} else {
			throw new RuntimeException("Invalid email or password");
		}

//		if (user == null || passwordEncoder.matches(password, user.getPassword())) {
//			throw new RuntimeException("Invalid email or password");
//		}
//
//		return jwtConfig.createToken(user);
//	}

	}
}
