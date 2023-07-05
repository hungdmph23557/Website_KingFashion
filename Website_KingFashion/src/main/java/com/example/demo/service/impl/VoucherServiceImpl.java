package com.example.demo.service.impl;

import com.example.demo.entity.Voucher;
import com.example.demo.repository.VoucherRepository;
import com.example.demo.service.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class VoucherServiceImpl implements VoucherService {

    @Autowired
    private VoucherRepository repository;

    @Override
    public Page<Voucher> page(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        return repository.findAll(pageable);
    }

    @Override
    public Voucher detail(UUID id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public void add(Voucher voucher) {
        repository.save(voucher);
    }

    @Override
    public void delete(UUID id) {
        repository.deleteById(id);
    }
}
