package com.learnsphere.learnshpere.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.learnsphere.learnshpere.model.ForumPost;
// import com.learnsphere.learnshpere.model.Comment;
// import lombok.Data;


public interface ForumRepository extends MongoRepository<ForumPost, String> {

}
