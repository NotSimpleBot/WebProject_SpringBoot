package com.guzanov.spring.repositories;

import com.guzanov.spring.models.Post;
import org.springframework.data.repository.CrudRepository;

public interface PostDAO extends CrudRepository<Post, Long> {

}
