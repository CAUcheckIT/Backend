package capstone.checkIT.DTO;

import lombok.*;

import java.util.List;

public class LocationPictureDTO {



    @Builder
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LocationPictureResponseDTO {
        private Long id;
        private Long memberId; // Member ID
        private Long todoId; // Todo ID
        private String photoUrl; // 사진 URL
        private List<LocationProductResponseDTO> products; // LocationProduct 리스트
    }

    @Builder
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LocationProductResponseDTO {
        private Long id;
        private String name;
    }
}
