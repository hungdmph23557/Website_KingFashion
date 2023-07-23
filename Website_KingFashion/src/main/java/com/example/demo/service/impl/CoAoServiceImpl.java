package com.example.demo.service.impl;

import com.example.demo.entity.CoAo;
import com.example.demo.repository.CoAoRepository;
import com.example.demo.service.CoAoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CoAoServiceImpl implements CoAoService {

    @Autowired
    private CoAoRepository coAoRepository;

    @Override
    public List<CoAo> getAll() {
        return coAoRepository.findAll();
    }

    @Override
    public Page<CoAo> phanTrang(Integer pageNum, Integer pageNo) {
        Pageable pageable = PageRequest.of(pageNum,pageNo);
        return coAoRepository.findAll(pageable);
    }

    @Override
    public void add(CoAo coAo) {
        coAoRepository.save(coAo);
    }

    @Override
    public CoAo detail(UUID id) {
        return coAoRepository.getCoAoById(id);
    }

    @Override
    public void delete(UUID id) {
        coAoRepository.deleteById(id);
    }
}
