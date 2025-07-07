package net.engineeringdigest.journalApp.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import net.engineeringdigest.journalApp.entity.User;
import org.bson.types.ObjectId;

public interface UserRepository extends MongoRepository<User, ObjectId> {

    User findByUserName(String userName);
 
}
