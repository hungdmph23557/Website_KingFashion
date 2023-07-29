package com.example.demo.controller;

import com.example.demo.entity.Anh;
import com.example.demo.entity.ChatLieu;
import com.example.demo.entity.ChiTietSanPham;
import com.example.demo.entity.CoAo;
import com.example.demo.entity.KichCo;
import com.example.demo.entity.LoaiSanPham;
import com.example.demo.entity.MauSac;
import com.example.demo.entity.NhaSanXuat;
import com.example.demo.entity.SanPham;
import com.example.demo.entity.Voucher;
import com.example.demo.repository.AnhRepository;
import com.example.demo.service.AnhService;
import com.example.demo.service.ChatLieuService;
import com.example.demo.service.ChiTietSanPhamService;
import com.example.demo.service.CoAoService;
import com.example.demo.service.KichCoService;
import com.example.demo.service.LoaiSanPhamService;
import com.example.demo.service.MauSacService;
import com.example.demo.service.NhaSanXuatService;
import com.example.demo.service.SanPhamService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/chi-tiet-san-pham/")
public class ChiTietSanPhamController {

    private static int currentNumber = 1;

    @Autowired
    private ChiTietSanPhamService chiTietSanPhamService;

    @Autowired
    private AnhService anhService;

    @Autowired
    private AnhRepository anhRepository;

    @Autowired
    private SanPhamService sanPhamService;

    @Autowired
    private KichCoService kichCoService;

    @Autowired
    private CoAoService coAoService;

    @Autowired
    private LoaiSanPhamService loaiSanPhamService;

    @Autowired
    private MauSacService mauSacService;

    @Autowired
    private NhaSanXuatService nhaSanXuatService;

    @Autowired
    private ChatLieuService chatLieuService;

    private List<KichCo> listKichCo = new ArrayList<>();

    @GetMapping("hien-thi")
    public String hienThi(@RequestParam(defaultValue = "0", name = "page") Integer pageNum, Model model, HttpSession session) {
        if (session.getAttribute("successMessage") != null) {
            String successMessage = (String) session.getAttribute("successMessage");
            model.addAttribute("successMessage", successMessage);
            session.removeAttribute("successMessage");
        }
        List<SanPham> listSanPham = sanPhamService.getAll();
        Page<ChiTietSanPham> page = chiTietSanPhamService.PhanTrang(pageNum, 5);
        model.addAttribute("listSanPham", listSanPham);
        model.addAttribute("list", page);
        model.addAttribute("listCoAo", coAoService.getAll());
        model.addAttribute("listLoaiSanPham", loaiSanPhamService.getAll());
        model.addAttribute("listMauSac", mauSacService.getAll());
        model.addAttribute("listHang", mauSacService.getAll());
        model.addAttribute("listNhaSanXuat", nhaSanXuatService.getAll());
        model.addAttribute("listChatLieu", chatLieuService.getAll());
        model.addAttribute("att", new ChiTietSanPham());
        return "chitietsanpham/chi-tiet-san-pham";
    }



    @GetMapping("search")
    public String search(@RequestParam(value = "ten", required = false) String ten,
                         @RequestParam(value = "minTien", required = false) Integer minTien,
                         @RequestParam(value = "maxTien", required = false) Integer maxTien,
                         @RequestParam(defaultValue = "0", name = "page") Integer pageNum,
                         Model model, HttpSession session) {

        if (session.getAttribute("successMessage") != null) {
            String successMessage = (String) session.getAttribute("successMessage");
            model.addAttribute("successMessage", successMessage);
            session.removeAttribute("successMessage");
        }

        Page<ChiTietSanPham> ketQuaTimKiem = chiTietSanPhamService.search(ten, minTien, maxTien, pageNum, 5);

        model.addAttribute("list", ketQuaTimKiem);
        model.addAttribute("att", new ChiTietSanPham()); // Add this line to set the "att" attribute in the model

        // Add other model attributes if required

        return "chitietsanpham/chi-tiet-san-pham";
    }

