package com.example.demo.service.impl;

import com.example.demo.entity.LoaiSanPham;
import com.example.demo.repository.LoaiSanPhamRepository;
import com.example.demo.service.LoaiSanPhamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class LoaiSanPhamServiceImpl implements LoaiSanPhamService {

    @Autowired
    private LoaiSanPhamRepository loaiSanPhamRepository;

    @Override
    public List<LoaiSanPham> getAll() {
        return loaiSanPhamRepository.findAll();
    }

    @Override
    public Page<LoaiSanPham> phanTrang(Integer pageNum, Integer pageNo) {
        Pageable pageable = PageRequest.of(pageNum, pageNo);
        return loaiSanPhamRepository.findAll(pageable);
    }

    @Override
    public void add(LoaiSanPham loaiSanPham) {
        loaiSanPhamRepository.save(loaiSanPham);
    }

    @Override
    public LoaiSanPham detail(UUID id) {
        return loaiSanPhamRepository.getLoaiSanPhamById(id);
    }

    @Override
    public void delete(UUID id) {
        loaiSanPhamRepository.deleteById(id);
    }
}
