package com.example.demo.service;

import com.example.demo.entity.HoaDon;

import org.springframework.data.domain.Page;

import java.util.List;

public interface HoaDonService {

    List<HoaDon> getAll();

    Page<HoaDon> phanTrangHoaDon(Integer pageNum, Integer pageNo);

    List<HoaDon> getExcel();
}
