package fileProject.dto.fileShare.response;

import java.util.UUID;

import fileProject.dto.file.response.FileIdResponse;
import fileProject.dto.user.response.UserIdResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetAllFileShareResponse {

	private UUID id;

	private FileIdResponse fileId;

	private UserIdResponse userId;
}