    @GetMapping("view-add")
    public String viewAdd(Model model) {
        model.addAttribute("listCoAo", coAoService.getAll());
        model.addAttribute("listLoaiSanPham", loaiSanPhamService.getAll());
        model.addAttribute("listMauSac", mauSacService.getAll());
        model.addAttribute("listNhaSanXuat", nhaSanXuatService.getAll());
        model.addAttribute("listChatLieu", chatLieuService.getAll());
        model.addAttribute("att", new ChiTietSanPham());
        model.addAttribute("lsp1", new LoaiSanPham());
        model.addAttribute("ms1", new MauSac());
        model.addAttribute("nsx1", new NhaSanXuat());
        model.addAttribute("ca1", new CoAo());
        model.addAttribute("cl1", new ChatLieu());
        return "chitietsanpham/add-chi-tiet-san-pham";
    }

    @PostMapping("add")
    public String add(@Valid @ModelAttribute("att") ChiTietSanPham chiTietSanPham,
                      BindingResult result,
                      RedirectAttributes redirectAttributes,
                      @RequestParam(defaultValue = "0", name = "page") Integer pageNum,
                      Model model, HttpSession session) {

        if (result.hasErrors()) {
            Page<ChiTietSanPham> page = chiTietSanPhamService.PhanTrang(pageNum, 5);
            model.addAttribute("list", page);
            model.addAttribute("listCoAo", coAoService.getAll());
            model.addAttribute("listLoaiSanPham", loaiSanPhamService.getAll());
            model.addAttribute("listMauSac", mauSacService.getAll());
            model.addAttribute("listNhaSanXuat", nhaSanXuatService.getAll());
            model.addAttribute("listChatLieu", chatLieuService.getAll());
            return "chitietsanpham/chi-tiet-san-pham";
        }
        String tenSanPham = chiTietSanPham.getSanPham().getTen();
        String moTaSanPham = chiTietSanPham.getSanPham().getMoTa();

        String maSanPham = "SP" + currentNumber;
        String maChiTietSanPham = "CTSP" + currentNumber;

        currentNumber++;

        SanPham sanPham = new SanPham();
        sanPham.setMa(maSanPham);
        sanPham.setMoTa(moTaSanPham);
        sanPham.setTen(tenSanPham);
        sanPham.setNgayTao(new Date());
        sanPham.setTrangThai(1);
        sanPhamService.add(sanPham);


        chiTietSanPham.setMa(maChiTietSanPham);
        chiTietSanPham.setNgayTao(new Date());
        chiTietSanPham.setTrangThai(1);

        chiTietSanPham.setSanPham(sanPham);

        model.addAttribute("att", sanPham);

        model.addAttribute("att", chiTietSanPham);
        session.setAttribute("successMessage", "Thêm thành công!");
        chiTietSanPhamService.add(chiTietSanPham);
        redirectAttributes.addAttribute("id", chiTietSanPham.getId());
        return "redirect:/chi-tiet-san-pham/view-update/{id}";
    }

    @GetMapping("delete/{id}")
    public String delete(@PathVariable UUID id, Model model, HttpSession session) {
        session.setAttribute("successMessage", "Xóa thành công!");
        chiTietSanPhamService.delete(id);
        return "redirect:/chi-tiet-san-pham/hien-thi";
    }

    @GetMapping("view-update/{id}")
    public String updateCTSP(@PathVariable UUID id, Model model) {
        ChiTietSanPham chiTietSanPham = chiTietSanPhamService.detail(id);

        listKichCo = kichCoService.getKichCoByChiTietSanPhamId(id);

        model.addAttribute("att", chiTietSanPham);
        model.addAttribute("listKc", listKichCo);
        model.addAttribute("listCoAo", coAoService.getAll());
        model.addAttribute("listLoaiSanPham", loaiSanPhamService.getAll());
        model.addAttribute("listMauSac", mauSacService.getAll());
        model.addAttribute("listNhaSanXuat", nhaSanXuatService.getAll());
        model.addAttribute("listChatLieu", chatLieuService.getAll());
        model.addAttribute("kc1", new KichCo());
        model.addAttribute("a1", new Anh());
        model.addAttribute("lsp1", new LoaiSanPham());
        model.addAttribute("ms1", new MauSac());
        model.addAttribute("nsx1", new NhaSanXuat());
        model.addAttribute("ca1", new CoAo());
        model.addAttribute("cl1", new ChatLieu());

        List<Anh> listAnh = anhService.getAllByChiTietSanPhamId(id);
        List<Anh> filteredAnhList = new ArrayList<>();
        // Thay ... bằng giá trị UUID thực tế từ URL

        for (Anh anh : listAnh) {
            if (anh.getChiTietSanPham().getId().equals(id)) {
                filteredAnhList.add(anh);
            }
        }
        model.addAttribute("listAnh", filteredAnhList);

        return "chitietsanpham/add-kich-co-anh-chi-tiet-san-pham";
    }


