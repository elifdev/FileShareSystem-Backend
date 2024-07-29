package fileProject.business.rules.signUp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fileProject.core.utilities.exception.BusinessException;
import fileProject.core.utilities.exception.Message;
import fileProject.dataAccess.UserRepository;
import fileProject.entities.User;

@Service
public class SignUpBusinessRule {

	@Autowired
	private UserRepository userRepository;

	public boolean checkUserExists(String email) {
		User user = userRepository.findByEmail(email);
		if (user != null) {
			return true;
		} else {
			throw new BusinessException(Message.USERNOTFOUND);
		}

	}
}
