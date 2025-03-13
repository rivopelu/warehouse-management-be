package com.warehouse.app.controllers.impl;

import com.warehouse.app.annotations.BaseControllerImpl;
import com.warehouse.app.controllers.MasterDataController;
import com.warehouse.app.dto.request.RequestCreateCategory;
import com.warehouse.app.dto.response.BaseResponse;
import com.warehouse.app.services.MasterDataService;
import com.warehouse.app.utilities.ResponseHelper;
import lombok.RequiredArgsConstructor;

@BaseControllerImpl
@RequiredArgsConstructor
public class MasterDataControllerImpl implements MasterDataController {

    private final MasterDataService masterDataService;
    @Override
    public BaseResponse createNewCategory(RequestCreateCategory requestCreateCategory) {
        return ResponseHelper.createBaseResponse(masterDataService.createNewCategory(requestCreateCategory));
    }
}
