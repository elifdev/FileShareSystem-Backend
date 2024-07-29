package fileProject.dto.file.response;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.Data;

@Data

public class GetFileResponse {

	private UUID id;

	private String fileName;

	private String filePath;

	private LocalDateTime uploadDate;

}
