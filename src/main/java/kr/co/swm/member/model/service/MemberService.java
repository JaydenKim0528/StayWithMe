package kr.co.swm.member.model.service;

import kr.co.swm.member.model.dto.MemberDTO;
import kr.co.swm.member.model.mapper.MemberMapper;
import kr.co.swm.member.util.SmsCertificationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@RequiredArgsConstructor
@Service
public class MemberService {

    private final PasswordEncoder passwordEncoder;
    private final MemberMapper memberMapper;
    private final SmsCertificationUtil smsCertificationUtill;

    // 회원가입
    public int setSignup(MemberDTO memberDTO) {
        String userPwd = memberDTO.getUserPwd();
        String userConfirmPwd = memberDTO.getConfirmPassword();

        if(userPwd.equals(userConfirmPwd)) {
            // 비밀번호와 비밀번호 확인이 일치하는지 체크
            String encodedPassword = passwordEncoder.encode(userPwd);
            // 암호화 비밀번호 DTO에 저장
            memberDTO.setUserPwd(encodedPassword);
            // 현재 날짜와 시간 설정
            memberDTO.setCreatedDate(LocalDateTime.now());

            return memberMapper.setSignUp(memberDTO);
        }
        else {
            return 0;
        }
    }

    // id 중복검사
    public int idCheck(String userId) {
        return memberMapper.idCheck(userId);
    }


    // 인증번호 생성 메서드
    public String generateCertificationCode() {
        Random random = new Random();
        int code = 100000 + random.nextInt(900000);
        return String.valueOf(code);
    }

    // Cool SMS API 호출 메서드
    public void sendSMSViaCoolSMS(String phoneNumber, String certificationCode) {
        smsCertificationUtill.sendSMS(phoneNumber, certificationCode); // SmsCertificationUtil을 사용하여 SMS 발송
    }




}
