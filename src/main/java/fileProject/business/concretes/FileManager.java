package fileProject.business.concretes;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import fileProject.business.abstracts.FileService;
import fileProject.business.abstracts.UserService;
import fileProject.dataAccess.FileRepository;
import fileProject.entities.File;
import fileProject.entities.User;
import io.jsonwebtoken.io.IOException;

@Service
public class FileManager implements FileService {

	@Autowired
	private FileRepository fileRepository;

	@Autowired
	private UserService userService;

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
		oFile.setUser(file.getUser());

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
		fileEntity.setUser(user);

		return fileRepository.save(fileEntity);
	}

	@Override
	public Optional<byte[]> downloadFile(UUID fileId) throws IOException {
		try {
			File file = fileRepository.findById(fileId).orElseThrow(() -> new IOException("File not found"));

			Path path = Paths.get(file.getFilePath());
			if (!Files.exists(path)) {
				throw new IOException("File not found at path: " + file.getFilePath());
			}

			return Optional.of(Files.readAllBytes(path));
		} catch (IOException | java.io.IOException e) {
			e.printStackTrace(); // Log the exception for debugging purposes
			return Optional.empty();
		}
	}

	@Override
	public List<File> getAllFiles() {
		return fileRepository.findAll();
	}

}
