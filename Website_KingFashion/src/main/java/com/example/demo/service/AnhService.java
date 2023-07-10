package com.example.demo.service;

import com.example.demo.entity.Anh;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface AnhService {
    Page<Anh> phanTrang(Integer pageNum, Integer pageNo);

    void add(Anh anh);

    Anh detail(UUID id);

    void delete(UUID id);
}
