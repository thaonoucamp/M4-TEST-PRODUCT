package com.codegym.controller;

import com.codegym.model.Category;
import com.codegym.model.Product;
import com.codegym.service.CategoryService;
import com.codegym.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @ModelAttribute("categories")
    public Iterable<Category> categories() {
        return categoryService.findAll();
    }

    @GetMapping("/list-products")
    public ModelAndView showList() {
        Iterable<Product> products = productService.findAll();
        ModelAndView modelAndView = new ModelAndView("/product/list");
        modelAndView.addObject("products", products);
        return modelAndView;
    }

    @GetMapping("/home")
    public ModelAndView home(@PageableDefault(value = 3) Pageable pageable) {
        Page<Product> products = productService.findAll(pageable);
        ModelAndView modelAndView = new ModelAndView("/product/list");
        modelAndView.addObject("products", products);
        return modelAndView;
    }

    @GetMapping("/sort")
    public ModelAndView sortByPrice(@PageableDefault(value = 3, sort = "price", direction = Sort.Direction.ASC) Pageable pageable) {
        Page<Product> products = productService.findAllByName(pageable);
        ModelAndView modelAndView = new ModelAndView("/product/list");
        modelAndView.addObject("products", products);
        return modelAndView;
    }

    @PostMapping("/search-name")
    public ModelAndView resultSearch(@RequestParam("name") Optional<String> name, Pageable pageable) {
        Page<Product> products;
        if (name.isPresent()) {
            products = productService.findAllByName(name.get(), pageable);
        } else {
            products = productService.findAll(pageable);
        }
        ModelAndView modelAndView = new ModelAndView("/product/list");
        modelAndView.addObject("products", products);
        return modelAndView;
    }

    @GetMapping("/create-product")
    public ModelAndView showFormBlog() {
        ModelAndView modelAndView = new ModelAndView("/product/create");
        modelAndView.addObject("product", new Product());
        return modelAndView;
    }

    @PostMapping("/create-product")
    public ModelAndView saveBlog(@ModelAttribute("product") Product product) {
        productService.save(product);
        ModelAndView modelAndView = new ModelAndView("/product/create");
        modelAndView.addObject("product", new Product());
        modelAndView.addObject("message", "New product created successfully");
        return modelAndView;
    }

    @GetMapping("/edit-product/{id}")
    public ModelAndView showFormView(@PathVariable Long id) {
        Optional<Product> product = productService.findById(id);
        if (product.isPresent()) {
            ModelAndView modelAndView = new ModelAndView("/product/view");
            modelAndView.addObject("product", product.get());
            return modelAndView;
        } else {
            return new ModelAndView("/error.404");
        }
    }

    @GetMapping("edit-product/{id}")
    public ModelAndView showFormEdit(@PathVariable Long id) {
        Optional<Product> product = productService.findById(id);
        if (product.isPresent()) {
            ModelAndView modelAndView = new ModelAndView("product/edit");
            modelAndView.addObject("product", product.get());
            return modelAndView;
        } else {
            return new ModelAndView("/error.404");
        }
    }

    @PutMapping("/edit-product")
    public ModelAndView updateBlog(@ModelAttribute("product") Product product) {
        productService.save(product);
        ModelAndView modelAndView = new ModelAndView("/product/edit");
        modelAndView.addObject("product", product);
        modelAndView.addObject("message", "product updated successfully");
        return modelAndView;
    }

    @GetMapping("delete-product/{id}")
    public ModelAndView showFormDelete(@PathVariable Long id) {
        Optional<Product> product = productService.findById(id);
        if (product.isPresent()) {
            ModelAndView modelAndView = new ModelAndView("/product/delete");
            modelAndView.addObject("product", product);
            return modelAndView;
        } else {
            return new ModelAndView("/error.404");
        }
    }

    @DeleteMapping("/delete-product")
    public String deleteBlog(@ModelAttribute("blog") Product product) {
        productService.remove(product.getId());
        return "redirect:/home";
    }

    @PostMapping("/view-category")
    public ModelAndView viewCategory(@RequestParam("category") Long id) {
        Optional<Category> category = categoryService.findById(id);
        if (!category.isPresent()) {
            return new ModelAndView("error.404");
        }
        Iterable<Product> products = productService.findAllByCategory(category.get());
        ModelAndView modelAndView = new ModelAndView("/category/view");
        modelAndView.addObject("category", category.get());
        modelAndView.addObject("products", products);
        return modelAndView;
    }
}