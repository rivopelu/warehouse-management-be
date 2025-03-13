package com.warehouse.app.services.impl;

import com.warehouse.app.dto.request.RequestCreateCategory;
import com.warehouse.app.entities.Category;
import com.warehouse.app.exception.BadRequestException;
import com.warehouse.app.exception.SystemErrorException;
import com.warehouse.app.repositories.CategoryRepository;
import com.warehouse.app.services.MasterDataService;
import com.warehouse.app.utilities.UtilsHelper;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MasterDataServiceImpl implements MasterDataService {
    private final CategoryRepository categoryRepository;

    public MasterDataServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public String createNewCategory(RequestCreateCategory requestCreateCategory) {
        List<String> slugs = new ArrayList<>();
        for (String name : requestCreateCategory.getName()){
            slugs.add(UtilsHelper.generateUniqueSlug(name));
        }
        Optional<Category> findCategory = categoryRepository.findAllBySlug(slugs);
        if (findCategory.isPresent()) {
            throw new BadRequestException("Category with name " + findCategory.get().getName() + " already exists");
        }

        try {
            List<Category> categories = new ArrayList<>();
            for (String name : requestCreateCategory.getName()){
                String slug = UtilsHelper.generateUniqueSlug(name);
                Category category = Category.builder()
                        .name(name)
                        .slug(slug)
                        .build();
                categories.add(category);
            }
            categoryRepository.saveAll(categories);
            return UtilsHelper.getMessage("category.created");
        }catch (Exception e){
            throw new SystemErrorException(e);
        }
    }

    @Override
     public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

}
