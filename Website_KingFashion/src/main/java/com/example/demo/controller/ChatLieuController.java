package com.example.demo.controller;

import com.example.demo.entity.ChatLieu;
import com.example.demo.service.ChatLieuService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Controller
@RequestMapping("/chat-lieu/")
public class ChatLieuController {
    @Autowired
    private ChatLieuService chatLieuService;

    @GetMapping("hien-thi")
    public String hienThi(@RequestParam(defaultValue = "0", name = "page") Integer pageNum, Model model) {
        Page<ChatLieu> page = chatLieuService.phanTrangChatLieu(pageNum, 5);
        model.addAttribute("list", page);
        model.addAttribute("cl1", new ChatLieu());
        return "chatlieu/chat-lieu";
    }

    @PostMapping("add")
    public String add(@Valid @ModelAttribute("ms1") ChatLieu chatLieu, BindingResult result, Model model) {

        if (result.hasErrors()) {
            return "chatlieu/chat-lieu";
        }
        chatLieu.setNgayTao(new Date());
        chatLieu.setNgaySua(new Date());
        model.addAttribute("cl1", chatLieu);
        chatLieuService.add(chatLieu);
        return "redirect:/chat-lieu/hien-thi";
    }

    @GetMapping("delete/{id}")
    public String delete(@PathVariable UUID id, Model model) {
        chatLieuService.delete(id);
        return "redirect:/chat-lieu/hien-thi";
    }

    @GetMapping("view-update/{id}")
    public String updateMauSac(@PathVariable UUID id, Model model) {
        ChatLieu chatLieu = chatLieuService.detail(id);
        model.addAttribute("cl1", chatLieu);
        return "chatlieu/update-chatlieu";
    }

    @PostMapping("update")
    public String updateMauSac(@Valid @ModelAttribute("ms1") ChatLieu chatLieu, BindingResult result, Model model, @RequestParam("ngayTao") String ngayTao) {
        if (result.hasErrors()) {
            return "chatlieu/update-chatlieu";
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date ngayTaoDate;
        try {
            ngayTaoDate = dateFormat.parse(ngayTao);
        } catch (ParseException e) {
            e.printStackTrace();
            return "redirect:/error";
        }

        chatLieu.setNgayTao(ngayTaoDate);
        chatLieu.setNgaySua(new Date());
        model.addAttribute("cl1", chatLieu);
        chatLieuService.add(chatLieu);
        return "redirect:/chat-lieu/hien-thi";
    }
}
