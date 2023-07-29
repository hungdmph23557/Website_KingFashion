package com.example.demo.controller;

import com.example.demo.entity.Anh;
import com.example.demo.repository.AnhRepository;
import com.example.demo.service.AnhService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.sql.SQLException;
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
    @GetMapping("display")
    public ResponseEntity<byte[]> displayImage(@RequestParam("id") UUID id) throws IOException, SQLException {
        Anh image = anhService.viewById(id);
        byte[] imageBytes = null;
        imageBytes = image.getTen().getBytes(1, (int) image.getTen().length());
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(imageBytes);
    }

    @GetMapping("add-anh")
    public ModelAndView addImage() {
        return new ModelAndView("addimage");
    }
//
//    @PostMapping("add-anh")
//    public String addImagePost(@RequestParam("image") MultipartFile[] files) throws IOException, SQLException {
//        anhService.add(a,files);
//        return "redirect:/anh/hien-thi";
//    }

//    @GetMapping("delete/{id}")
//    public String delete(@PathVariable UUID id, Model model) {
//        anhService.delete(id);
//        return "redirect:/anh/hien-thi";
//    }
//
//    @GetMapping("view-update/{id}")
//    public String viewUpdate(@PathVariable UUID id, Model model) {
//        Anh anh = anhService.detail(id);
//        model.addAttribute("att", anh);
//        return "anh/update-anh";
//    }

//    @PostMapping("update")
//    public String update(@Valid @ModelAttribute("att") Anh anh, BindingResult result, Model model, @RequestParam("ngayTao") String ngayTao) {
//        if (result.hasErrors()) {
//            return "anh/update-anh";
//        }
//
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//        Date ngayTaoDate;
//        try {
//            ngayTaoDate = dateFormat.parse(ngayTao);
//        } catch (ParseException e) {
//            e.printStackTrace();
//            return "redirect:/error";
//        }
//
//        anh.setNgayTao(ngayTaoDate);
//        anh.setNgaySua(new Date());
//        model.addAttribute("att", anh);
//        anhService.add(anh);
//        return "redirect:/anh/hien-thi";
//    }
}
