package kr.co.swm.adminPage.accommodation.controller;


import kr.co.swm.adminPage.accommodation.model.dto.AccommodationDto;
import kr.co.swm.adminPage.accommodation.model.dto.AccommodationImageDto;
import kr.co.swm.adminPage.accommodation.model.dto.RoomForm;
import kr.co.swm.adminPage.accommodation.model.service.AccommodationServiceImpl;
import kr.co.swm.adminPage.accommodation.util.UploadFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class AccommodationController {

    private final UploadFile uploadFile;
    private final AccommodationServiceImpl accommodationService;

    @Autowired
    public AccommodationController(UploadFile uploadFile, AccommodationServiceImpl accommodationService) {
        this.uploadFile = uploadFile;
        this.accommodationService = accommodationService;
    }

    @GetMapping("/")
    public String index(Model model) {
        return "index";
    }

    @GetMapping("/enroll")
    public String enroll(Model model) {
        System.out.println("in");

        model.addAttribute("location", new AccommodationDto());
        return "accommodation/enroll";
    }


    @PostMapping("/save-location")
    public ResponseEntity<?> saveLocation(@RequestParam("previewFiles") List<MultipartFile> subFile,
                                          @RequestParam("mainPhoto") List<MultipartFile> mainFile,
                                          @ModelAttribute AccommodationDto accommodationDto, // 업소
                                          @ModelAttribute RoomForm roomForm // 객실
    ) {
        AccommodationImageDto mainImage = uploadFile.uploadSingleFile(mainFile.get(1), "MAIN");
        accommodationService.saveAccommodation(accommodationDto, mainImage);

        int roomsSize = roomForm.getRooms().size() - 1;
        int startIndex = roomsSize;

        for (int i = 1; i < roomForm.getRooms().size(); i++) {
            AccommodationDto room = roomForm.getRooms().get(i);
            accommodationDto.changeRate(room.getWeekdayRate(),
                    room.getFridayRate(),
                    room.getSaturdayRate(),
                    room.getSundayRate());

            int endIndex = room.getEndIndex();
            accommodationService.enrollRooms(accommodationDto,
                    room,
                    roomsSize,
                    subFile,
                    startIndex);

            startIndex = (endIndex + roomsSize);
        }
        Map<String, String> response = new HashMap<>();
        try {
            response.put("message", "Upload successful!");
            response.put("success", "true");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();

            response.put("success", "false");
            response.put("message", "Error occurred");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
