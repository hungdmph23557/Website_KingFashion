package com.example.demo.service;

import com.example.demo.entity.ChatLieu;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.UUID;

public interface ChatLieuService {

    List<ChatLieu> getAll();

    Page<ChatLieu> phanTrangChatLieu(Integer pageNum, Integer pageNo);

    void add(ChatLieu chatLieu);

    ChatLieu detail(UUID id);

    void delete(UUID id);
}
