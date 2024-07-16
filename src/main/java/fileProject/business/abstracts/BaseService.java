package fileProject.business.abstracts;

import java.util.UUID;

public interface BaseService<T> {

	T create(T entity);

	T update(T entity);

	void delete(UUID id);

}
