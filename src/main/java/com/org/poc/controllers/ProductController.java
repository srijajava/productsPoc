package com.org.poc.controllers;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.org.poc.entities.Product;
import com.org.poc.repositories.ProductRepository;

@Controller
public class ProductController {
	
	private final static Logger log = LoggerFactory.getLogger(ProductController.class);
    
    private final ProductRepository productRepository;

    @Autowired
    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
    @GetMapping("/index")
    public String showProductsList(Model model) {
    	
    	log.info("showProductsList: Request came to view products list");
    	  model.addAttribute("products", productRepository.findAll());
          return "index";
    }
    
    @GetMapping("/signup")
    public String showSignUpForm(Product product) {
        return "add-product";
    }
    
    @PostMapping("/addProduct")
    public String addProduct(@Valid Product product, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "add-product";
        }
        log.info("addProduct: Adding product={}",product);
        productRepository.save(product);
        model.addAttribute("products", productRepository.findAll());
        return "index";
    }
    
    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        Product product = productRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid Product Id:" + id));
        model.addAttribute("product", product);
        log.info("showUpdateForm: Update product={}",product);
        return "update-product";
    }
    
    @PostMapping("/update/{id}")
    public String updateProduct(@PathVariable("id") long id, @Valid Product product, BindingResult result, Model model) {
        if (result.hasErrors()) {
        	product.setId(id);
            return "update-product";
        }
        log.info("updateProduct: Update product={}",product);
        productRepository.save(product);
        model.addAttribute("products", productRepository.findAll());
        return "index";
    }
    
    @GetMapping("/delete/{id}")
    public String deleteProducts(@PathVariable("id") long id, Model model) {
        Product product = productRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid Product Id:" + id));
        productRepository.delete(product);
        log.info("deleteProducts: Deleted product={}",product);
        model.addAttribute("products", productRepository.findAll());
        return "index";
    }
}
