package com.brunoAM.MasterBook_api.author.repository;

import com.brunoAM.MasterBook_api.author.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
    Optional<Author> findByName(String name);

    Optional<List<Author>> findByBirthDate(LocalDateTime birthDate);
}
