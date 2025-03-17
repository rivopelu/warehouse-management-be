package com.warehouse.app.controllers.impl;

import com.warehouse.app.annotations.BaseControllerImpl;
import com.warehouse.app.controllers.MasterDataController;
import com.warehouse.app.dto.request.RequestCreateCategory;
import com.warehouse.app.dto.request.RequestCreateProduct;
import com.warehouse.app.dto.request.RequestCreateVariant;
import com.warehouse.app.dto.response.BaseResponse;
import com.warehouse.app.services.MasterDataService;
import com.warehouse.app.utilities.ResponseHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;

@BaseControllerImpl
@RequiredArgsConstructor
public class MasterDataControllerImpl implements MasterDataController {

    private final MasterDataService masterDataService;
    @Override
    public BaseResponse createNewCategory(RequestCreateCategory requestCreateCategory) {
        return ResponseHelper.createBaseResponse(masterDataService.createNewCategory(requestCreateCategory));
    }

    @Override
    public BaseResponse getAllCategories() {
        return ResponseHelper.createBaseResponse(masterDataService.getAllCategories());
    }

    @Override
    public BaseResponse createProduct(RequestCreateProduct requestCreateProduct) {

        return ResponseHelper.createBaseResponse(masterDataService.createProduct(requestCreateProduct));
    }

    @Override
    public BaseResponse editProduct(String id, RequestCreateProduct requestCreateProduct) {
        return ResponseHelper.createBaseResponse(masterDataService.editProduct(id, requestCreateProduct));
    }

    @Override
    public BaseResponse getListProducts(Pageable pageable, String keyword, String categoryId) {
        return ResponseHelper.createBaseResponse(masterDataService.getListProducts(pageable, keyword, categoryId));
    }

    @Override
    public BaseResponse getDetailProduct(String id) {
        return ResponseHelper.createBaseResponse(masterDataService.getDetailProduct(id));
    }

    @Override
    public BaseResponse createVariant(RequestCreateVariant requestCreateVariant) {

        return ResponseHelper.createBaseResponse(masterDataService.createVariant(requestCreateVariant));
    }

    @Override
    public BaseResponse getListVariants(String id) {
        return ResponseHelper.createBaseResponse(masterDataService.getListVariants(id));
    }
}
