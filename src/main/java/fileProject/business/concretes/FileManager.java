package fileProject.business.concretes;

import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import fileProject.business.abstracts.FileService;
import fileProject.business.abstracts.UserService;
import fileProject.core.utilities.config.mapper.ModelMapperService;
import fileProject.dataAccess.FileRepository;
import fileProject.dto.file.response.GetFileResponse;
import fileProject.entities.File;
import fileProject.entities.User;
import io.jsonwebtoken.io.IOException;

@Service
public class FileManager implements FileService {

	@Autowired
	private FileRepository fileRepository;

	@Autowired
	private UserService userService;

	@Autowired
	private ModelMapperService modelMapperService;

	@Override
	public File create(File file) {
		return fileRepository.save(file);

	}

	@Override
	public File update(File file) {
		File oFile = fileRepository.findById(file.getId()).orElseThrow(() -> new RuntimeException("File not found"));

		oFile.setFileName(file.getFileName());
		oFile.setFilePath(file.getFilePath());
		oFile.setUploadDate(LocalDateTime.now());

		return fileRepository.save(oFile);
	}

	@Override
	public void delete(UUID fileId) throws IOException {
		File file = fileRepository.findById(fileId).orElseThrow(() -> new RuntimeException("File not found"));
		Path path = Paths.get(file.getFilePath());
		try {
			Files.delete(path);
		} catch (java.io.IOException e) {

			e.printStackTrace();
		}

		fileRepository.delete(file);
	}

	private final String uploadDir = "upload/";

	@Override
	public File uploadFile(MultipartFile file) throws IOException {
		User user = userService.getAuthenticatedUser();
		String fileName = file.getOriginalFilename();
		String filePath = uploadDir + "_" + fileName;

		Path path = Paths.get(filePath);
		try {
			Files.write(path, file.getBytes());
		} catch (java.io.IOException e) {

			e.printStackTrace();
		}

		File fileEntity = new File();

		fileEntity.setFileName(fileName);
		fileEntity.setFilePath(filePath);
		fileEntity.setUploadDate(LocalDateTime.now());

		return fileRepository.save(fileEntity);
	}

	public Optional<Resource> downloadFile(UUID fileId) throws IOException {
		File file = fileRepository.findById(fileId).orElseThrow(() -> new IOException("File not found"));

		Path path = Paths.get(file.getFilePath());
		if (!Files.exists(path)) {
			throw new IOException("File not found at path: " + file.getFilePath());
		}

		try {
			Resource resource = new UrlResource(path.toUri());
			if (resource.exists() && resource.isReadable()) {
				return Optional.of(resource);
			} else {
				throw new IOException("File not readable or does not exist at path: " + file.getFilePath());
			}
		} catch (MalformedURLException e) {
			throw new IOException("Error while creating UrlResource", e);
		}
	}

	@Override
	public String getContentType(Path path) throws java.io.IOException {
		String contentType = Files.probeContentType(path);
		return (contentType != null) ? contentType : "application/octet-stream";
	}

	@Override
	public List<File> getAllFiles() {
		return fileRepository.findAll();
	}

	@Override
	public Optional<GetFileResponse> getFile(UUID id) {
		return fileRepository.findById(id)
				.map(file -> modelMapperService.forResponse().map(file, GetFileResponse.class));
	}

	@Override
	public ResponseEntity<Resource> serveFile(UUID id) {
		GetFileResponse fileResponse = getFile(id).orElseThrow(() -> new RuntimeException("File not found"));
		Resource resource = downloadFile(id).orElseThrow(() -> new RuntimeException("File not found"));

		String contentType = null;
		try {
			String filePath = fileResponse.getFilePath();
			if (filePath == null || filePath.isEmpty()) {
				throw new RuntimeException("File path is invalid");
			}
			try {
				contentType = getContentType(Paths.get(filePath));
			} catch (java.io.IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (IOException e) {
			contentType = "application/octet-stream";
		}

		return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType))
				.header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + fileResponse.getFileName() + "\"")
				.body(resource);
	}

}