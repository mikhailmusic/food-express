package rut.miit.food.express.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rut.miit.food.express.dto.user.UserAddDto;
import rut.miit.food.express.dto.user.UserChangePasswordDto;
import rut.miit.food.express.dto.user.UserUpdateDto;
import rut.miit.food.express.dto.user.UserDto;
import rut.miit.food.express.entity.User;
import rut.miit.food.express.exception.UserNotFoundException;
import rut.miit.food.express.repository.UserRepository;
import rut.miit.food.express.service.UserService;

import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void registerUser(UserAddDto dto) {
        Set<String> logins = userRepository.findAllUsernames();

        // TODO: хешировать пароль!
        User user = new User(dto.firstName(), dto.login(), dto.address(), dto.birthDate(), dto.password(), dto.confirmPassword(), logins);
        userRepository.save(user);
    }

    @Override
    public void updateUserInfo(UserUpdateDto dto) {
        Set<String> phoneNumbers = userRepository.findAllPhoneNumbers();

        User user = userRepository.findById(dto.id()).orElseThrow(() -> new UserNotFoundException(dto.id()));
        user.setFirstName(dto.firstName());
        user.setPhoneNumber(dto.phoneNumber(), phoneNumbers);
        user.setAddress(dto.address());
        userRepository.save(user);

    }

    @Override
    public void updateUserPassword(UserChangePasswordDto dto) {
        // TODO: хешировать пароль!
        User user = userRepository.findById(dto.id()).orElseThrow(() -> new UserNotFoundException(dto.id()));
        user.changePassword(dto.oldPassword(), dto.newPassword(), dto.confirmNewPassword());
        userRepository.save(user);
    }

    @Override
    public UserDto getUser(Integer id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        return toDto(user);
    }

    @Override
    public UserDto getUserByUsername(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException(username));
        return toDto(user);
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map(this::toDto).toList();
    }

    private UserDto toDto(User user) {
        return new UserDto(user.getId(), user.getFirstName(), user.getPhoneNumber(), user.getAddress(),
                user.getBirthDate(), user.getLogin());
    }
}
