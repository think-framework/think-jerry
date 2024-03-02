package io.github.thinkframework.example.web.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@RequestMapping("/api")
@Controller
public class HelloResourceImpl {
    @GetMapping("/hello")
    public ResponseEntity<String> upload(){
        return ResponseEntity.ok("success");
    }
}
