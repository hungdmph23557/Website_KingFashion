package com.example.demo.repository;

import com.example.demo.entity.KichCo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface KichCoRepository extends JpaRepository<KichCo, UUID> {

        KichCo getKichCoById(UUID id);

        @Query("SELECT c FROM KichCo c ORDER BY c.ten ASC")
        List<KichCo> getAll();
}
