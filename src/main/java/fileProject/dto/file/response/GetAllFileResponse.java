package fileProject.dto.file.response;

import java.time.LocalDateTime;
import java.util.UUID;

import fileProject.dto.user.response.UserResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetAllFileResponse {

	private UUID id;
	private String fileName;
	private String filePath;
	private LocalDateTime uploadDate;
	private UserResponse name;

}
