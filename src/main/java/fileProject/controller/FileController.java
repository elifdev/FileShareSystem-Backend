package fileProject.controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import fileProject.business.abstracts.FileService;
import fileProject.business.abstracts.UserService;
import fileProject.core.utilities.config.mapper.ModelMapperService;
import fileProject.dto.SuccessResponse;
import fileProject.dto.file.request.CreateFileRequest;
import fileProject.dto.file.request.DeleteFileRequest;
import fileProject.dto.file.request.UpdateFileRequest;
import fileProject.dto.file.response.GetAllFileResponse;
import fileProject.entities.File;
import io.jsonwebtoken.io.IOException;

@RestController
@RequestMapping("/api/v1/files")
public class FileController {

	@Autowired
	private FileService fileService;

	@Autowired
	private UserService userService;

	@Autowired
	private ModelMapperService modelMapper;

	@PostMapping("/create")
	public SuccessResponse createFile(@RequestBody CreateFileRequest createFileRequest) {
		File file = modelMapper.forRequest().map(createFileRequest, File.class);
		fileService.create(file);
		return new SuccessResponse();
	}

	@PutMapping("/update")
	public SuccessResponse updateFile(@RequestBody UpdateFileRequest updateFileRequest) {
		File file = modelMapper.forRequest().map(updateFileRequest, File.class);
		fileService.update(file);
		return new SuccessResponse();
	}

	@DeleteMapping("/delete")
	public SuccessResponse deleteFile(@RequestBody DeleteFileRequest deleteFileRequest) {
		fileService.delete(deleteFileRequest.getId());
		return new SuccessResponse();
	}

	@PostMapping("/upload")
	public ResponseEntity<File> uploadFile(@RequestParam("file") MultipartFile file) {
		try {
			File uploadedFile = fileService.uploadFile(file);
			return ResponseEntity.ok(uploadedFile);
		} catch (IOException e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}

	@GetMapping("/download/{fileId}")
	public ResponseEntity<byte[]> downloadFile(@PathVariable UUID fileId) {
		try {
			Optional<byte[]> fileData = fileService.downloadFile(fileId);
			return fileData.map(ResponseEntity::ok).orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}

	@GetMapping("/getAll")
	public List<GetAllFileResponse> getAllFiles() {
		List<File> files = fileService.getAllFiles();
		return files.stream().map(file -> modelMapper.forResponse().map(file, GetAllFileResponse.class))
				.collect(Collectors.toList());
	}
}
