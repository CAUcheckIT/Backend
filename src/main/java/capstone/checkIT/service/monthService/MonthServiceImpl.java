package capstone.checkIT.service.monthService;

import capstone.checkIT.DTO.MonthDTO.MonthRequestDTO;
import capstone.checkIT.DTO.MonthDTO.MonthResponseDTO;
import capstone.checkIT.apipayLoad.code.status.ErrorStatus;
import capstone.checkIT.config.JwtManager;
import capstone.checkIT.entity.Member;
import capstone.checkIT.entity.Month;
import capstone.checkIT.exception.GeneralException;
import capstone.checkIT.repository.MemberRepository;
import capstone.checkIT.repository.MonthRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MonthServiceImpl implements MonthService {
    private final MonthRepository monthRepository;
    private final JwtManager jwtManager;
    private final MemberRepository memberRepository;
    @Override
    @Transactional
    public MonthResponseDTO.CreateMonthDto createMonth (String accessToken, MonthRequestDTO.CreateMonthDto request){
        Long memberId = jwtManager.validateJwt(accessToken);
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.LOGIN_ERROR_EMAIL));
        Month month =Month.builder()
                .productName(request.getProductName())
                .frequency(request.getFrequency())
                .member(member)
                .build();
        monthRepository.save(month);

        return MonthResponseDTO.CreateMonthDto.builder()
                .id(month.getId())
                .memberId(month.getMember().getId())
                .productName(month.getProductName())
                .frequency(month.getFrequency())
                .build();
    }
    @Override
    @Transactional
    public MonthResponseDTO.CreateMonthDto updateMonth (String accessToken, Long MonthId, MonthRequestDTO.CreateMonthDto request){
        Long memberId = jwtManager.validateJwt(accessToken);
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.LOGIN_ERROR_EMAIL));
        Month month = monthRepository.findById(MonthId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.MONTH_NOT_EXIST));

        month.setProductName(request.getProductName());
        month.setFrequency(request.getFrequency());

        monthRepository.save(month);

        return MonthResponseDTO.CreateMonthDto.builder()
                .id(MonthId)
                .memberId(memberId)
                .productName(month.getProductName())
                .frequency(month.getFrequency())
                .build();
    }
}
