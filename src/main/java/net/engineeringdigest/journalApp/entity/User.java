package net.engineeringdigest.journalApp.entity;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.NonNull;

@Document(collection = "user")
@Data

public class User {
    
    @Id
    private ObjectId id;
    
    @Indexed(unique = true)
    @NonNull
    private String userName;
    @NonNull    
    private String password;
    private List<JournalEntry> journal_entries = new ArrayList<>();
}
