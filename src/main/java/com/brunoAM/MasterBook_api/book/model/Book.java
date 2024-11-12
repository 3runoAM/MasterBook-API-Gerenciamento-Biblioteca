package com.brunoAM.MasterBook_api.book.model;

import com.brunoAM.MasterBook_api.author.model.Author;
import com.brunoAM.MasterBook_api.category.model.Category;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @NotBlank
    @Length(min=3, max=100)
    private String title;

    @Valid
    @ManyToOne
    private Author author;

    @NotNull
    private LocalDateTime publishedAt;

    @NotNull
    @ManyToMany
    private List<@Valid Category> category;

    @NotNull
    private boolean borrowed;

    @Override
    public boolean equals(Object obj){
        if(this == obj) return true;
        if(obj == null || getClass() != obj.getClass()) return false;
        Book book = (Book) obj;
        return this.getTitle().equals(book.getTitle()) && this.getAuthor().equals(book.getAuthor());
    }
}