package com.warehouse.app.controllers.impl;

import com.warehouse.app.annotations.BaseControllerImpl;
import com.warehouse.app.controllers.UtilsController;
import com.warehouse.app.dto.response.BaseResponse;
import com.warehouse.app.utilities.ResponseHelper;

@BaseControllerImpl
public class UtilsControllerImpl implements UtilsController {
    @Override
    public BaseResponse ping() {
        return ResponseHelper.createBaseResponse("Pong");
    }
}
