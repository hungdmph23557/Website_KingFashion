package com.example.demo.service;

import com.example.demo.entity.NhaSanXuat;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.UUID;

public interface NhaSanXuatService {

    List<NhaSanXuat> getAll();

    void add(NhaSanXuat nhaSanXuat);

    NhaSanXuat detail(UUID id);

    void delete(UUID id);

    Page<NhaSanXuat> phanTrangNhaSanXuat (Integer pageNum, Integer pageNo);
}
