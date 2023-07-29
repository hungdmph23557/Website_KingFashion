package com.example.demo.controller;

import com.example.demo.entity.KichCo;
import com.example.demo.service.KichCoService;
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
import java.util.Random;
import java.util.UUID;

@Controller

@RequestMapping("/kich-co/")


public class KichCoController {
    @Autowired
    private KichCoService kichCoService;

    @GetMapping("hien-thi")
    public String hienThi(@RequestParam(defaultValue = "0", name = "page") Integer pageNum, Model model) {
        Page<KichCo> page = kichCoService.phanTrang(pageNum, 5);
        model.addAttribute("list", page);
        model.addAttribute("att", new KichCo());
        return "kichco/kich-co";
    }

    @PostMapping("add")

    public String add(@Valid @ModelAttribute("att") KichCo kichCo, BindingResult result, Model model) {

        if (result.hasErrors()) {
            return "kichco/kich-co";
        }
        String ma = "KC" + new Random().nextInt(100000);
        kichCo.setMa(ma);
        kichCo.setTrangThai(1);
        kichCo.setNgayTao(new Date());
        model.addAttribute("att", kichCo);
        kichCoService.add(kichCo);
        return "redirect:/kich-co/hien-thi";
    }

    @GetMapping("delete/{id}")
    public String delete(@PathVariable UUID id, Model model) {
        kichCoService.delete(id);
        return "redirect:/kich-co/hien-thi";
    }

    @GetMapping("view-update/{id}")
    public String viewUpdate(@PathVariable UUID id, Model model) {

        KichCo kichCo = kichCoService.detail(id);
        model.addAttribute("att", kichCo);
        return "kichco/update-kich-co";
    }



    @PostMapping("update")
    public String update(@Valid @ModelAttribute("att") KichCo kichCo, BindingResult result, Model model, @RequestParam("ngayTao") String ngayTao) {
        if (result.hasErrors()) {
            return "kichco/update-kich-co";
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date ngayTaoDate;
        try {
            ngayTaoDate = dateFormat.parse(ngayTao);
        } catch (ParseException e) {
            e.printStackTrace();
            return "redirect:/error";
        }

        kichCo.setNgayTao(ngayTaoDate);
        kichCo.setNgaySua(new Date());
        model.addAttribute("att", kichCo);
        kichCoService.add(kichCo);
        return "redirect:/kich-co/hien-thi";

    }
}
