package com.example.demo.service;

import com.example.demo.entity.KichCo;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.UUID;
@Service
public interface KichCoService {
    Page<KichCo> phanTrang(Integer pageNum, Integer pageNo);

    void add(KichCo kichCo);

    KichCo detail(UUID id);

    void delete(UUID id);
}
