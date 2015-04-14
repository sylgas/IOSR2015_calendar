package pl.edu.agh.student.db.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface Repository<T> extends MongoRepository<T, String> {
}
