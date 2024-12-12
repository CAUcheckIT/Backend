package capstone.checkIT.converter;

import capstone.checkIT.DTO.TodoDTO.TodoResponseDTO;
import capstone.checkIT.entity.Todo;

import java.util.List;

public class TodoConverter {
    public static TodoResponseDTO.TomorrowResponse toProductResponse (Todo todo){

        List<TodoResponseDTO.TomorrowResponse.ProductResponse> todoResponses= todo.getProductList().stream()
                .map(product -> new TodoResponseDTO.TomorrowResponse.ProductResponse(
                        product.getId(),
                        product.getName()

                ))
                .toList();


        return TodoResponseDTO.TomorrowResponse.builder()
                .id(todo.getId())
                .memberId(todo.getMember().getId())
                .date(todo.getDate())
                .products(todoResponses)
                .build();


    }

    public static TodoResponseDTO.TodayResponse todayResponse(Todo todo){
        List<TodoResponseDTO.TodayResponse.TodoTodayResponse> todayResponses=todo.getTodoTodayList().stream()
                .map(todoToday -> new TodoResponseDTO.TodayResponse.TodoTodayResponse(
                        todoToday.getId(),
                        todoToday.getName()
                ))
                .toList();

        return TodoResponseDTO.TodayResponse.builder()
                .id(todo.getId())
                .memberId(todo.getMember().getId())
                .date(todo.getDate())
                .todays(todayResponses)
                .build();
    }
}
