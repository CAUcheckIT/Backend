package capstone.checkIT.DTO.openAiDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TextMessage extends capstone.checkIT.dto.openAiDTO.Message {
    private String content;

    public TextMessage(String role, String content) {
        super(role);
        this.content = content;
    }
}