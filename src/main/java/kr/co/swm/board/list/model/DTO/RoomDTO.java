package kr.co.swm.board.list.model.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoomDTO {
    private int roomNo;
    private int accommodationNo;
    private int roomTypeNo;
    private String roomName;
    private String roomCheckInTime;
    private String roomCheckOutTime;
}
