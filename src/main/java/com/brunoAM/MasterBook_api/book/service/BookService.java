package com.brunoAM.MasterBook_api.book.service;

import com.brunoAM.MasterBook_api.book.model.Book;
import com.brunoAM.MasterBook_api.book.repository.BookRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Data
@AllArgsConstructor
public class BookService {
    private final BookRepository bookRepository;

    public Book findBookById(Long id){
        return bookRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException(("Book with id %d was not found").formatted(id)));
    }

    public List<Book> findBooksByTitle(String title){
        return bookRepository.findByTitle(title)
                .orElseThrow(() -> new IllegalStateException("Book with title %s was not found".formatted(title)));
    }

    public List<Book> findBooksByAuthor(String authorsName){
        return bookRepository.findByAuthorName(authorsName)
                .orElseThrow(() -> new IllegalStateException("Books written by author %s was not found".formatted(authorsName)));
    }

    public List<Book> findBooksByCategory(String categoryName){
        return bookRepository.findByCategoryName(categoryName)
                .orElseThrow(() -> new IllegalStateException("Books with category %s was not found".formatted(categoryName)));
    }

    public List<Book> findAllBooks(){
        return bookRepository.findAll();
    }

    @Transactional
    public boolean addBook(@Valid Book book){
        bookRepository.findByTitle(book.getTitle())
                .ifPresent(b -> {
                    b.stream()
                            .filter(book1 -> book1.getAuthor().equals(book.getAuthor()))
                            .findAny()
                            .ifPresentOrElse(book1 -> {
                                throw new IllegalStateException("Book %s already exists".formatted(book.getTitle()));
                            }, () -> bookRepository.save(book));
                });
        return true;
    }

    @Transactional
    public boolean updateBook(Long id, Book book){
        bookRepository.findById(id)
                .ifPresentOrElse(foundedBook -> {
                    if(book.getTitle() != null && !book.getTitle().isEmpty()){
                        foundedBook.setTitle(book.getTitle());
                    }
                    if(book.getAuthor() != null && book.getAuthor().getId() != null){
                        foundedBook.setAuthor(book.getAuthor());
                    }
                    if(book.getPublishedAt() != null && book.getPublishedAt().isBefore(LocalDateTime.now())){
                        foundedBook.setPublishedAt(book.getPublishedAt());
                    }
                    if(book.getCategory() != null && !book.getCategory().isEmpty()){
                        foundedBook.setCategory(book.getCategory());
                    }
                    if(book.isBorrowed() == foundedBook.isBorrowed()) {
                        foundedBook.setBorrowed(book.isBorrowed());
                    }
                    bookRepository.save(foundedBook);
                }, () -> {
                    throw new IllegalStateException("Book with id %d not found".formatted(book.getId()));
                });
        return true;
    }

    @Transactional
    public boolean borrowBook(Long id){
        bookRepository.findById(id)
                .ifPresentOrElse(book -> {
                    if(book.isBorrowed()){
                        throw new IllegalStateException("Book with id %d is already borrowed".formatted(id));
                    }
                    book.setBorrowed(true);
                    bookRepository.save(book);
                }, () -> {
                    throw new IllegalStateException("Book with id %d was not found".formatted(id));
                });

        return true;
    }
}