package capstone.checkIT.service.monthService;

import capstone.checkIT.DTO.MonthDTO.MonthRequestDTO;
import capstone.checkIT.DTO.MonthDTO.MonthResponseDTO;

public interface MonthService {
    MonthResponseDTO.CreateMonthDto createMonth (String accessToken, MonthRequestDTO.CreateMonthDto request);
    MonthResponseDTO.CreateMonthDto updateMonth (String accessToken, Long MonthId, MonthRequestDTO.CreateMonthDto request);
}
