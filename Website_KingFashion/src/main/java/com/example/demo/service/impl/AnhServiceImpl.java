package com.example.demo.service.impl;

import com.example.demo.entity.Anh;
import com.example.demo.repository.AnhRepository;
import com.example.demo.service.AnhService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AnhServiceImpl implements AnhService {

    @Autowired
    private AnhRepository anhRepository;

    @Override
    public Page<Anh> phanTrang(Integer pageNum, Integer pageNo) {
        Pageable pageable = PageRequest.of(pageNum,pageNo);
        return anhRepository.findAll(pageable);
    }

    @Override
    public void add(Anh anh) {
        anhRepository.save(anh);
    }

    @Override
    public Anh detail(UUID id) {
        return anhRepository.getAnhById(id);
    }

    @Override
    public void delete(UUID id) {
        anhRepository.deleteById(id);
    }
}
