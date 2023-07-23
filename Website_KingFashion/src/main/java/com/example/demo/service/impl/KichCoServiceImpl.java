package com.example.demo.service.impl;

import com.example.demo.entity.KichCo;
import com.example.demo.repository.KichCoRepository;
import com.example.demo.service.KichCoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service

public class KichCoServiceImpl implements KichCoService {

    @Autowired
    private KichCoRepository kichCoRepository;

    @Override
    public List<KichCo> getAll() {
        return kichCoRepository.findAll();
    }

    @Override
    public Page<KichCo> phanTrang(Integer pageNum, Integer pageNo) {
        Pageable pageable = PageRequest.of(pageNum,pageNo);
        return kichCoRepository.findAll(pageable);
    }

    @Override
    public void add(KichCo kichCo) {
            kichCoRepository.save(kichCo);
    }

    @Override
    public KichCo detail(UUID id) {
        return kichCoRepository.getKichCoById(id);
    }

    @Override
    public void delete(UUID id) {
        kichCoRepository.deleteById(id);
    }
}
