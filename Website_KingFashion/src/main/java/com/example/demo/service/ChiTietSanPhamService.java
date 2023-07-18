package com.example.demo.service;

import com.example.demo.entity.ChiTietSanPham;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface ChiTietSanPhamService {
    Page<ChiTietSanPham> PhanTrang(Integer PageNum, Integer PageNo);

    void add(ChiTietSanPham chiTietSanPham);

    ChiTietSanPham detail(UUID id);

    void delete(UUID id);
}
