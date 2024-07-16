package fileProject.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fileProject.business.abstracts.UserService;
import fileProject.core.utilities.config.mapper.ModelMapperService;
import fileProject.dto.SuccessResponse;
import fileProject.dto.user.request.CreateUserRequest;
import fileProject.dto.user.request.DeleteUserRequest;
import fileProject.dto.user.request.PasswordChangeRequest;
import fileProject.dto.user.request.UpdateUserRequest;
import fileProject.dto.user.response.GetAllUsersResponse;
import fileProject.dto.user.response.UserResponse;
import fileProject.entities.User;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

	@Autowired
	private UserService userService;
	@Autowired
	private ModelMapperService modelMapper;

	@PostMapping("/create")
	public SuccessResponse create(@Valid @RequestBody CreateUserRequest request) {
		User user = modelMapper.forRequest().map(request, User.class);
		userService.create(user);
		return new SuccessResponse();
	}

	@PutMapping("/update")
	private SuccessResponse update(@RequestBody UpdateUserRequest request) {
		User user = modelMapper.forRequest().map(request, User.class);
		userService.update(user);
		return new SuccessResponse();
	}

	@PostMapping("/delete")
	public SuccessResponse delete(@RequestBody DeleteUserRequest request) {
		userService.delete(request.getId());
		return new SuccessResponse();

	}

	@GetMapping("/getAll")
	public ResponseEntity<List<GetAllUsersResponse>> getAllUsers() {
		List<User> users = userService.getAllUsers();
		List<GetAllUsersResponse> userDTOs = new ArrayList<>();
		users.forEach(user -> {
			userDTOs.add(modelMapper.forResponse().map(user, GetAllUsersResponse.class));
		});
		return ResponseEntity.ok(userDTOs);

	}

	@GetMapping("/{name}")
	public UserResponse findUserByName(@PathVariable String name) {
		User user = userService.findUserByName(name);
		return modelMapper.forResponse().map(user, UserResponse.class);
	}

	@PostMapping("/changePassword")
	public SuccessResponse changePassword(@RequestBody PasswordChangeRequest request, Principal principal) {
		userService.changePassword(request.getOldPassword(), request.getNewPassword(), principal.getName());
		return new SuccessResponse();
	}

}
