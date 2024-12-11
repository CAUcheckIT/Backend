package capstone.checkIT.service.monthService;

import capstone.checkIT.DTO.MonthDTO.MonthRequestDTO;
import capstone.checkIT.DTO.MonthDTO.MonthResponseDTO;
import capstone.checkIT.entity.Month;

public interface MonthService {
    MonthResponseDTO.CreateMonthDto createMonth (String accessToken, MonthRequestDTO.CreateMonthDto request);
    MonthResponseDTO.CreateMonthDto updateMonth (String accessToken, Long MonthId, MonthRequestDTO.CreateMonthDto request);
    void deleteMonth (String accessToken, Long MonthId);
    MonthResponseDTO.getMonthDto getMonth(String accessToken, Long MonthId);
    MonthResponseDTO.getMonthDto achieveDay(String accessToken,Long monthId,boolean newIsStart);
}
