package com.yjj.campustradeplatform.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
public class UploadController {

    private static final String UPLOAD_DIR = "uploads/";
    private static final String ALLOWED_EXTENSIONS = "jpg,jpeg,png,gif";

    @PostMapping("/upload")
    public ResponseEntity<Map<String, Object>> upload(@RequestParam("file") MultipartFile file) {
        Map<String, Object> result = new HashMap<>();
        
        if (file.isEmpty()) {
            result.put("success", false);
            result.put("message", "请选择要上传的文件");
            return ResponseEntity.badRequest().body(result);
        }

        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null) {
            result.put("success", false);
            result.put("message", "文件名无效");
            return ResponseEntity.badRequest().body(result);
        }

        String extension = originalFilename.substring(originalFilename.lastIndexOf(".") + 1).toLowerCase();
        if (!ALLOWED_EXTENSIONS.contains(extension)) {
            result.put("success", false);
            result.put("message", "不支持的文件格式，仅支持 JPG、PNG、GIF");
            return ResponseEntity.badRequest().body(result);
        }

        try {
            File uploadDir = new File(UPLOAD_DIR);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }

            String newFilename = UUID.randomUUID().toString() + "." + extension;
            File dest = new File(UPLOAD_DIR + newFilename);
            file.transferTo(dest);

            String url = "/uploads/" + newFilename;
            result.put("success", true);
            result.put("url", url);
            result.put("message", "上传成功");
            return ResponseEntity.ok(result);

        } catch (IOException e) {
            result.put("success", false);
            result.put("message", "文件上传失败：" + e.getMessage());
            return ResponseEntity.internalServerError().body(result);
        }
    }
}
