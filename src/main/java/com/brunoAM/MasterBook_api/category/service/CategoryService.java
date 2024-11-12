package com.brunoAM.MasterBook_api.category.service;

import com.brunoAM.MasterBook_api.category.model.Category;
import com.brunoAM.MasterBook_api.category.repository.CategoryRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Data
@AllArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public Category findCategoryById(Long id){
        return categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Category with id %d was not found".formatted(id)));
    }

    public Category findCategoryByName(String name){
        return categoryRepository.findByName(name)
                .orElseThrow(() -> new IllegalStateException("Category with name %s was not found".formatted(name)));
    }

    public List<Category> findAllCategories(){
        return categoryRepository.findAll();
    }

    @Transactional
    public boolean addCategory(@Valid Category category){
        categoryRepository.findByName(category.getName())
                .ifPresentOrElse(c -> {
                    throw new IllegalStateException("Category %s already exists".formatted(category.getName()));
                }, () -> categoryRepository.save(category));
        return true;
    }

    @Transactional
    public boolean updateCategory(Long id, Category category){
        categoryRepository.findById(id)
                .ifPresentOrElse(categoryFounded -> {
                    if(category.getName() != null && !category.getName().isEmpty()){
                        categoryFounded.setName(category.getName());
                    }
                    categoryRepository.save(categoryFounded);
                }, () -> {
                    throw new IllegalStateException("Category with id %d not found".formatted(category.getId()));
                });
        return true;
    }

    @Transactional
    public boolean deleteCategory(Long id){
        categoryRepository.findById(id)
                .ifPresentOrElse(categoryFounded -> categoryRepository.deleteById(id),
                        () -> {
                            throw new IllegalStateException("Category with id %d not found".formatted(id));
                        });
        return true;
    }
}
