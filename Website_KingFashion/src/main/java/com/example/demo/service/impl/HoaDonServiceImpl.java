package com.example.demo.service.impl;

import com.example.demo.entity.HoaDon;
import com.example.demo.repository.HoaDonRepository;
import com.example.demo.service.HoaDonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class HoaDonServiceImpl implements HoaDonService {

    @Autowired
    private HoaDonRepository hoaDonRepository;

    @Override
    public List<HoaDon> getAll() {
        return hoaDonRepository.findAll();
    }

    @Override
    public Page<HoaDon> phanTrangHoaDon(Integer pageNum, Integer pageNo) {
        Pageable pageable = PageRequest.of(pageNum, pageNo);
        return hoaDonRepository.findAll(pageable);
    }

    @Override
    public Page<HoaDon> search(String maHoaDon, Date ngayThanhToan, Double tongTienSauKhiGiam, Boolean trangThai, String tenNguoiNhan, Date ngayNhanDuKien, Date ngayShip, Integer size, Integer page) {
        Pageable pageable = PageRequest.of(page, size);
        return hoaDonRepository.search(maHoaDon, ngayThanhToan, tongTienSauKhiGiam, trangThai, tenNguoiNhan, ngayNhanDuKien, ngayShip, pageable);
    }

    @Override
    public List<HoaDon> getExcel() {
        return hoaDonRepository.findAll();
    }
}
