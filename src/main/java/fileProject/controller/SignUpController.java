package fileProject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fileProject.business.abstracts.SignUpService;
import fileProject.dto.signUp.request.SignUpRequest;
import fileProject.dto.signUp.response.SignUpResponse;

@RestController
@RequestMapping("/api/v1")
public class SignUpController {

	@Autowired
	private SignUpService signUpService;

	@PostMapping("/signup")
	public ResponseEntity<SignUpResponse> signUp(@RequestBody SignUpRequest signUpRequest) {
		String result = signUpService.signUp(signUpRequest.getName(), signUpRequest.getEmail(),
				signUpRequest.getPassword());
		return ResponseEntity.ok(new SignUpResponse(result));
	}
}
