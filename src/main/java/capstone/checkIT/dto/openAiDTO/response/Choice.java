package capstone.checkIT.dto.openAiDTO.response;

import capstone.checkIT.dto.openAiDTO.TextMessage;
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
