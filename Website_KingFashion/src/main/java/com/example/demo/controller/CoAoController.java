package com.example.demo.controller;

import com.example.demo.entity.CoAo;
import com.example.demo.service.CoAoService;
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
@RequestMapping("/co-ao/")
public class CoAoController {
    @Autowired
    private CoAoService coAoService;

    @GetMapping("hien-thi")
    public String hienThi(@RequestParam(defaultValue = "0", name = "page") Integer pageNum, Model model) {
        Page<CoAo> page = coAoService.phanTrang(pageNum, 5);
        model.addAttribute("list", page);
        model.addAttribute("att", new CoAo());
        return "coao/co-ao";
    }

    @PostMapping("add")
    public String add(@Valid @ModelAttribute("att") CoAo coAo, BindingResult result, Model model) {

        if (result.hasErrors()) {
            return "coao/co-ao";
        }
        String ma = "CAO" + new Random().nextInt(100000);
        coAo.setMa(ma);
        coAo.setTrangThai(1);
        coAo.setNgayTao(new Date());
        model.addAttribute("att", coAo);
        coAoService.add(coAo);
        return "redirect:/co-ao/hien-thi";
    }

    @GetMapping("delete/{id}")
    public String delete(@PathVariable UUID id, Model model) {
        coAoService.delete(id);
        return "redirect:/co-ao/hien-thi";
    }

    @GetMapping("view-update/{id}")
    public String viewUpdate(@PathVariable UUID id, Model model) {
        CoAo coAo = coAoService.detail(id);
        model.addAttribute("att", coAo);
        return "coao/update-co-ao";
    }

    @PostMapping("update")
    public String update(@Valid @ModelAttribute("att") CoAo coAo, BindingResult result, Model model, @RequestParam("ngayTao") String ngayTao) {
        if (result.hasErrors()) {
            return "coao/update-co-ao";
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date ngayTaoDate;
        try {
            ngayTaoDate = dateFormat.parse(ngayTao);
        } catch (ParseException e) {
            e.printStackTrace();
            return "redirect:/error";
        }

        coAo.setNgayTao(ngayTaoDate);
        coAo.setNgaySua(new Date());
        model.addAttribute("att", coAo);
        coAoService.add(coAo);
        return "redirect:/co-ao/hien-thi";
    }
}
