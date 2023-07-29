package com.example.demo.service;

import com.example.demo.entity.HoaDon;

import org.springframework.data.domain.Page;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public interface HoaDonService {

    List<HoaDon> getAll();

    Page<HoaDon> phanTrangHoaDon(Integer pageNum, Integer pageNo);


    List<HoaDon> getExcel();
    HoaDon detail(UUID id );

    void  add(HoaDon hoaDon);
}
