package fileProject.business.concretes;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fileProject.business.abstracts.FileShareService;
import fileProject.dataAccess.FileShareRepository;
import fileProject.entities.FileShare;

@Service
public class FileShareManager implements FileShareService {

	@Autowired
	private FileShareRepository fileShareRepository;

	@Override
	public List<FileShare> getAllFileShare() {
		return fileShareRepository.findAll();

	}

}
