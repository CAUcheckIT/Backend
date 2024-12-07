package capstone.checkIT.DTO.openAiDTO.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ImageContent extends capstone.checkIT.dto.openAiDTO.request.Content {
    private ImageUrl image_url;

    public ImageContent(String type, ImageUrl image_url) {
        super(type);
        this.image_url = image_url;
    }
}