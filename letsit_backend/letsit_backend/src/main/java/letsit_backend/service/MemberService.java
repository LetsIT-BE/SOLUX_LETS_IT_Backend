package letsit_backend.service;

import letsit_backend.dto.MemberResponseDto;
import letsit_backend.dto.MemberSignupRequestDto;
import letsit_backend.repository.MemberRepository;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository MemberRepository;

    public MemberResponseDto signUp(MemberSignupRequestDto requestDto) {
        return MemberResponseDto.fromEntity(MemberRepository.save(requestDto.toEntity()));
    }
}
