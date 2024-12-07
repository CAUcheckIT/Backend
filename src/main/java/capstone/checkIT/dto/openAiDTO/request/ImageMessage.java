package capstone.checkIT.DTO.openAiDTO.request;

import java.util.List;

import capstone.checkIT.DTO.openAiDTO.Message;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ImageMessage extends Message {
    private List<capstone.checkIT.DTO.openAiDTO.request.Content> content;

    public ImageMessage(String role, List<capstone.checkIT.DTO.openAiDTO.request.Content> content) {
        super(role);
        this.content = content;
    }
}