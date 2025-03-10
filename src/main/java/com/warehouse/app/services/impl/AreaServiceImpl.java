package com.warehouse.app.services.impl;

import com.warehouse.app.dto.response.ResponseAreaData;
import com.warehouse.app.dto.response.ResponseFullArea;
import com.warehouse.app.entities.City;
import com.warehouse.app.entities.District;
import com.warehouse.app.entities.Province;
import com.warehouse.app.entities.SubDistrict;
import com.warehouse.app.exception.NotFoundException;
import com.warehouse.app.exception.SystemErrorException;
import com.warehouse.app.repositories.CityRepository;
import com.warehouse.app.repositories.DistrictRepository;
import com.warehouse.app.repositories.ProvinceRepository;
import com.warehouse.app.repositories.SubDistrictRepository;
import com.warehouse.app.services.AreaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AreaServiceImpl implements AreaService {

    private final ProvinceRepository provinceRepository;
    private final CityRepository cityRepository;
    private final DistrictRepository districtRepository;
    private final SubDistrictRepository subDistrictRepository;

    @Override
    public List<ResponseAreaData> getProvinceList() {
        List<Province> provinceList = provinceRepository.findAll();
        List<ResponseAreaData> responseAreaDataList = new ArrayList<>();
        try {
            for (Province province : provinceList) {
                ResponseAreaData responseAreaData = ResponseAreaData.builder()
                        .id(province.getId())
                        .name(province.getName())
                        .build();
                responseAreaDataList.add(responseAreaData);

            }
            return responseAreaDataList;
        } catch (Exception e) {
            throw new SystemErrorException(e);
        }
    }

    @Override
    public List<ResponseAreaData> getCityList(BigInteger provinceId) {
        List<City> cityList = cityRepository.findByProvinceId(provinceId);
        List<ResponseAreaData> responseAreaDataList = new ArrayList<>();
        try {
            for (City city : cityList) {
                ResponseAreaData responseAreaData = ResponseAreaData.builder()
                        .name(city.getName())
                        .id(city.getId())
                        .build();
                responseAreaDataList.add(responseAreaData);
            }
            return responseAreaDataList;
        } catch (Exception e) {
            throw new SystemErrorException(e);
        }
    }

    @Override
    public List<ResponseAreaData> getDistrictList(BigInteger cityId) {

        List<District> districtList = districtRepository.findByCityId(cityId);
        List<ResponseAreaData> responseAreaDataList = new ArrayList<>();

        try {
            for (District district : districtList) {
                ResponseAreaData res = ResponseAreaData.builder()
                        .name(district.getName())
                        .id(district.getId())
                        .build();
                responseAreaDataList.add(res);
            }
            return responseAreaDataList;
        } catch (Exception e) {
            throw new SystemErrorException(e);
        }
    }

    @Override
    public List<ResponseAreaData> getSubDistrictList(BigInteger districtId) {

        List<SubDistrict> subDistrictList = subDistrictRepository.findByDistrictId(districtId);
        List<ResponseAreaData> responseAreaDataList = new ArrayList<>();

        try {
            for (SubDistrict subDistrict : subDistrictList) {
                ResponseAreaData responseAreaData = ResponseAreaData.builder()
                        .name(subDistrict.getName())
                        .id(subDistrict.getId())
                        .build();
                responseAreaDataList.add(responseAreaData);
            }
            return responseAreaDataList;
        } catch (Exception e) {
            throw new SystemErrorException(e);
        }
    }

    @Override
    public ResponseFullArea findAreaBySubDistrictId(BigInteger subDistrictId) {
        SubDistrict subDistrict = subDistrictRepository.findById(subDistrictId).orElseThrow(() -> new NotFoundException("not.found.subdistrict"));
        try {
            return ResponseFullArea.builder()
                    .build();
        }catch (Exception e) {
            throw new SystemErrorException(e);
        }
    }
}
