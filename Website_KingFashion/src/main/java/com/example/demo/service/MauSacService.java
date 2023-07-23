package com.example.demo.service;

import com.example.demo.entity.MauSac;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.UUID;

public interface MauSacService {

    List<MauSac> getAll();

    void add(MauSac mauSac);

    MauSac detail(UUID id);

    void delete(UUID id);

    Page<MauSac> phanTrangMauSac (Integer pageNum, Integer pageNo);

}
