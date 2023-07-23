package com.example.demo.service;

import com.example.demo.entity.TaiKhoan;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.UUID;

public interface TaiKhoanService {

    List<TaiKhoan> getAll();

    Page<TaiKhoan> page(Integer page, Integer size);

    TaiKhoan detail(UUID id);

    void add(TaiKhoan taiKhoan);

    void delete(UUID id);

}
