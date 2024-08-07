package org.example.salecomputercomponent.controller;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.example.salecomputercomponent.entities.Category;
import org.example.salecomputercomponent.entities.Product;
import org.example.salecomputercomponent.services.impl.CategoryProcessImpl;
import org.example.salecomputercomponent.services.impl.OrderDatailProcessimpl;
import org.example.salecomputercomponent.services.impl.ProductProcessImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin/product")
public class ProductAdminController {

    @Autowired
    private CategoryProcessImpl categoryProcess;
    @Autowired
    private ProductProcessImpl productProcess;

    @Autowired
    private OrderDatailProcessimpl orderDatailProcess;
    @Autowired
    private Cloudinary cloudinary;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        // Loại bỏ thuộc tính image khỏi binding để tránh lỗi
        binder.setDisallowedFields("image");
    }
    @GetMapping
    public String listProducts(@RequestParam(defaultValue = "0") int page,
                               @RequestParam(defaultValue = "5") int size,
                               Model model) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("productid").ascending());
        Page<Product> productPage = productProcess.getAllProduct(pageable);

        model.addAttribute("products", productPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", productPage.getTotalPages());

        return "admin/productAdmin";
    }



    @GetMapping("/add")
    public String addProductForm(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("categories", categoryProcess.getAllCategories()); // Để chọn danh mục khi thêm sản phẩm
        return "admin/product-form";
    }

    @PostMapping("/add")
    public String addProduct(@ModelAttribute Product product,
                             @RequestParam(value = "images", required = false) MultipartFile image,
                             BindingResult bindingResult,
                             Model model) {
        // Kiểm tra lỗi xác thực
        if (bindingResult.hasErrors()) {
            model.addAttribute("product", product);
            return "admin/product-form"; // Trả về trang lỗi nếu có lỗi
        }

        // Xử lý tệp tin nếu có
        if (image != null && !image.isEmpty()) {
            try {
                // Upload file to Cloudinary
                Map<String, Object> uploadResult = cloudinary.uploader().upload(image.getBytes(), ObjectUtils.emptyMap());

                // Lấy URL của ảnh đã tải lên
                String imageUrl = uploadResult.get("url").toString();

                // Lưu đường dẫn vào đối tượng sản phẩm
                product.setImage(imageUrl);
            } catch (IOException e) {
                e.printStackTrace();
                // Xử lý lỗi (thông báo cho người dùng hoặc log lỗi)
                model.addAttribute("error", "Lỗi khi tải ảnh lên");
                return "admin/product-form";
            }
        }

        // Lưu sản phẩm vào cơ sở dữ liệu
        productProcess.saveProduct(product);

        // Chuyển hướng đến danh sách sản phẩm sau khi lưu
        return "redirect:/admin/product";
    }




    @GetMapping("/edit/{id}")
    public String editProductForm(@PathVariable int id, Model model) {
        Product product = productProcess.getProductById(id);
        model.addAttribute("product", product);
        model.addAttribute("categories", categoryProcess.getAllCategories());
        return "admin/product-form";
    }


    @PostMapping("/edit/{id}")
    public String editProduct(@PathVariable("id") int id,
                              @ModelAttribute Product product,
                              @RequestParam(value = "image", required = false) MultipartFile image,
                              BindingResult bindingResult) {
        // Kiểm tra lỗi xác thực
        if (bindingResult.hasErrors()) {
            return "admin/product-form";
        }

        // Kiểm tra sản phẩm hiện tại từ cơ sở dữ liệu
        Product existingProduct = productProcess.getProductById(id);
        if (existingProduct == null) {
            // Xử lý khi không tìm thấy sản phẩm
            return "redirect:/admin/product";
        }

        if (image != null && !image.isEmpty()) {
            try {
                // Upload file to Cloudinary
                Map uploadResult = cloudinary.uploader().upload(image.getBytes(), ObjectUtils.emptyMap());

                // Get the URL of the uploaded image
                String imageUrl = uploadResult.get("url").toString();

                // Cập nhật đường dẫn vào đối tượng sản phẩm
                product.setImage(imageUrl);
            } catch (IOException e) {
                e.printStackTrace();
                // Xử lý lỗi (thông báo cho người dùng hoặc log lỗi)
                return "admin/product-form";
            }
        } else {
            // Nếu không có hình ảnh mới, giữ nguyên hình ảnh cũ
            product.setImage(existingProduct.getImage());
        }

        // Cập nhật các thuộc tính khác của sản phẩm từ đối tượng 'product' tới 'existingProduct'
        existingProduct.setName(product.getName());
        existingProduct.setDescription(product.getDescription());
        existingProduct.setPrice(product.getPrice());
        existingProduct.setQuantity(product.getQuantity());
        existingProduct.setCategory(product.getCategory());
        existingProduct.setImage(product.getImage()); // Đảm bảo cập nhật ảnh cuối cùng

        // Lưu sản phẩm đã cập nhật
        productProcess.saveProduct(existingProduct);

        return "redirect:/admin/product"; // Chuyển hướng đến danh sách sản phẩm hoặc trang chi tiết sau khi lưu
    }


    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable int id, RedirectAttributes redirectAttributes) {
//        productProcess.deleteProductById(id);
//        return "redirect:/admin/product";

        if (orderDatailProcess.existsByProductId(id)) {
            // Thêm thông báo lỗi vào RedirectAttributes
            redirectAttributes.addFlashAttribute("errorMessage", " không thể xóa vì nguowif d");
            System.out.println("Không thể xóa sản phẩm vì đã có đơn hàng liên quan");
            return "redirect:/admin/product";
        }

        // Xóa danh mục nếu không có sản phẩm liên quan
        productProcess.deleteProductById(id);
        return "redirect:/admin/product";

    }


}
