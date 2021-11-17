package bg.wallet.www.project.services.impl;

import bg.wallet.www.project.enums.UserRole;
import bg.wallet.www.project.models.Role;
import bg.wallet.www.project.models.User;
import bg.wallet.www.project.models.binding.UserRegisterBindingModel;
import bg.wallet.www.project.models.service.UserServiceModel;
import bg.wallet.www.project.repositories.UserRepository;
import bg.wallet.www.project.services.RoleService;
import bg.wallet.www.project.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;
    private final ModelMapper modelMapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleService roleService, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
        this.modelMapper = modelMapper;
    }

    public void save(UserRegisterBindingModel userRegisterBindingModel) {
        //TODO check if such an email already exists

        Role role = null;

        if (this.userRepository.count() == 0) {
            role = this.roleService.create(UserRole.ADMIN);
            this.roleService.create(UserRole.USER);
        } else {
            role = this.roleService.findByAuthority(UserRole.USER);
       }

        Set<Role> roles = new HashSet<>();
        roles.add(role);

        UserServiceModel userServiceModel = this.modelMapper.map(userRegisterBindingModel,UserServiceModel.class);
        User user = this.modelMapper.map(userServiceModel,User.class);

        user.setPassword(this.passwordEncoder.encode(user.getPassword()));
        user.setAuthorities(roles);

        this.userRepository.save(user);
    }

    @Override
    public User findByEmail(String email) {
        return this.userRepository.findUserByEmail(email);
    }
}
