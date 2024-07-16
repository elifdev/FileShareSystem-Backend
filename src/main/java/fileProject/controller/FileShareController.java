package fileProject.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fileProject.business.abstracts.FileShareService;
import fileProject.core.utilities.config.mapper.ModelMapperService;
import fileProject.dto.fileShare.response.GetAllFileShareResponse;
import fileProject.entities.FileShare;

@RestController
@RequestMapping("/api/v1/fileshares")
public class FileShareController {

	@Autowired
	private FileShareService fileShareService;

	@Autowired
	private ModelMapperService modelMapper;

	@GetMapping("/getAll")
	public List<GetAllFileShareResponse> getAllFileShares() {
		List<FileShare> fileShares = fileShareService.getAllFileShare();
		return fileShares.stream()
				.map(fileShare -> modelMapper.forResponse().map(fileShare, GetAllFileShareResponse.class))
				.collect(Collectors.toList());
	}
}
