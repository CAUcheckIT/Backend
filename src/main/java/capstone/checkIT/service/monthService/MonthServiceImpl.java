package capstone.checkIT.service.monthService;

import capstone.checkIT.DTO.MonthDTO.MonthRequestDTO;
import capstone.checkIT.DTO.MonthDTO.MonthResponseDTO;
import capstone.checkIT.apipayLoad.code.status.ErrorStatus;
import capstone.checkIT.config.JwtManager;
import capstone.checkIT.entity.*;
import capstone.checkIT.exception.GeneralException;
import capstone.checkIT.repository.CheckRepository;
import capstone.checkIT.repository.MemberRepository;
import capstone.checkIT.repository.MonthRepository;
import capstone.checkIT.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MonthServiceImpl implements MonthService {
    private final MonthRepository monthRepository;
    private final JwtManager jwtManager;
    private final MemberRepository memberRepository;
    private final CheckRepository checkRepository;
    private final ProductRepository productRepository;
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

    @Override
    @Transactional
    public void deleteMonth (String accessToken, Long MonthId){
        Long memberId = jwtManager.validateJwt(accessToken);
        Month month = monthRepository.findByIdAndMemberId(MonthId, memberId)
               .orElseThrow(() -> new GeneralException(ErrorStatus.MONTH_NOT_EXIST));

        monthRepository.delete(month);
    }

    @Override
    @Transactional
    public MonthResponseDTO.getMonthDto getMonth (String accessToken, Long MonthId){
        Long memberId = jwtManager.validateJwt(accessToken);
        Month month = monthRepository.findByIdAndMemberId(MonthId, memberId)
               .orElseThrow(() -> new GeneralException(ErrorStatus.MONTH_NOT_EXIST));

        return MonthResponseDTO.getMonthDto.builder()
                .monthId(month.getId())
               .memberId(month.getMember().getId())
               .sentence(month.getSentence())
               .checkDay(month.getCheckDay())
               .build();
    }

    @Override
    @Transactional
    public MonthResponseDTO.getMonthDto achieveDay(String accessToken,Long monthId,boolean newIsStart){
        Long memberId= jwtManager.validateJwt(accessToken);
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.LOGIN_ERROR_EMAIL));
        Month month = monthRepository.findByIdAndMemberId(monthId, memberId)
                .orElseThrow(()->new GeneralException(ErrorStatus.MONTH_NOT_EXIST));

        Product product=productRepository.findByName(month.getProductName())
                .orElseThrow(()->new GeneralException(ErrorStatus.PRODUCT_NOT_EXIST));


        //기존 isStart 상태 확인
        boolean currentIsStart= member.getIsStart();

        //isStart가 true -> false로 변경된 경우만 처리
        if(currentIsStart && !newIsStart){
            List<Check> checks=checkRepository.findByProductId(product.getId());

            long trueCheckCount=checks.stream()
                    .filter(Check::getIsChecked)
                    .count();
            boolean allTrue=checks.stream()
                    .allMatch(Check::getIsChecked);

            if(allTrue || trueCheckCount >=3){
                // month의 startDate부터 오늘까지의 날짜 차이를 계산
                long daysDifference = ChronoUnit.DAYS.between(month.getStartDate(), LocalDate.now()) + 1;

                List<String> checkDayList = month.getCheckDay(); // 기존 리스트 가져오기
                if (checkDayList == null) {
                    checkDayList = new ArrayList<>(); // 리스트가 null이면 새로 생성
                }
                checkDayList.add(String.valueOf(daysDifference));
                month.setCheckDay(checkDayList);
                monthRepository.save(month);
            }

        }

        return MonthResponseDTO.getMonthDto.builder()
                .monthId(month.getId())
                .memberId(memberId)
                .productName(product.getName())
                .sentence(month.getSentence())
                .checkDay(month.getCheckDay())
                .build();




    }
}
