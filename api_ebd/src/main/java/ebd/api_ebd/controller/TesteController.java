package ebd.api_ebd.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping()
public class TesteController {
    @GetMapping("/teste")
    public String hello() {
        return "Funcionou!";
    }
}
