package rut.miit.food.express.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import rut.miit.food.express.dto.user.*;
import rut.miit.food.express.entity.Role;
import rut.miit.food.express.entity.enums.UserRole;
import rut.miit.food.express.exception.EntityNotFoundException;
import rut.miit.food.express.repository.UserRoleRepository;
import rut.miit.food.express.util.PageWrapper;
import rut.miit.food.express.entity.User;
import rut.miit.food.express.exception.UserNotFoundException;
import rut.miit.food.express.repository.UserRepository;
import rut.miit.food.express.service.UserService;
import rut.miit.food.express.util.PaginationHelper;

import java.util.List;
import java.util.Set;
import java.util.function.BiPredicate;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserRoleRepository userRoleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void registerUser(UserAddDto dto) {
        Set<String> logins = userRepository.findAllUsernames();
        Role userRole = userRoleRepository.findRoleByName(UserRole.USER).orElseThrow(() -> new EntityNotFoundException("Role not found: " + UserRole.USER));

        User user = new User(dto.firstName(), dto.login(), dto.address(), dto.birthDate(), dto.password(), dto.confirmPassword(),
                passwordEncoder.encode(dto.password()), logins, userRole);
        userRepository.save(user);
    }

    @Override
    public void updateUserInfo(UserUpdateDto dto) {
        Set<String> phoneNumbers = userRepository.findAllPhoneNumbers();

        User user = userRepository.findByUsername(dto.username()).orElseThrow(() -> new UserNotFoundException(dto.username()));
        user.setFirstName(dto.firstName());
        user.setPhoneNumber(dto.phoneNumber(), phoneNumbers);
        user.setAddress(dto.address());
        userRepository.update(user);

    }

    @Override
    public void updateUserPassword(UserChangePasswordDto dto) {
        User user = userRepository.findByUsername(dto.username()).orElseThrow(() -> new UserNotFoundException(dto.username()));
        BiPredicate<String, String> validator = (password, encodedPassword) -> passwordEncoder.matches(password, encodedPassword);
        user.changePassword(dto.oldPassword(), dto.newPassword(), dto.confirmNewPassword(),
                passwordEncoder.encode(dto.newPassword()), validator);
        userRepository.update(user);
    }

    @Override
    public UserDto getUserById(Integer id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        return toDto(user);
    }

    @Override
    public UserDto getUser(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException(username));
        return toDto(user);
    }

    @Override
    public PageWrapper<UserDto> getAllUsers(String searchQuery, int page, int size) {
        List<UserDto> dtoList = userRepository.findByUsernameContaining(searchQuery).stream().map(this::toDto).toList();
        return PaginationHelper.getPage(dtoList, page, size);
    }

    @Override
    public List<String> getUserRoles() {
        return List.of(UserRole.USER.name(), UserRole.MODERATOR.name(), UserRole.ADMIN.name());
    }

    @Override
    public void userAdminUpdate(UserAdminUpdateDto dto) {
        User user = userRepository.findByUsername(dto.username()).orElseThrow(() -> new UserNotFoundException(dto.username()));
        Role role = userRoleRepository.findRoleByName(UserRole.valueOf(dto.role())).orElseThrow(() -> new EntityNotFoundException("Role not found: " + dto.role()));
        user.setRole(role);
        userRepository.update(user);
    }

    private UserDto toDto(User user) {
        return new UserDto(user.getId(), user.getFirstName(), user.getPhoneNumber(), user.getAddress(),
                user.getBirthDate(), user.getLogin(), user.getRole().getName().name());
    }
}
