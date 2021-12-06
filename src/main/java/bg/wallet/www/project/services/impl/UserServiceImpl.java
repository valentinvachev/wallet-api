package bg.wallet.www.project.services.impl;

import bg.wallet.www.project.enums.UserRole;
import bg.wallet.www.project.exceptions.DuplicateEntityException;
import bg.wallet.www.project.exceptions.EntityNotFoundException;
import bg.wallet.www.project.models.Role;
import bg.wallet.www.project.models.User;
import bg.wallet.www.project.models.binding.UserRegisterBindingModel;
import bg.wallet.www.project.models.service.UserServiceModel;
import bg.wallet.www.project.models.view.UserViewInfoModel;
import bg.wallet.www.project.repositories.UserRepository;
import bg.wallet.www.project.services.RoleService;
import bg.wallet.www.project.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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



    @Override
    public List<UserViewInfoModel> findAllUsers() {
        List<User> usersDb = this.userRepository.findAll();

        List<UserViewInfoModel> result =  usersDb.stream().map(u->{
            UserViewInfoModel userViewInfoModel = this.modelMapper.map(u,UserViewInfoModel.class);
            userViewInfoModel.setRoles(u.getAuthorities().stream().map(a->a.getAuthority().toString()).collect(Collectors.toSet()));
            return userViewInfoModel;
        }).collect(Collectors.toList());

        return result;
    }

    @Override
    public void changeUserRoles(Long id,Boolean isAdmin) throws EntityNotFoundException {
        User userDb = this.userRepository.getById(id);

        if (userDb == null) {
            throw new EntityNotFoundException("User does not exist");
        }

        Set<Role> currentAuthorities = userDb.getAuthorities();

        if (isAdmin) {
            Role role = this.roleService.findByAuthority(UserRole.ADMIN);
            if (role == null) {
                role = this.roleService.create(UserRole.ADMIN);
            }
            Set<Role> newAuthorities = new HashSet<>(currentAuthorities);
            newAuthorities.add(role);
            userDb.setAuthorities(newAuthorities);
        } else {
            Set<Role> newAuthorities = currentAuthorities
                    .stream()
                    .filter(role -> !role.getAuthority().equals(UserRole.ADMIN))
                    .collect(Collectors.toSet());

            userDb.setAuthorities(newAuthorities);
        }
        this.userRepository.save(userDb);
    }

    public void save(UserRegisterBindingModel userRegisterBindingModel) throws DuplicateEntityException {
        User userDb = this.findByEmail(userRegisterBindingModel.getEmail());

        if (userDb != null) {
            throw new DuplicateEntityException("User with such email already exists");
        }

        Set<Role> roles = new HashSet<>();

        if (this.userRepository.count() == 0) {
            Role roleAdmin = this.roleService.create(UserRole.ADMIN);
            Role roleUser = this.roleService.create(UserRole.USER);

            roles.add(roleAdmin);
            roles.add(roleUser);
        } else {
            Role roleUser = this.roleService.findByAuthority(UserRole.USER);
            roles.add(roleUser);
       }


        UserServiceModel userServiceModel = this.modelMapper.map(userRegisterBindingModel,UserServiceModel.class);
        User user = this.modelMapper.map(userServiceModel,User.class);

        user.setPassword(this.passwordEncoder.encode(user.getPassword()));
        user.setAuthorities(roles);

        this.userRepository.save(user);
    }

    @Override
    public UserViewInfoModel findUserInfoByEmail(String email) throws EntityNotFoundException {
        User userDb = this.findByEmail(email);

        if (userDb == null) {
            throw new EntityNotFoundException("User does not exist");
        }

        UserViewInfoModel userViewInfoModel = this.modelMapper.map(userDb,UserViewInfoModel.class);
        userViewInfoModel.setRoles(userDb.getAuthorities().stream().map(a->a.getAuthority().toString()).collect(Collectors.toSet()));

        return userViewInfoModel;
    }

    @Override
    public User findByEmail(String email) {
        return this.userRepository.findUserByEmail(email);
    }
}
