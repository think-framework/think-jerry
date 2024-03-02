package io.github.thinkframework.example.web.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api")
@Controller
public class HelloResourceImpl {
    @GetMapping("/hello")
    public ResponseEntity<String> upload(){
        return ResponseEntity.ok("success");
    }
}
