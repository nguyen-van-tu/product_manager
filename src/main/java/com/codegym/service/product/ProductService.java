package com.codegym.service.product;

import com.codegym.model.Category;
import com.codegym.model.Product;
import com.codegym.repository.IProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService implements IProductService {
    @Autowired
    private IProductRepository productRepository;

    @Override
    public List<Product> findALl() {
        return (List<Product>) productRepository.findAll();
    }

    @Override
    public Page<Product> findALl(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    @Override
    public Product findById(Long id) {
        return productRepository.findOne(id);
    }

    @Override
    public Product save(Product product) {
        return productRepository.save(product);
    }

    @Override
    public void remove(Long id) {
        productRepository.delete(id);
    }

    @Override
    public List<Product> findByName(String name) {
        return productRepository.findProductName(name);
    }

    @Override
    public List<Product> top5Price() {
        return productRepository.findByTopPrice(5);
    }

    @Override
    public List<Product> top5ProductNew() {
        return productRepository.findByTopNewProduct(5);
    }

    @Override
    public Long totalPrice() {
        return productRepository.totalPrice();
    }


    @Override
    public List<Product> findByCategoryName(Long id) {
        return productRepository.findProductByCategoryName(id);
    }
}
