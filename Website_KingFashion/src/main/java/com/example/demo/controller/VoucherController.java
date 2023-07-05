package com.example.demo.controller;

import com.example.demo.entity.Voucher;
import com.example.demo.service.impl.VoucherServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
@RequestMapping("/voucher")
public class VoucherController {

    @Autowired
    private VoucherServiceImpl voucherService;

    @GetMapping("/hien-thi")
    public String hienThi(Model model, @RequestParam(name = "page", defaultValue = "0") Integer p) {
        Page<Voucher> page = voucherService.page(p, 5);
        model.addAttribute("list", page);
        return "voucher/home";
    }

    @GetMapping("/view-add")
    public String viewAdd(Model model) {
        model.addAttribute("voucher", new Voucher());
        return "voucher/add";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable UUID id, Model model) {
        voucherService.delete(id);
        return "redirect:/voucher/hien-thi";
    }

    @GetMapping("/view-update/{id}")
    public String viewUpdate(@PathVariable UUID id, Model model) {
        Voucher voucher = voucherService.detail(id);
        model.addAttribute("voucher", voucher);
        return "voucher/update";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("voucher") Voucher voucher) {
        voucherService.add(voucher);
        return "redirect:/voucher/hien-thi";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute("voucher") Voucher voucher) {
        voucherService.add(voucher);
        return "redirect:/voucher/hien-thi";
    }
}
