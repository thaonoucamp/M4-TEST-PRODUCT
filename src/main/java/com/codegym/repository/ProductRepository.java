package com.codegym.repository;

import com.codegym.model.Category;
import com.codegym.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends PagingAndSortingRepository<Product, Long> {
    @Query("select p from Product p order by p.name asc ")
    Page<Product> findAllOrOrderByDate(Pageable pageable);

    Page<Product> findAllByTitleContaining(String title, Pageable pageable);

    Iterable<Product> findAllByCategory(Category category);
}