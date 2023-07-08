package com.example.demo.service;

import com.example.demo.entity.LoaiSanPham;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface LoaiSanPhamService {

    Page<LoaiSanPham> phanTrang(Integer pageNum, Integer pageNo);

    void add(LoaiSanPham loaiSanPham);

    LoaiSanPham detail(UUID id);

    void delete(UUID id);
}
