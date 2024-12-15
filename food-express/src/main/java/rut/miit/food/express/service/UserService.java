package rut.miit.food.express.service;

import rut.miit.food.express.dto.user.*;
import rut.miit.food.express.util.PageWrapper;

import java.util.List;

public interface UserService {
    void registerUser(UserAddDto dto);
    void updateUserInfo(UserUpdateDto dto);
    void updateUserPassword(UserChangePasswordDto dto);
    UserDto getUser(String username);
    PageWrapper<UserDto> getAllUsers(String searchQuery, int page, int size);
    List<String> getUserRoles();
    void userAdminUpdate(UserAdminUpdateDto dto);
}
