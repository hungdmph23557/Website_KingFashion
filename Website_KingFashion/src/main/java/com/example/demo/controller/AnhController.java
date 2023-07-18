package com.example.demo.controller;

import com.example.demo.entity.Anh;
import com.example.demo.repository.AnhRepository;
import com.example.demo.service.AnhService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
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
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Controller
@RequestMapping("/anh/")
public class AnhController {

    private static int currentNumber = 1;

    @Autowired
    private AnhService anhService;

    @Autowired
    private AnhRepository anhRepository;

    @GetMapping("hien-thi")
    public String hienThi(@RequestParam(defaultValue = "0", name = "page") Integer pageNum, Model model) {
        Page<Anh> page = anhService.phanTrang(pageNum, 5);
        model.addAttribute("list", page);
        model.addAttribute("att", new Anh());
        return "anh/anh";
    }

    @PostMapping("add")
    public String add(@Valid @ModelAttribute("att") Anh anh, BindingResult result, @RequestParam MultipartFile image, Model model) {

        if (result.hasErrors()) {
            return "coao/co-ao";
        }
        String maAnh = "Anh" + currentNumber;
        currentNumber++;
        anh.setMa(maAnh);
        anh.setTen(image.getOriginalFilename());
        anh.setNgayTao(new Date());
        anh.setTrangThai(1);
        model.addAttribute("att", anh);
        Anh uploadAnh = anhRepository.save(anh);
        if (uploadAnh != null) {
            try {
                File saveFile = new ClassPathResource("static/image").getFile();
                Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + image.getOriginalFilename());
                Files.copy(image.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return "redirect:/anh/hien-thi";
    }

    @GetMapping("delete/{id}")
    public String delete(@PathVariable UUID id, Model model) {
        anhService.delete(id);
        return "redirect:/anh/hien-thi";
    }

    @GetMapping("view-update/{id}")
    public String viewUpdate(@PathVariable UUID id, Model model) {
        Anh anh = anhService.detail(id);
        model.addAttribute("att", anh);
        return "anh/update-anh";
    }

    @PostMapping("update")
    public String update(@Valid @ModelAttribute("att") Anh anh, BindingResult result, Model model, @RequestParam("ngayTao") String ngayTao) {
        if (result.hasErrors()) {
            return "anh/update-anh";
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date ngayTaoDate;
        try {
            ngayTaoDate = dateFormat.parse(ngayTao);
        } catch (ParseException e) {
            e.printStackTrace();
            return "redirect:/error";
        }

        anh.setNgayTao(ngayTaoDate);
        anh.setNgaySua(new Date());
        model.addAttribute("att", anh);
        anhService.add(anh);
        return "redirect:/anh/hien-thi";
    }
}
