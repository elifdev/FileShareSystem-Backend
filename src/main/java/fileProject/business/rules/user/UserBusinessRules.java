package fileProject.business.rules.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fileProject.dataAccess.UserRepository;

@Service
public class UserBusinessRules {

	@Autowired
	private UserRepository userRepository;
}
