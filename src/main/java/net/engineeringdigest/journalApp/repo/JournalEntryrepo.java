package net.engineeringdigest.journalApp.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import net.engineeringdigest.journalApp.entity.JournalEntry;
import org.bson.types.ObjectId;

public interface JournalEntryrepo extends MongoRepository<JournalEntry, ObjectId> { // <entity, id type>

}
