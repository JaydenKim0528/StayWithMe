package kr.co.swm.board.list.model.DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class MainSearchDTO {

    private String type;
    private String boardType = "";
    private int minRate = 0;
    private int maxRate = 1000000;
    private List<String> options;

    private String mainSearch;
    private String checkInDate;
    private String checkOutDate;
}
