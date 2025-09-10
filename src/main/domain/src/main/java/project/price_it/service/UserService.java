package project.price_it.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.price_it.repository.UserRepository;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;


}
