package fileProject.business.abstracts;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import fileProject.entities.File;

public interface FileService extends BaseService<File> {

	File uploadFile(MultipartFile file);

	Optional<byte[]> downloadFile(UUID fileId);

	List<File> getAllFiles();

}
