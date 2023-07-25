package com.example.demo.service;

import com.example.demo.entity.TaiKhoan;
import org.springframework.data.domain.Page;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public interface TaiKhoanService {

    List<TaiKhoan> getAll();

    Page<TaiKhoan> page(Integer page, Integer size);

    Page<TaiKhoan> search(String ma, String ten, String sdt, String email, String diaChi, Date ngaySinh, Integer size, Integer page);

    TaiKhoan detail(UUID id);

    void add(TaiKhoan taiKhoan);

    void delete(UUID id);

}
