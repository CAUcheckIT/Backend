package capstone.checkIT.service;

import capstone.checkIT.entity.UserEntity;
import capstone.checkIT.repository.UserRepository;
import capstone.checkIT.web.dto.JoinDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class JoinServiceImpl implements JoinService{

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public void joinProcess(JoinDTO joinDTO) {
        String username=joinDTO.getUsername();
        String password=joinDTO.getPassword();
        Boolean isExist=userRepository.existsByUsername(username);
        if(isExist) {
            return;
        }

        UserEntity data = new UserEntity();

        data.setUsername(username);
        data.setPassword(bCryptPasswordEncoder.encode(password));
        data.setRole("ROLE_ADMIN");

        userRepository.save(data);
    }
}
