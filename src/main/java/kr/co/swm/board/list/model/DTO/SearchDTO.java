package kr.co.swm.board.list.model.DTO;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class SearchDTO {
     private String type = "전체";
     private int minRate = 0;
     private int maxRate = 1000000;
     private List<String> options;

     // 검색 시작 날짜
     private String checkInDate = "2024-08-29";

     // 검색 종료 날짜
     private String checkOutDate = "2024-09-08";

}
