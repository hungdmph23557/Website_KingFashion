package com.example.demo.service;

import com.example.demo.entity.HoaDon;

import org.springframework.data.domain.Page;

import java.util.Date;
import java.util.List;

public interface HoaDonService {

    List<HoaDon> getAll();

    Page<HoaDon> phanTrangHoaDon(Integer pageNum, Integer pageNo);

    Page<HoaDon> search(String maHoaDon, Date ngayThanhToan, Double tongTienSauKhiGiam, Boolean trangThai, String tenNguoiNhan, Date ngayNhanDuKien, Date ngayShip, Integer size, Integer page);

    List<HoaDon> getExcel();
}
