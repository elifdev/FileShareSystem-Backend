package fileProject.dto.file.request;

import java.time.LocalDateTime;

import fileProject.entities.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateFileRequest {

	private String fileName;

	private String filePath;

	private LocalDateTime uploadDate;

	private User user;

}
