package project.price_it.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.price_it.dto.UserDto;
import project.price_it.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) {
        return ResponseEntity.status(HttpStatus.OK).body(UserDto.fromEntity(userService.create(userDto.toEntity())));
    }

    @GetMapping("/{id}")
    private ResponseEntity<UserDto> getUser(@PathVariable("id") Long id){
        return ResponseEntity.status(HttpStatus.OK).body(UserDto.fromEntity(userService.get(id)));
    }
}
