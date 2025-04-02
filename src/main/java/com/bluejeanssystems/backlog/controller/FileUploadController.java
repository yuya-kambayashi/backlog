package com.bluejeanssystems.backlog.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RequiredArgsConstructor
@Controller
public class FileUploadController {

    private static final String UPLOAD_DIR = "C:\\Users\\kamba\\Documents\\02_Java\\02_Backlog\\uploads\\";

    @PostMapping("/upload")
    public ResponseEntity<Map<String, String>> uploadFile(@RequestParam("file") MultipartFile file) {
        Map<String, String> response = new HashMap<>();

        try {
            // アップロードディレクトリを作成（存在しない場合）
            File uploadDir = new File(UPLOAD_DIR);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }

            // ユニークなファイル名を作成（ファイル名の競合を防ぐ）
            String uniqueFileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
            Path filePath = Paths.get(UPLOAD_DIR, uniqueFileName);

            // ファイルを保存
            Files.write(filePath, file.getBytes());

            // レスポンスにファイル名とパスを追加
            response.put("message", "ファイルがアップロードされました");
            response.put("fileName", uniqueFileName);

            response.put("filePath", filePath.toString());
            response.put("pathWithTag", "<img src=\"" + filePath.toString() + "\">");

            /*
            ファイルを保存
            ファイルをS3にアップロード
                フォルダ作成
            cloudfrontのUrlを取得
            imgタグの埋め込み
            */

            return ResponseEntity.ok(response);
        } catch (IOException e) {
            response.put("error", "エラー: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}