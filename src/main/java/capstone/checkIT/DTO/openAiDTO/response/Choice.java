package capstone.checkIT.DTO.openAiDTO.response;

import capstone.checkIT.DTO.openAiDTO.TextMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Choice {
    private int index;
    private TextMessage message;
}