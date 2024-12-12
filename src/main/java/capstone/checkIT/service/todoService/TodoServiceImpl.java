package capstone.checkIT.service.todoService;

import capstone.checkIT.DTO.TodoDTO.TodoResponseDTO;
import capstone.checkIT.apipayLoad.code.status.ErrorStatus;
import capstone.checkIT.config.JwtManager;
import capstone.checkIT.converter.TodoConverter;
import capstone.checkIT.entity.Product;
import capstone.checkIT.entity.Todo;
import capstone.checkIT.exception.GeneralException;
import capstone.checkIT.repository.MemberRepository;
import capstone.checkIT.repository.ProductRepository;
import capstone.checkIT.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TodoServiceImpl implements TodoService {
    private final MemberRepository memberRepository;
    private final JwtManager jwtManager;
    private final TodoRepository todoRepository;
    private final ProductRepository productRepository;
    public TodoResponseDTO.TomorrowResponse getTomorrowList(String accessToken, Long todoId){
        Long memberId = jwtManager.validateJwt(accessToken);

        Todo todo=todoRepository.findByIdAndMemberId(todoId, memberId);

        return TodoConverter.toProductResponse(todo);
    }

    public TodoResponseDTO.TomorrowResponse createTomorrow(String accessToken, Long todoId){
        Long memberId = jwtManager.validateJwt(accessToken);

        Todo todo=todoRepository.findByIdAndMemberId(todoId, memberId);
        List<String> productsNames= List.of(todo.getTomorrowImg().split(","));

        List<Product> products = productsNames.stream()
                .map(productName -> Product.builder()
                        .name(productName)
                        .build())
                .toList();
        productRepository.saveAll(products);

        return TodoConverter.toProductResponse(todo);


    }

    public void deleteTomorrow(String accessToken, Long productId){
        Long memberId = jwtManager.validateJwt(accessToken);
        Product product = productRepository.findByMemberIdAndId(memberId, productId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.PRODUCT_NOT_EXIST));

        productRepository.delete(product);

    }

    public TodoResponseDTO.TomorrowResponse addTomorrow(String accessToken, Long todoId, String productName) {
        Long memberId = jwtManager.validateJwt(accessToken);

        Todo todo = todoRepository.findByIdAndMemberId(todoId, memberId);

        Product product = Product.builder()
                .name(productName)
                .build();

        todo.getProductList().add(product);
        todoRepository.save(todo);

        return TodoConverter.toProductResponse(todo);
    }

    public TodoResponseDTO.TomorrowResponse updateTomorrow(String accessToken, Long productId, String productName){
        Long memberId = jwtManager.validateJwt(accessToken);
        Product product = productRepository.findByMemberIdAndId(memberId, productId)
               .orElseThrow(() -> new GeneralException(ErrorStatus.PRODUCT_NOT_EXIST));

        product.setName(productName);
        productRepository.save(product);

        return TodoConverter.toProductResponse(product.getTodo());
    }


}
