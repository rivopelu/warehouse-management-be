package com.warehouse.app.services.impl;

import com.warehouse.app.dto.request.RequestCreateCategory;
import com.warehouse.app.dto.request.RequestCreateProduct;
import com.warehouse.app.dto.request.RequestCreateVariant;
import com.warehouse.app.dto.response.ResponseDetailProduct;
import com.warehouse.app.dto.response.ResponseListProduct;
import com.warehouse.app.dto.response.ResponseListProductVariant;
import com.warehouse.app.entities.*;
import com.warehouse.app.exception.BadRequestException;
import com.warehouse.app.exception.NotFoundException;
import com.warehouse.app.exception.SystemErrorException;
import com.warehouse.app.repositories.*;
import com.warehouse.app.services.AccountService;
import com.warehouse.app.services.MasterDataService;
import com.warehouse.app.utilities.EntityUtils;
import com.warehouse.app.utilities.UtilsHelper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MasterDataServiceImpl implements MasterDataService {
    private final CategoryRepository categoryRepository;
    private final AccountService accountService;
    private final ProductRepository productRepository;
    private final VariantProductRepository variantProductRepository;
    private final UnitTypeRepository unitTypeRepository;
    private final ProductVariantUnitRepository productVariantUnitRepository;

    @Override
    public String createNewCategory(RequestCreateCategory requestCreateCategory) {
        List<String> slugs = new ArrayList<>();
        for (String name : requestCreateCategory.getName()) {
            slugs.add(UtilsHelper.generateUniqueSlug(name));
        }
        Optional<Category> findCategory = categoryRepository.findAllBySlug(slugs);
        if (findCategory.isPresent()) {
            throw new BadRequestException("Category with name " + findCategory.get().getName() + " already exists");
        }

        try {
            List<Category> categories = new ArrayList<>();
            for (String name : requestCreateCategory.getName()) {
                String slug = UtilsHelper.generateUniqueSlug(name);
                Category category = Category.builder()
                        .name(name)
                        .slug(slug)
                        .build();
                categories.add(category);
            }
            categoryRepository.saveAll(categories);
            return UtilsHelper.getMessage("category.created");
        } catch (Exception e) {
            throw new SystemErrorException(e);
        }
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public ResponseDetailProduct createProduct(RequestCreateProduct requestCreateProduct) {
        Category category = categoryRepository.findById(requestCreateProduct.getCategoryId()).orElseThrow(() -> new NotFoundException(UtilsHelper.getMessage("not.found.category")));
        String slug = UtilsHelper.generateUniqueSlug(requestCreateProduct.getName());
        boolean checkExistedProduct = productRepository.existsBySlugAndActiveIsTrue(slug);
        if (checkExistedProduct) {
            throw new BadRequestException("Product with name " + requestCreateProduct.getName() + " already exists");
        }
        Product product = Product.builder()
                .name(requestCreateProduct.getName())
                .slug(slug)
                .category(category)
                .imageUrl(requestCreateProduct.getImageUrl())
                .build();
        EntityUtils.created(product, accountService.getCurrentAccountId());
        try {
            productRepository.save(product);
            return buildDetailProduct(product);
        } catch (Exception e) {
            throw new SystemErrorException(e);
        }
    }

    @Override
    public ResponseDetailProduct editProduct(String id, RequestCreateProduct requestCreateProduct) {
        String slug = UtilsHelper.generateUniqueSlug(requestCreateProduct.getName());
        Product product = productRepository.findById(id).orElseThrow(() -> new NotFoundException("Product not found"));
        if (!product.getSlug().equals(slug)) {
            boolean checkExistedProduct = productRepository.existsBySlugAndActiveIsTrue(slug);
            if (checkExistedProduct) {
                throw new BadRequestException("Product with name " + requestCreateProduct.getName() + " already exists");
            }
        }

        product.setName(requestCreateProduct.getName());
        product.setCategory(categoryRepository.findById(requestCreateProduct.getCategoryId()).orElseThrow(() -> new NotFoundException("Category not found")));
        product.setImageUrl(requestCreateProduct.getImageUrl());
        EntityUtils.updated(product, accountService.getCurrentAccountId());
        try {
            productRepository.save(product);
            return buildDetailProduct(product);
        } catch (Exception e) {
            throw new SystemErrorException(e);
        }
    }

    @Override
    public Page<ResponseListProduct> getListProducts(Pageable pageable, String keyword, String categoryId) {
        PageRequest pageRequest = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.Direction.DESC, "createdDate");
        Page<Product> productPage = productRepository.findListAndFilter(pageRequest, keyword, categoryId);
        List<ResponseListProduct> productsList = buildListProducts(productPage.getContent());
        try {
            return new PageImpl<>(productsList, pageable, productPage.getTotalElements());
        } catch (Exception e) {
            throw new SystemErrorException(e);
        }
    }

    @Override
    public ResponseDetailProduct getDetailProduct(String id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new NotFoundException("Product not found"));
        try {
            return buildDetailProduct(product);
        } catch (Exception e) {
            throw new SystemErrorException(e);
        }
    }

    @Transactional
    @Override
    public String createVariant(RequestCreateVariant requestCreateVariant) {
        Product product = productRepository.findById(requestCreateVariant.getProductId()).orElseThrow(() -> new NotFoundException("Product not found"));
        String slug = UtilsHelper.generateUniqueSlug(requestCreateVariant.getName());
        String currentAccountId = accountService.getCurrentAccountId();
        Optional<VariantProduct> findVariant = variantProductRepository.findBySlugOrUniqueCodeAndActiveIsTrue(slug, requestCreateVariant.getUniqueCode());

        if (findVariant.isPresent()) {
            if (findVariant.get().getSlug().equals(slug)) {
                throw new BadRequestException("Variant with name " + requestCreateVariant.getName() + " already exists");
            }
            if (findVariant.get().getUniqueCode().equals(requestCreateVariant.getUniqueCode())) {
                throw new BadRequestException("unique code for " + requestCreateVariant.getName() + " already exists");
            }
        }


        VariantProduct buildVariantProduct = VariantProduct.builder()
                .product(product)
                .name(requestCreateVariant.getName())
                .description(requestCreateVariant.getDescription())
                .slug(slug)
                .imageUrl(requestCreateVariant.getImageUrl())
                .uniqueCode(requestCreateVariant.getUniqueCode())
                .build();
        EntityUtils.created(buildVariantProduct, currentAccountId);
        VariantProduct variantProduct = variantProductRepository.save(buildVariantProduct);
        int idx = 0;
        boolean isHasMainParent = false;
        List<ProductVariantUnit> productVariantUnitList = new ArrayList<>();
        for (RequestCreateVariant.Units units : requestCreateVariant.getUnits()) {
            UnitType unit =  unitTypeRepository.findById(units.getTypeId()).orElseThrow(() -> new NotFoundException("Unit type not found"));
            UnitType parentUnit  = null;
            if (units.getParentId() != null) {
                parentUnit =  unitTypeRepository.findById(units.getParentId()).orElse(null);
            }
            if (units.getParentId() == null){
                if (!isHasMainParent){
                    isHasMainParent = true;
                } else{
                    throw new BadRequestException("Parent unit id "  + " is already exists");
                }
            }
            Boolean isMainParent = parentUnit == null;
            ProductVariantUnit productVariantUnit = ProductVariantUnit.builder()
                    .unit(unit)
                    .parentUnit(parentUnit)
                    .variantProduct(variantProduct)
                    .quantity(units.getValue())
                    .count(idx)
                    .isMainParent(isMainParent)
                    .build();

            EntityUtils.created(productVariantUnit, currentAccountId);
            idx = idx + 1;
            productVariantUnitList.add(productVariantUnit);
        }
        try {

            productVariantUnitRepository.saveAll(productVariantUnitList);
            return "success";
        } catch (Exception e) {
            throw new SystemErrorException(e);
        }
    }

    @Override
    public List<ResponseListProductVariant> getListVariants(String id) {
        List<VariantProduct> variantProducts = variantProductRepository.findByProductId(id);
        List<ResponseListProductVariant> responseListProductVariantList = new ArrayList<>();
        try {

            for (VariantProduct variantProduct : variantProducts) {

                List<ResponseListProductVariant.Units> unitsList = new ArrayList<>();

                for (ProductVariantUnit productVariantUnit : variantProduct.getVariantUnits()){
                    ResponseListProductVariant.Units units = ResponseListProductVariant.Units.builder()
                            .type(productVariantUnit.getUnit().getName())
                            .parentType(productVariantUnit.getParentUnit() != null ? productVariantUnit.getParentUnit().getName() : null)
                            .quantity(productVariantUnit.getQuantity())
                            .isMainParent(productVariantUnit.getIsMainParent())
                            .count(productVariantUnit.getCount())
                            .build();
                    unitsList.add(units);
                }

                unitsList.sort(Comparator.comparing(ResponseListProductVariant.Units::getCount));

                ResponseListProductVariant responseListProductVariant = ResponseListProductVariant.builder()
                        .name(variantProduct.getName())
                        .id(variantProduct.getId())
                        .uniqueCode(variantProduct.getUniqueCode())
                        .imageUrl(variantProduct.getImageUrl())
                        .description(variantProduct.getDescription())
                        .units(unitsList)
                        .build();
                responseListProductVariantList.add(responseListProductVariant);
            }
            return  responseListProductVariantList;
        }catch (Exception e){
            throw new SystemErrorException(e);
        }
    }

    private List<ResponseListProduct> buildListProducts(List<Product> products) {
        List<ResponseListProduct> responseListProducts = new ArrayList<>();
        for (Product product : products) {
            ResponseListProduct responseListProduct = ResponseListProduct.builder()
                    .id(product.getId())
                    .name(product.getName())
                    .slug(product.getSlug())
                    .imageUrl(product.getImageUrl())
                    .categoryId(product.getCategory().getId())
                    .categoryName(product.getCategory().getName())
                    .build();
            responseListProducts.add(responseListProduct);
        }
        return responseListProducts;
    }

    private ResponseDetailProduct buildDetailProduct(Product product) {
        return ResponseDetailProduct.builder()
                .id(product.getId())
                .name(product.getName())
                .imageUrl(product.getImageUrl())
                .categoryId(product.getCategory().getId())
                .categoryName(product.getCategory().getName())
                .build();
    }

}
