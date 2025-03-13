package com.warehouse.app.services;

import com.warehouse.app.dto.request.RequestCreateCategory;
import com.warehouse.app.entities.Category;

import java.util.List;

public interface MasterDataService {


    String createNewCategory(RequestCreateCategory requestCreateCategory);

    List<Category> getAllCategories();
}
