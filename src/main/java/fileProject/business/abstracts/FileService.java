package fileProject.business.abstracts;

import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import fileProject.dto.file.response.GetFileResponse;
import fileProject.entities.File;

public interface FileService extends BaseService<File> {

	File uploadFile(MultipartFile file);

	Optional<Resource> downloadFile(UUID fileId);

	List<File> getAllFiles();

	String getContentType(Path path) throws java.io.IOException;

	Optional<GetFileResponse> getFile(UUID id);

	ResponseEntity<Resource> serveFile(UUID id);

}
