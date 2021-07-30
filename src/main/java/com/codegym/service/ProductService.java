package com.codegym.service;

import com.codegym.GeneralService;
import com.codegym.model.Category;
import com.codegym.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface ProductService extends GeneralService<Product> {
    Page<Product> findAll(Pageable pageable);
    Page<Product> findAllOrOrderByDate(Pageable pageable);

    Page<Product> findAllByTitleContaining(String title, Pageable pageable);

    Iterable<Product> findAllByCategory(Category category);
}