package com.warehouse.app.controllers;

import com.warehouse.app.annotations.BaseController;
import com.warehouse.app.dto.request.RequestCreateCategory;
import com.warehouse.app.dto.request.RequestCreateProduct;
import com.warehouse.app.dto.response.BaseResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@BaseController("master-data")
public interface MasterDataController {

    @PostMapping("v1/category/new")
    BaseResponse createNewCategory(@RequestBody  RequestCreateCategory requestCreateCategory);

    @GetMapping("v1/category/list")
    BaseResponse getAllCategories();


    @PostMapping("v1/product/new")
    BaseResponse createProduct(@RequestBody RequestCreateProduct requestCreateProduct);


    @PutMapping("v1/product/edit/{id}")
    BaseResponse editProduct(@PathVariable("id") String id, @RequestBody RequestCreateProduct requestCreateProduct);

    @GetMapping("v1/product/list")
    BaseResponse getListProducts(
            Pageable pageable,
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "category", required = false) String categoryId
    );

    @GetMapping("v1/product/detail/{id}")
    BaseResponse getDetailProduct(@PathVariable("id") String id);
}
