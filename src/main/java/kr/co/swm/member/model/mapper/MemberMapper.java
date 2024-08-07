package kr.co.swm.member.model.mapper;

import kr.co.swm.member.model.dto.MemberDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface MemberMapper {

    public int setSignUp(@Param("memberDTO") MemberDTO memberDTO);

    public int idCheck(@Param("userId") String userId);
}
