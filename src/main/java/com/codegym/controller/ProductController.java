package com.codegym.controller;

import com.codegym.exception.NotFoundException;
import com.codegym.model.Category;
import com.codegym.model.Product;
import com.codegym.service.category.ICategoryService;
import com.codegym.service.product.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;


@Controller
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private IProductService productService;
    @Autowired
    private ICategoryService categoryService;

    @ModelAttribute("categories")
    public List<Category> categories() {
        return categoryService.findALl();
    }

    @GetMapping("")
    public ModelAndView getAll(@PageableDefault(size = 3) Pageable pageable) {
        Page<Product> products = productService.findALl(pageable);
        ModelAndView modelAndView = new ModelAndView("list");
        modelAndView.addObject("products", products);
//        modelAndView.addObject("categories", categoryService.findAll());
        return modelAndView;
    }


    @GetMapping("/create")
    public ModelAndView showCreate() {
        ModelAndView modelAndView = new ModelAndView("create");
        modelAndView.addObject("product", new Product());
        return modelAndView;
    }

    @PostMapping("/create")
    public ModelAndView create(@ModelAttribute Product product) {
        long id = productService.findALl().size();
        product.setId(id);
        productService.save(product);
        ModelAndView modelAndView = new ModelAndView("create", "product", new Product());
        modelAndView.addObject("mess", "product has been added successfully " + product.getName());
        return modelAndView;
    }
    @ExceptionHandler(NotFoundException.class)
    public ModelAndView showNotFound() {
        return new ModelAndView("error-404");
    }

    @GetMapping("/edit/{id}")
    public ModelAndView showEdit(@PathVariable Long id) {
        Product products = productService.findById(id);
        ModelAndView modelAndView = new ModelAndView("edit");
        modelAndView.addObject("product", products);
        return modelAndView;
    }

    @PostMapping("/edit/{id}")
    public ModelAndView edit(@PathVariable Long id, @ModelAttribute Product product) {
        product.setId(id);
        productService.save(product);
        return new ModelAndView("redirect:/products");
    }

    @GetMapping("/delete/{id}")
    public ModelAndView delete(@PathVariable long id) {
        ModelAndView modelAndView = new ModelAndView("redirect:/products");
        productService.remove(id);
        return modelAndView;
    }

    @GetMapping("/view/{id}")
    public ModelAndView viewDetail(@PathVariable long id) {
        return new ModelAndView("detail", "product", productService.findById(id));
    }


    @PostMapping("")
    public ModelAndView searchProductByCategory(@RequestParam String name) {
        String name1 = "%" + name + "%";
        List<Product> products = productService.findByName(name1);
        if (products.size() == 0) return new ModelAndView("error-404");
        else return new ModelAndView("list", "products", products);

    }

    @PostMapping("/searchCategory")
    public ModelAndView searchProductByCategory(@RequestParam Long id) {
        List<Product> products = productService.findByCategoryName(id);
        if (products.size() == 0) return new ModelAndView("error-404");
        else
        return new ModelAndView("list", "products", products);
    }


    @GetMapping("/top5price")
    public ModelAndView top5Price(){
        ModelAndView modelAndView = new ModelAndView("list");
        modelAndView.addObject("products",productService.top5Price());
        return modelAndView;
    }
    @GetMapping("/top5newProduct")
    public ModelAndView top5newProduct(){
        ModelAndView modelAndView = new ModelAndView("list");
        modelAndView.addObject("products",productService.top5ProductNew());
        return modelAndView;
    }
    @GetMapping("/totalPrice")
    public ModelAndView sumPrice() {
        ModelAndView modelAndView = new ModelAndView("list");
        modelAndView.addObject("totalPrice", productService.totalPrice());
        modelAndView.addObject("products", productService.findALl());
        return modelAndView;
    }

}
