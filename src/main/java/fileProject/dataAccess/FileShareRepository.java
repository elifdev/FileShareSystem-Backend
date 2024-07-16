package fileProject.dataAccess;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import fileProject.entities.FileShare;

public interface FileShareRepository extends JpaRepository<FileShare, UUID> {
}
