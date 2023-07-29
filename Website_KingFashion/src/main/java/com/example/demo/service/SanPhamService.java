package com.example.demo.service;

import com.example.demo.entity.SanPham;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
@Service
public interface SanPhamService {
    Page<SanPham> phanTrang(Integer pageNum, Integer pageNo);

    void add(SanPham sanPham);

    SanPham detail(UUID id);

    void delete(UUID id);

    List<SanPham> getAll();

}
