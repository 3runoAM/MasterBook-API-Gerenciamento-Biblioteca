package com.brunoAM.MasterBook_api.author.service;

import com.brunoAM.MasterBook_api.author.model.Author;
import com.brunoAM.MasterBook_api.author.repository.AuthorRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Data
@AllArgsConstructor
public class AuthorService {
    private final AuthorRepository authorRepository;

    public Author findAuthor(Long id) {
        return authorRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Author with id %d was not found".formatted(id)));
    }

    public Author findAuthorByName(String name) {
        return authorRepository.findByName(name)
                .orElseThrow(() -> new IllegalStateException("Author with name %s was not found".formatted(name)));
    }

    public List<Author> findAuthorsByBirthDate(LocalDateTime birthDate) {
        return authorRepository.findByBirthDate(birthDate)
                .orElseThrow(() -> new IllegalStateException("Authors born on %s was not found".formatted(birthDate)));
    }

    public List<Author> findAllAuthors() {
        return authorRepository.findAll();
    }

    @Transactional
    public boolean addAuthor(@Valid Author author) {
        authorRepository.findByName(author.getName())
                .ifPresentOrElse(a -> {
                    throw new IllegalStateException("Author %s already exists".formatted(author.getName()));
                }, () -> {
                    authorRepository.save(author);
                });

        return true;
    }

    @Transactional
    public boolean updateAuthor(Long id, Author author) {
        authorRepository.findById(id)
                .ifPresentOrElse(authorFounded -> {
                    if(author.getName() != null && !author.getName().isEmpty()){
                        authorFounded.setName(author.getName());
                    }
                    if(author.getBirthDate() != null && author.getBirthDate().isBefore(LocalDateTime.now())){
                        authorFounded.setBirthDate(author.getBirthDate());
                    }
                    authorRepository.save(authorFounded);
                }, () -> {
                    throw new IllegalStateException("Author with id %d not found".formatted(id));
                });
        return true;
    }

    @Transactional
    public boolean deleteAuthor(Long id) {
        authorRepository.findById(id)
                .ifPresentOrElse(author -> authorRepository.deleteById(id),
                        () -> {
                            throw new IllegalStateException("Author with id %d not found".formatted(id));
                        });
        return true;
    }
}
