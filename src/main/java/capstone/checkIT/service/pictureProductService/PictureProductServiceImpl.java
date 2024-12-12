package capstone.checkIT.service.pictureProductService;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import capstone.checkIT.DTO.TodoDTO.TodoResponseDTO;
import capstone.checkIT.DTO.openAiDTO.request.ChatGPTRequest;
import capstone.checkIT.DTO.openAiDTO.response.ChatGPTResponse;
import capstone.checkIT.apipayLoad.code.status.ErrorStatus;
import capstone.checkIT.config.JwtManager;
import capstone.checkIT.converter.TodoConverter;
import capstone.checkIT.entity.Member;
import capstone.checkIT.entity.Product;
import capstone.checkIT.entity.Todo;
import capstone.checkIT.entity.TodoToday;
import capstone.checkIT.exception.GeneralException;
import capstone.checkIT.repository.MemberRepository;
import capstone.checkIT.repository.ProductRepository;
import capstone.checkIT.repository.TodoRepository;
import capstone.checkIT.repository.TodoTodayRepository;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class PictureProductServiceImpl implements PictureProductService {

    @Value("${openai.model}")
    private String apiModel;

    @Value("${openai.api.url}")
    private String apiUrl;

    private final RestTemplate template;
    private final TodoRepository todoRepository;
    private final JwtManager jwtManager;
    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;
    private final TodoTodayRepository todoTodayRepository;

//    public ChatGPTResponse requestTextAnalysis(String requestText) {
//        ChatGPTRequest request = ChatGPTRequest.createTextRequest(apiModel, 500, "user", requestText);
//        return template.postForObject(apiUrl, request, ChatGPTResponse.class);
//    }

    // ImageUrl을 만듦 -> base64Image
    public TodoResponseDTO.TomorrowResponse requestImageAnalysis(String accessToken, String imageUrl, String requestText)  throws IOException{

        // 1. JWT 토큰에서 memberId 추출
        Long memberId = jwtManager.validateJwt(accessToken);

        // 2. 멤버 검증
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.LOGIN_ERROR_EMAIL));


        //String base64Image = Base64.encodeBase64String(image.getBytes());
        //String imageUrl = "data:image/jpeg;base64," + base64Image;
        ChatGPTRequest request = ChatGPTRequest.createImageRequest(apiModel, 500, "user", requestText, imageUrl);
        ChatGPTResponse response = template.postForObject(apiUrl, request, ChatGPTResponse.class);
        String productString = response.getChoices().get(0).getMessage().getContent();

        Todo todo = Todo.builder()
                .member(member)
                .date(LocalDate.now())
                .tomorrowImg(productString)
                .checkImg(productString)
                .build();
        Todo savedTodo = todoRepository.save(todo);
        List<String> productsNames= List.of(productString.split(","));

        List<Product> products = productsNames.stream()
                .map(productName -> Product.builder()
                        .member(member)
                        .todo(savedTodo)
                        .name(productName)
                        .build())
                .toList();
        productRepository.saveAll(products);

        return TodoConverter.toProductResponse(savedTodo);
    }


    public TodoResponseDTO.TodayResponse requestTakeImageAnalysis(String accessToken, String imageUrl, String requestText, Long todoId)  throws IOException{

        // 1. JWT 토큰에서 memberId 추출
        Long memberId = jwtManager.validateJwt(accessToken);

        // 2. 멤버 검증
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.LOGIN_ERROR_EMAIL));


        //String base64Image = Base64.encodeBase64String(image.getBytes());
        //String imageUrl = "data:image/jpeg;base64," + base64Image;
        ChatGPTRequest request = ChatGPTRequest.createImageRequest(apiModel, 500, "user", requestText, imageUrl);
        ChatGPTResponse response = template.postForObject(apiUrl, request, ChatGPTResponse.class);
        String productString = response.getChoices().get(0).getMessage().getContent();

        Todo todo = todoRepository.findByIdAndMemberId(todoId, memberId);
        if (todo == null) {
            throw new GeneralException(ErrorStatus.TODO_NOT_FOUND); // 적절한 예외 처리
        }
        todo.setCheckImg(productString);

        List<String> todayNames= List.of(productString.split(","));

        List<TodoToday> todoTodays = todayNames.stream()
                .map(todayName -> TodoToday.builder()
                        .member(member)
                        .todo(todo)
                        .name(todayName)
                        .build())
                .toList();
        todoTodayRepository.saveAll(todoTodays);

        return TodoConverter.todayResponse(todo);

    }
//    // ImageUrl을 만듦
//    //원래 throws IOException 포함
//    public ChatGPTResponse requestImageAnalysis(MultipartFile image, String requestText)  throws IOException{
//        String base64Image = Base64.encodeBase64String(image.getBytes());
//        String imageUrl = "data:image/jpeg;base64," + base64Image;
//        ChatGPTRequest request = ChatGPTRequest.createImageRequest(apiModel, 500, "user", requestText, imageUrl);
//        return template.postForObject(apiUrl, request, ChatGPTResponse.class);
//    }
}