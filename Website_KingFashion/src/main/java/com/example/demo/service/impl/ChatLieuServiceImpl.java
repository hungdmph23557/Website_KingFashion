package com.example.demo.service.impl;

import com.example.demo.entity.ChatLieu;
import com.example.demo.repository.ChatLieuRepository;
import com.example.demo.service.ChatLieuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ChatLieuServiceImpl implements ChatLieuService {

    @Autowired
    private ChatLieuRepository chatLieuRepository;

    @Override
    public List<ChatLieu> getAll() {
        return chatLieuRepository.findAll();
    }

    @Override
    public Page<ChatLieu> phanTrangChatLieu(Integer pageNum, Integer pageNo) {
        Pageable pageable = PageRequest.of(pageNum,pageNo);
        return chatLieuRepository.findAll(pageable);
    }

    @Override
    public void add(ChatLieu chatLieu) {
        chatLieuRepository.save(chatLieu);
    }

    @Override
    public ChatLieu detail(UUID id) {
        return chatLieuRepository.getChatLieuById(id);
    }

    @Override
    public void delete(UUID id) {
        chatLieuRepository.deleteById(id);
    }
}
