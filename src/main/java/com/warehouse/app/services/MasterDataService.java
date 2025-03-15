package com.warehouse.app.services;

import com.warehouse.app.dto.request.RequestCreateCategory;
import com.warehouse.app.dto.request.RequestCreateProduct;
import com.warehouse.app.dto.response.ResponseDetailProduct;
import com.warehouse.app.dto.response.ResponseListProduct;
import com.warehouse.app.entities.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MasterDataService {


    String createNewCategory(RequestCreateCategory requestCreateCategory);

    List<Category> getAllCategories();

    ResponseDetailProduct createProduct(RequestCreateProduct requestCreateProduct);

    ResponseDetailProduct editProduct(String id, RequestCreateProduct requestCreateProduct);

    Page<ResponseListProduct> getListProducts(Pageable pageable, String keyword, String categoryId);
}