    @PostMapping("update")
    public String update(@Valid @ModelAttribute("att") ChiTietSanPham chiTietSanPham, BindingResult result,
                         RedirectAttributes redirectAttributes,
                         Model model) {
        if (result.hasErrors()) {
            return "chitietsanpham/update-chi-tiet-san-pham";
        }
        chiTietSanPham.setNgaySua(new Date());
        model.addAttribute("att", chiTietSanPham);
        List<KichCo> kichCoList = kichCoService.getKichCoByChiTietSanPhamId(chiTietSanPham.getId());

        // Tính tổng số lượng kích cỡ
        int totalKichCoQuantity = kichCoList.stream().mapToInt(KichCo::getSoLuong).sum();

        // Set tổng số lượng kích cỡ cho sản phẩm chi tiết
        chiTietSanPham.setSoLuong(totalKichCoQuantity);
        chiTietSanPhamService.add(chiTietSanPham);
        redirectAttributes.addAttribute("id", chiTietSanPham.getId());
        return "redirect:/chi-tiet-san-pham/view-update/{id}";
    }

    @PostMapping("addLoaiSanPham")
    public String addLoaiSanPham(@Valid @ModelAttribute("lsp1") LoaiSanPham loaiSanPham, BindingResult result, Model model) {

        if (result.hasErrors()) {
            return "chitietsanpham/add-chi-tiet-san-pham";
        }
        loaiSanPham.setNgayTao(new Date());
        loaiSanPham.setMa("LSP01");
        loaiSanPham.setTrangThai(1);
        model.addAttribute("lsp1", loaiSanPham);
        loaiSanPhamService.add(loaiSanPham);
        return viewAdd(model);
    }

    @PostMapping("addMauSac")
    public String addMauSac(@Valid @ModelAttribute("ms1") MauSac mauSac, BindingResult result, Model model) {

        if (result.hasErrors()) {
            return "chitietsanpham/add-chi-tiet-san-pham";
        }
        mauSac.setNgayTao(new Date());
        mauSac.setTenMauSac("LSP01");
        mauSac.setTrangThai(1);
        model.addAttribute("ms1", mauSac);
        mauSacService.add(mauSac);
        return viewAdd(model);
    }

    @PostMapping("addNhaSanXuat")
    public String addMauSac(@Valid @ModelAttribute("nsx1") NhaSanXuat nhaSanXuat, BindingResult result, Model model) {

        if (result.hasErrors()) {
            return "chitietsanpham/add-chi-tiet-san-pham";
        }
        nhaSanXuat.setNgayTao(new Date());
        nhaSanXuat.setMaNhaSanXuat("LSP01");
        nhaSanXuat.setTrangThai(1);
        model.addAttribute("ms1", nhaSanXuat);
        nhaSanXuatService.add(nhaSanXuat);
        return viewAdd(model);
    }

    @PostMapping("addCoAo")
    public String addMauSac(@Valid @ModelAttribute("ca1") CoAo coAo, BindingResult result, Model model) {

        if (result.hasErrors()) {
            return "chitietsanpham/add-chi-tiet-san-pham";
        }
        coAo.setNgayTao(new Date());
        coAo.setMa("LSP01");
        coAo.setTrangThai(1);
        model.addAttribute("ms1", coAo);
        coAoService.add(coAo);
        return viewAdd(model);
    }

