package fileProject.dataAccess;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import fileProject.entities.File;

public interface FileRepository extends JpaRepository<File, UUID> {

}
