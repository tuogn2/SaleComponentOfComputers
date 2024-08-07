package org.example.salecomputercomponent.services.impl;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class ImageService {

    private final String UPLOAD_DIR = "src/main/resources/static/IMG/";

    public String saveImage(MultipartFile file) throws IOException {
        // Tạo một tên tệp duy nhất
        String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();

        // Đảm bảo thư mục lưu trữ tồn tại
        Path uploadPath = Paths.get(UPLOAD_DIR);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        // Lưu tệp
        Path filePath = uploadPath.resolve(fileName);
        file.transferTo(filePath.toFile());

        // Trả về đường dẫn tệp để lưu vào cơ sở dữ liệu
        return "/IMG/" + fileName;
    }
}
