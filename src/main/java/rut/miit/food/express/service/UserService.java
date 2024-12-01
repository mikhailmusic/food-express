package rut.miit.food.express.service;

import rut.miit.food.express.dto.user.UserAddDto;
import rut.miit.food.express.dto.user.UserChangePasswordDto;
import rut.miit.food.express.dto.user.UserUpdateDto;
import rut.miit.food.express.dto.user.UserDto;

import java.util.List;

public interface UserService {
    void registerUser(UserAddDto userDto);
    void updateUserInfo(UserUpdateDto userDto);
    void updateUserPassword(UserChangePasswordDto userDto);
    UserDto getUser(Integer id);
    UserDto getUserByUsername(String username);
    List<UserDto> getAllUsers();


//    void deactivateAccount(Integer id);
}
