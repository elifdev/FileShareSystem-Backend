package fileProject.dataAccess;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import fileProject.entities.File;
import fileProject.entities.User;

public interface FileRepository extends JpaRepository<File, UUID> {
	List<File> findByUser(User user);
}
