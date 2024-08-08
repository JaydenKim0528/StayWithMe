package kr.co.swm.adminPage.accommodation.controller;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class AddressRestController {




    @PostMapping("/saveLocation")
    public String saveLocation(@RequestBody Map<String, Object> location) {

        System.out.println("진입");

        String latStr = location.get("lat").toString();
        String lonStr = location.get("lon").toString();

        double lat = Double.parseDouble(latStr);
        double lon = Double.parseDouble(lonStr);

        System.out.println("1 : " + lat);
        System.out.println("1 : " + lon);


        return "success";

    }
}
