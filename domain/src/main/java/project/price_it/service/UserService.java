package project.price_it.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.price_it.entity.UserEntity;
import project.price_it.repository.UserRepository;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    @Transactional
    public UserEntity create(UserEntity entity){
        return userRepository.save(entity);
    }

    public UserEntity get(Long id){
        return userRepository.getById(id);
    }


    public UserEntity authenticate(String email, String password) {
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("사용자 없음"));

        if (!user.getPassword().equals(password)) {
            throw new IllegalArgumentException("비밀번호 불일치");
        }

        return user;
    }
}
