package com.brunoAM.MasterBook_api.category.model;

import com.brunoAM.MasterBook_api.book.model.Book;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private final Long id;

    @NotBlank
    @Length(min=3, max=100)
    private String name;

    @NotNull
    @ManyToMany(mappedBy = "category")
    private List<@Valid Book> book;
}