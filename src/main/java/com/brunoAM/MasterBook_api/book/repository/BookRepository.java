package com.brunoAM.MasterBook_api.book.repository;

import com.brunoAM.MasterBook_api.author.model.Author;
import com.brunoAM.MasterBook_api.book.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    Optional<List<Book>> findByTitle(String title);

    Optional<List<Book>> findByAuthorName(String authorName);

    Optional<List<Book>> findByCategoryName(String categoryName);
}
