package kr.co.swm.adminPage.accommodation.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Controller
public class AccommodationController {

    @GetMapping("/enroll")

    public String enroll() {

        System.out.println("in");
        return "accommodation/enroll";
    }


}
