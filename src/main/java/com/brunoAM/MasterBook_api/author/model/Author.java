package com.brunoAM.MasterBook_api.author.model;

import com.brunoAM.MasterBook_api.book.model.Book;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @NotBlank
    @Length(min=3, max=100)
    private String name;

    @Past
    private LocalDateTime birthDate;

    @OneToMany(mappedBy = "author")
    private List<@Valid Book> books;

    @PrePersist
    private void onCreate(){
        this.books = new ArrayList<>();
    }
}
