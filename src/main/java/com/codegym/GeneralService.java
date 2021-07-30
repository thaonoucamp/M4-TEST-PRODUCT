package com.codegym;

import com.codegym.model.Category;

import java.util.Optional;

public interface GeneralService<T> {

    Iterable<Category> findAll();

    Optional<Category> findById(Long id);

    void save(T t);

    void remove(Long id);
}