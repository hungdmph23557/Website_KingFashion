package com.example.demo.service.impl;

import com.example.demo.entity.ChiTietSanPham;
import com.example.demo.repository.AnhRepository;
import com.example.demo.repository.ChiTietSanPhamRepository;
import com.example.demo.repository.SanPhamRepository;
import com.example.demo.service.ChiTietSanPhamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ChiTietSanPhamServiceImpl implements ChiTietSanPhamService {

    @Autowired
    private ChiTietSanPhamRepository chiTietSanPhamRepository;

    @Autowired
    private SanPhamRepository sanPhamRepository;

    @Autowired
    private AnhRepository anhRepository;

    @Override
    public List<ChiTietSanPham> getAll() {
        return chiTietSanPhamRepository.findAll();
    }

    @Override
    public Page<ChiTietSanPham> PhanTrang(Integer PageNum, Integer PageNo) {
        Pageable pageable = PageRequest.of(PageNum,PageNo);
        return chiTietSanPhamRepository.phanTrang(pageable);
    }

    @Override
    public Page<ChiTietSanPham> search(String ten, Integer minTien, Integer maxTien, Integer PageNum, Integer PageNo) {
        Pageable pageable = PageRequest.of(PageNum, PageNo);
        return chiTietSanPhamRepository.search(ten, minTien, maxTien, pageable);
    }
    @Override
    public void add(ChiTietSanPham chiTietSanPham) {
        if(chiTietSanPham.getSanPham().getId() == null) {
            sanPhamRepository.save(chiTietSanPham.getSanPham());
        }

        chiTietSanPhamRepository.save(chiTietSanPham);
    }

    @Override
    public ChiTietSanPham detail(UUID id) {
        return chiTietSanPhamRepository.findById(id).orElse(null);
    }

    @Override
    public void delete(UUID id) {
        chiTietSanPhamRepository.delete(id);
    }
}
