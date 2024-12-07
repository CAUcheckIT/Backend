package capstone.checkIT.DTO.openAiDTO.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class TextContent extends capstone.checkIT.DTO.openAiDTO.request.Content {
    private String text;

    public TextContent(String type, String text) {
        super(type);
        this.text = text;
    }
}