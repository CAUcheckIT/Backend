package capstone.checkIT.web.controller;

import capstone.checkIT.service.JoinService;
import capstone.checkIT.web.dto.JoinDTO;
import org.springframework.web.bind.annotation.PostMapping;

public class JoinController {

    private final JoinService joinService;

    public JoinController(JoinService joinService) {

        this.joinService = joinService;
    }

    @PostMapping("/join")
    public String joinProcess(JoinDTO joinDTO) {

        System.out.println(joinDTO.getUsername());
        joinService.joinProcess(joinDTO);

        return "ok";
    }


}
