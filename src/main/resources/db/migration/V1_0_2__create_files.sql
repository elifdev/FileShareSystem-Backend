CREATE TABLE files (
    id BINARY(16) NOT NULL,
    file_name VARCHAR(255) NOT NULL,
    file_path VARCHAR(255) NOT NULL,
    upload_date DATETIME NOT NULL,
    PRIMARY KEY (id)
);