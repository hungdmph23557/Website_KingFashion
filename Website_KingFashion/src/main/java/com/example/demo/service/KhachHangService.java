package com.example.demo.service;

import com.example.demo.entity.KhachHang;
import com.example.demo.entity.Voucher;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface KhachHangService {
    Page<KhachHang> page(Integer page, Integer size);

    KhachHang detail(UUID id);

    void add(KhachHang khachHang);

    void delete(UUID id);

}
