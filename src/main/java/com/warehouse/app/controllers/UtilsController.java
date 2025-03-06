package com.warehouse.app.controllers;

import com.warehouse.app.annotations.BaseController;
import com.warehouse.app.annotations.PublicAccess;
import com.warehouse.app.dto.response.BaseResponse;
import org.springframework.web.bind.annotation.GetMapping;

@BaseController
public interface UtilsController {

    @PublicAccess
    @GetMapping("/ping")
    BaseResponse ping();
}
