package kr.co.swm;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TestController {

    @RequestMapping("/")
    public String index() {
        System.out.println("dd");
        return "index";
    }

    @RequestMapping("/contact.do")
    public String contact() {
        System.out.println("aaaa");
        return "contact";
    }

    @GetMapping("/cancel")
    public String cancel() {
        return "cancel-booking";
    }

    @GetMapping("replace")
    public String rr() {
        return "insertTest";
    }

}
