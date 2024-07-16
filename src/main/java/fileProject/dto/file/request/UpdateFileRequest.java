package fileProject.dto.file.request;

import java.time.LocalDateTime;
import java.util.UUID;

import fileProject.entities.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateFileRequest {

	private UUID id;
	private String fileName;
	private String filePath;
	private LocalDateTime uploadDate;
	private User user;
}
