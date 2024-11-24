package capstone.checkIT.service;

import capstone.checkIT.repository.UserRepository;
import capstone.checkIT.web.dto.JoinDTO;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public interface JoinService {
    void joinProcess(JoinDTO joinDTO);


}
