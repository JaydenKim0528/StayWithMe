package kr.co.swm.board.list.controller;

import kr.co.swm.board.list.model.DTO.*;
import kr.co.swm.board.list.model.sevice.TourAccommodationService;
import kr.co.swm.board.list.util.Pagenation;
import lombok.RequiredArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;


import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class ListController {

    private static final Logger log = LoggerFactory.getLogger(ListController.class);
    private final TourAccommodationService tourAccommodationService;
    private final Pagenation pagenation;


    @GetMapping("/tour")    //  tour에 대한 Get요청을 메소드와 mapping시킴
    public List<ListDTO> list(@RequestParam(value = "currentPage", defaultValue = "1") int currentPage,
                       @ModelAttribute SearchDTO searchDTO,
                       Model model) {
        //전체 게시글 수 구하기(Pagenation 영역)
        int listCount = tourAccommodationService.getTotalCount(searchDTO);
        int pageLimit = 3; // 보여질 페이지
        int boardLimit = 5; // 페이지당 게시글

        // 페이징
        PageInfoDTO pi = pagenation.getPageInfo(listCount, currentPage, pageLimit, boardLimit);

        //listCount: 전체 항목, currentPage: 현재 페이지 place 가 아니라 업소조회던데용? 이름 더 명시적으로 변경했습니당 ! [ej]
        List<ListDTO> accommodations = tourAccommodationService.getAccommodation(pi, searchDTO);

        accommodations.forEach(accommodation -> {
            Integer basicRate = 0;
            Integer extraRate = 0;

            // 업소 기준으로 객실정보 조회
            List<RoomDTO> rooms = tourAccommodationService.getRooms(accommodation.getBoardNo());

            List<Integer> roomNos = rooms.stream()
                    .map(RoomDTO::getRoomNo)
                    .toList();

            // 일반 요금의 최저가 조회
            basicRate = tourAccommodationService.getMinBasicRate(roomNos);

            // 추가 요금의 최저가 조회
            if (StringUtils.hasText(searchDTO.getCheckInDate())) {
                extraRate = tourAccommodationService.getMinExtraRate(roomNos, searchDTO);
            }

            // 최저가
            accommodation.getMinRate(basicRate, extraRate);
            accommodation.changeBroadCount(basicRate);
        });

        // 최소 기본 가격 조회

        List<String> uniqueFacilities = tourAccommodationService.getUniqueFacilities();

        //데이터 바인딩
        // 장소
        model.addAttribute("place", accommodations);

        // 페이징
        model.addAttribute("pi", pi);
        // 최저 기본 가격
        model.addAttribute("cost", accommodations);

        // 검색
        model.addAttribute("searchDTO", new SearchDTO());
        model.addAttribute("uniqueFacilities", uniqueFacilities);

        log.info("최저가, {}", accommodations);
        return accommodations;
        // return "tour";  // tour위치 반환
    }

    @PostMapping("/get-list")
    public String getList(Model model,
                          @RequestParam(value = "currentPage", defaultValue = "1") int currentPage,
                          @ModelAttribute MainSearchDTO mainSearchDTO) {

        System.out.println("========== Controller mainSearch ==========");
        System.out.println(mainSearchDTO.getMainSearch());
        System.out.println("===========================================");

        // 전체 게시글 수 구하기(Pagenation 영역)
        int listCount = tourAccommodationService.getListCount(mainSearchDTO);
        int pageLimit = 3; // 보여질 페이지
        int boardLimit = 5; // 페이지당 게시글

        PageInfoDTO pi = pagenation.getPageInfo(listCount, currentPage, pageLimit, boardLimit);

        // 날짜 기준 필터링 된 업소 리스트 조회
        List<ListDTO> place = tourAccommodationService.getList(mainSearchDTO);

        // 최소 기본 가격
        // List<ListDTO> cost = tourAccommodationService.getCost();

        // 부가시설 조회
        List<String> uniqueFacilities = tourAccommodationService.getFacilities(mainSearchDTO);

        // 데이터 바인딩
        model.addAttribute("place", place);
        model.addAttribute("pi", pi);
        model.addAttribute("cost", place);
        model.addAttribute("uniqueFacilities", uniqueFacilities);

        // searchDTO 또는 listDto를 뷰로 전달
        model.addAttribute("searchDTO", mainSearchDTO);

        return "tour";  // 리스트 페이지로 이동
    }

}

