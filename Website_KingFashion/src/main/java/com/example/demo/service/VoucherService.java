package com.example.demo.service;

import com.example.demo.entity.Voucher;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface VoucherService {

    Page<Voucher> page(Integer page, Integer size);

    Voucher detail(UUID id);

    void add(Voucher voucher);

    void delete(UUID id);

}