    @PostMapping("addChatLieu")
    public String addMauSac(@Valid @ModelAttribute("cl1") ChatLieu chatLieu, BindingResult result, Model model) {

        if (result.hasErrors()) {
            return "chitietsanpham/add-chi-tiet-san-pham";
        }
        chatLieu.setNgayTao(new Date());
        chatLieu.setMa("LSP01");
        chatLieu.setTrangThai(1);
        model.addAttribute("ms1", chatLieu);
        chatLieuService.add(chatLieu);
        return viewAdd(model);
    }

    @PostMapping("add-kich-co")
    public String addkc(@Valid @ModelAttribute("kc1") KichCo kichCo, BindingResult result,
                        @RequestParam("id") UUID id,
                        RedirectAttributes redirectAttributes,
                        Model model) {

        ChiTietSanPham chiTietSanPham = chiTietSanPhamService.detail(id);

        // Lấy danh sách kích cỡ của sản phẩm chi tiết
        List<KichCo> kichCoList = kichCoService.getKichCoByChiTietSanPhamId(chiTietSanPham.getId());

        // Kiểm tra xem kích cỡ đã tồn tại trong danh sách hay chưa
        boolean kichCoExists = false;
        for (KichCo existingKichCo : kichCoList) {
            if (existingKichCo.getTen().equalsIgnoreCase(kichCo.getTen())) {
                // Kích cỡ đã tồn tại, cập nhật số lượng bằng cách cộng dồn số lượng mới
                existingKichCo.setSoLuong(existingKichCo.getSoLuong() + kichCo.getSoLuong());
                kichCoService.add(existingKichCo);
                kichCoExists = true;
                break;
            }
        }

        if (!kichCoExists) {
            // Kích cỡ chưa tồn tại trong danh sách, thêm mới kích cỡ
            kichCo.setChiTietSanPham(chiTietSanPham);
            kichCo.setNgayTao(new Date());
            kichCo.setMa("KC01");
            kichCo.setTrangThai(1);
            kichCoService.add(kichCo);
            kichCoList.add(kichCo); // Thêm kích cỡ mới vào danh sách kích cỡ
        }

        // Tính tổng số lượng kích cỡ
        int totalKichCoQuantity = kichCoList.stream().mapToInt(KichCo::getSoLuong).sum();

        // Set tổng số lượng kích cỡ cho sản phẩm chi tiết
        chiTietSanPham.setSoLuong(totalKichCoQuantity);
        chiTietSanPhamService.add(chiTietSanPham);

        redirectAttributes.addAttribute("id", chiTietSanPham.getId());
        return "redirect:/chi-tiet-san-pham/view-update/{id}";
    }

    @PostMapping("updateKc")
    public String updatekc(@Valid @ModelAttribute("kc1") KichCo kichCo, BindingResult result,
                           @RequestParam("id") UUID id,
                           RedirectAttributes redirectAttributes,
                           Model model) {
        // Check for any validation errors
        if (result.hasErrors()) {
            // Handle validation errors if needed
            return "chitietsanpham/update-chi-tiet-san-pham";
        }

        // Get the ChiTietSanPham object
        ChiTietSanPham chiTietSanPham = chiTietSanPhamService.detail(id);

        // Get the list of KichCo objects related to the ChiTietSanPham
        List<KichCo> kichCoList = kichCoService.getKichCoByChiTietSanPhamId(chiTietSanPham.getId());

        // Find the KichCo object that needs to be updated
        KichCo updatedKichCo = null;
        for (KichCo existingKichCo : kichCoList) {
            if (existingKichCo.getId().equals(kichCo.getId())) {
                updatedKichCo = existingKichCo;
                break;
            }
        }

        if (updatedKichCo == null) {
            // If the KichCo object is not found, add an error message and redirect back
            redirectAttributes.addFlashAttribute("error", "Không tìm thấy KichCo hoặc không thể cập nhật.");
            return "redirect:/chi-tiet-san-pham/view-update/{id}";
        }

        // Update the soLuong value of the existing KichCo object
        updatedKichCo.setSoLuong(kichCo.getSoLuong());

        // Save the updated KichCo object (assuming you have a 'kichCoService.update' method)
        kichCoService.add(updatedKichCo);

        // Recalculate the totalKichCoQuantity after updating
        int totalKichCoQuantity = kichCoList.stream().mapToInt(KichCo::getSoLuong).sum();

        // Set the new totalKichCoQuantity for the ChiTietSanPham
        chiTietSanPham.setSoLuong(totalKichCoQuantity);

        // Save the updated ChiTietSanPham object
        chiTietSanPhamService.add(chiTietSanPham);

        // Redirect back to the view-update page with the updated ChiTietSanPham's id
        redirectAttributes.addAttribute("id", chiTietSanPham.getId());
        return "redirect:/chi-tiet-san-pham/view-update/{id}";
    }


