package capstone.checkIT.converter;

import capstone.checkIT.DTO.LocationPictureDTO;
import capstone.checkIT.entity.LocationPicture;

import java.util.List;

public class LocationPictureConverter {


    public static LocationPictureDTO.LocationPictureResponseDTO toLocationPictureResponse(LocationPicture locationPicture) {
        // LocationPicture에 연결된 LocationProduct 리스트를 DTO로 변환
        List<LocationPictureDTO.LocationProductResponseDTO> productResponses = locationPicture.getLocationProducts().stream()
                .map(locationProduct -> new LocationPictureDTO.LocationProductResponseDTO(
                        locationProduct.getId(),
                        locationProduct.getName()
                ))
                .toList();

        // LocationPicture를 포함하는 LocationPictureResponseDTO 반환
        return LocationPictureDTO.LocationPictureResponseDTO.builder()
                .id(locationPicture.getId()) // LocationPicture ID
                .memberId(locationPicture.getMember().getId()) // Member ID
                .todoId(locationPicture.getTodo().getId()) // Todo ID
                .photoUrl(locationPicture.getPhotoUrl()) // 사진 URL
                .products(productResponses) // LocationProduct 리스트
                .build();
    }
}