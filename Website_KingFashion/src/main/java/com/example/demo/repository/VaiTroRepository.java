package com.example.demo.repository;

import com.example.demo.entity.VaiTro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface VaiTroRepository extends JpaRepository<VaiTro, UUID> {
}