    @GetMapping("delete1/{id}")
    public String deleteKc(@ModelAttribute("att") ChiTietSanPham chiTietSanPham, @PathVariable UUID id, Model model,
                           RedirectAttributes redirectAttributes,
                           HttpSession session) {
        KichCo kichCo = kichCoService.getKichCoById(id);
        session.setAttribute("successMessage", "Xóa thành công!");
        kichCoService.delete(id);
        UUID chiTietSanPhamId = kichCo.getChiTietSanPham().getId();
        return "redirect:/chi-tiet-san-pham/view-update/" + chiTietSanPhamId;
    }

    @GetMapping("delete2/{id}")
    public String deleteAnh(@PathVariable UUID id, RedirectAttributes redirectAttributes, HttpSession session) {
        // Get the image by id
        Anh anh = anhService.getAnhById(id);

        // Delete the image
        anhService.delete(id);

        // Get the id of the related ChiTietSanPham
        UUID chiTietSanPhamId = anh.getChiTietSanPham().getId();

        // Add a success message to the session
        session.setAttribute("successMessage", "Xóa thành công!");

        // Redirect back to the "view-update" page
        return "redirect:/chi-tiet-san-pham/view-update/" + chiTietSanPhamId;
    }


    @GetMapping("anh-anh")
    public ModelAndView addImage() {
        return new ModelAndView("addimage");
    }

    @PostMapping("add-anh")
    public String addImagePost(@ModelAttribute("a1") Anh anh,
                               @RequestParam("image") MultipartFile[] files,
                               @RequestParam("id") UUID id,
                               RedirectAttributes redirectAttributes,
                               Model model
    ) throws IOException, SQLException {
        ChiTietSanPham chiTietSanPham = chiTietSanPhamService.detail(id);
        anh.setChiTietSanPham(chiTietSanPham);
        model.addAttribute("a1", anh);
        model.addAttribute("id", id);
        redirectAttributes.addAttribute("id", anh.getId());
        anhService.add(anh, files);
        return "redirect:/chi-tiet-san-pham/view-update/{id}";
    }

    @GetMapping("display")
    public ResponseEntity<byte[]> displayImage(@RequestParam("id") UUID id) throws IOException, SQLException {
        Anh image = anhService.viewById(id);
        byte[] imageBytes = image.getTen().getBytes(1, (int) image.getTen().length());
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(imageBytes);
    }

    @GetMapping("display1")
    public ResponseEntity<byte[]> displayImage1(@RequestParam("id") UUID id) throws IOException, SQLException {
        Anh anh = anhService.getAnhById(id);

        // Kiểm tra nếu trường image của đối tượng Anh là null thì xử lý hoặc thông báo lỗi tùy ý
        if (anh.getTen() != null) {
            // Convert Blob to byte[]
            byte[] imageData = convertBlobToBytes(anh.getTen());

            // Thiết lập các header cho response
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG); // Hoặc MediaType.IMAGE_PNG tùy loại ảnh

            // Trả về response chứa dữ liệu của ảnh
            return ResponseEntity.ok().headers(headers).body(imageData);
        } else {
            // Xử lý trường hợp image là null, ví dụ:
            String errorResponse = "Ảnh không khả dụng"; // Hoặc bạn có thể đưa ra thông báo lỗi khác tùy ý

            // Trả về response chứa thông báo lỗi
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.TEXT_PLAIN);
            return ResponseEntity.badRequest().headers(headers).body(errorResponse.getBytes());
        }
    }

    private byte[] convertBlobToBytes(Blob blob) throws IOException, SQLException {
        try (InputStream inputStream = blob.getBinaryStream()) {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            return outputStream.toByteArray();
        }
    }

}
