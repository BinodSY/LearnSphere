package com.learnsphere.learnshpere.repositories;

import java.util.List;
import org.springframework.data.domain.Pageable;
import com.learnsphere.learnshpere.model.Content;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ContentRepository extends MongoRepository<Content, String> {
      List<Content> findByTagsIn(List<String> tags, Pageable pageable);
}
