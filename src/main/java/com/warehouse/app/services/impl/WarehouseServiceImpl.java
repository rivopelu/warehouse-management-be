package com.warehouse.app.services.impl;

import com.warehouse.app.dto.request.RequestCreateWarehouse;
import com.warehouse.app.dto.response.ResponseDetailWarehouse;
import com.warehouse.app.dto.response.ResponseFullArea;
import com.warehouse.app.dto.response.ResponseListWarehouse;
import com.warehouse.app.entities.SubDistrict;
import com.warehouse.app.entities.Warehouse;
import com.warehouse.app.exception.NotFoundException;
import com.warehouse.app.exception.SystemErrorException;
import com.warehouse.app.repositories.SubDistrictRepository;
import com.warehouse.app.repositories.WarehouseRepository;
import com.warehouse.app.services.AccountService;
import com.warehouse.app.services.AreaService;
import com.warehouse.app.services.WarehouseService;
import com.warehouse.app.utilities.EntityUtils;
import com.warehouse.app.utilities.UtilsHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.warehouse.app.utilities.UtilsHelper.getMessage;

@RequiredArgsConstructor
@Service
public class WarehouseServiceImpl implements WarehouseService {

    private final SubDistrictRepository subDistrictRepository;
    private final AccountService accountService;
    private final WarehouseRepository warehouseRepository;
    private final AreaService areaService;

    @Override
    public String createWarehouse(RequestCreateWarehouse req) {
        SubDistrict subDistrict = subDistrictRepository.findById(req.getSubDistrictId()).orElseThrow(() -> new NotFoundException(getMessage("sub.district.not.found")));
        try {
            Warehouse warehouse = Warehouse.builder()
                    .address(req.getAddress())
                    .name(req.getName())
                    .subDistrict(subDistrict)
                    .build();
            EntityUtils.created(warehouse, accountService.getCurrentAccountId());
            warehouseRepository.save(warehouse);
            return getMessage("warehouse.created");
        } catch (Exception e) {
            throw new SystemErrorException(e);
        }
    }

    @Override
    public String editWarehouse(RequestCreateWarehouse req, String id) {
        Warehouse warehouse = warehouseRepository.findByIdAndActiveIsTrue(id).orElseThrow(() -> new NotFoundException(getMessage("warehouse.not.found")));
        SubDistrict subDistrict = subDistrictRepository.findById(req.getSubDistrictId()).orElseThrow(() -> new NotFoundException(getMessage("sub.district.not.found")));
        try {
            warehouse.setName(req.getName());
            warehouse.setAddress(req.getAddress());
            warehouse.setSubDistrict(subDistrict);
            EntityUtils.updated(warehouse, accountService.getCurrentAccountId());
            warehouseRepository.save(warehouse);
            return getMessage("warehouse.updated");
        } catch (Exception e) {
            throw new SystemErrorException(e);
        }
    }

    @Override
    public Page<ResponseListWarehouse> listWarehouse(Pageable pageable) {
        PageRequest pageRequest = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(Sort.Direction.DESC, "updatedDate"));
        Page<Warehouse> warehousePage = warehouseRepository.findAll(pageRequest);
        List<ResponseListWarehouse> responseListWarehouses = new ArrayList<>();
        try {
            for (Warehouse warehouse : warehousePage.getContent()) {
                ResponseListWarehouse responseListWarehouse = ResponseListWarehouse.builder()
                        .name(warehouse.getName())
                        .createdDate(warehouse.getCreatedDate())
                        .address(warehouse.getAddress())
                        .build();
                responseListWarehouses.add(responseListWarehouse);
            }
            return new PageImpl<>(responseListWarehouses, pageable, warehousePage.getTotalElements());
        } catch (Exception e) {
            throw new SystemErrorException(e);
        }
    }

    @Override
    public ResponseDetailWarehouse detailWarehouse(String id) {
        Warehouse warehouse = warehouseRepository.findByIdAndActiveIsTrue(id).orElseThrow(() -> new NotFoundException(getMessage("warehouse.not.found")));
        ResponseFullArea area = areaService.findAreaBySubDistrictId(warehouse.getSubDistrict().getId());

        try {
            return ResponseDetailWarehouse.builder()
                    .cityId(area.getCity().getId())
                    .cityName(area.getCity().getName())
                    .districtId(area.getDistrict().getId())
                    .districtName(area.getDistrict().getName())
                    .subDistrictId(area.getSubDistrict().getId())
                    .provinceId(area.getProvince().getId())
                    .provinceName(area.getProvince().getName())
                    .name(warehouse.getName())
                    .address(warehouse.getAddress())
                    .build();
        } catch (Exception e) {
            throw new SystemErrorException(e);
        }
    }
}
