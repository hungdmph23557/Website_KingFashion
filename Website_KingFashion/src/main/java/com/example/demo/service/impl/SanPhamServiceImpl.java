package com.example.demo.service.impl;

import com.example.demo.entity.LoaiSanPham;
import com.example.demo.entity.SanPham;
import com.example.demo.repository.SanPhamRepository;
import com.example.demo.service.SanPhamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
@Service
public class SanPhamServiceImpl implements SanPhamService {

    @Autowired
    private SanPhamRepository sanPhamRepository;

    @Override
    public Page<SanPham> phanTrang(Integer pageNum, Integer pageNo) {
        Pageable pageable = PageRequest.of(pageNum, pageNo);
        return sanPhamRepository.findAll(pageable);
    }

    @Override
    public void add(SanPham sanPham) {
        sanPhamRepository.save(sanPham);
    }

    @Override
    public SanPham detail(UUID id) {
        return sanPhamRepository.getSanPhamById(id);
    }

    @Override
    public void delete(UUID id) {
        sanPhamRepository.deleteById(id);
    }

    @Override
    public List<SanPham> getAll() {
        return sanPhamRepository.findAll();
    }
}
