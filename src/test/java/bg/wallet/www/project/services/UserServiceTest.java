package bg.wallet.www.project.services;

import bg.wallet.www.project.enums.UserRole;
import bg.wallet.www.project.exceptions.EntityNotFoundException;
import bg.wallet.www.project.models.Role;
import bg.wallet.www.project.models.User;
import bg.wallet.www.project.models.view.UserViewInfoModel;
import bg.wallet.www.project.repositories.UserRepository;
import bg.wallet.www.project.services.impl.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class UserServiceTest {

    private User testUser;
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private RoleService roleService;
    private ModelMapper modelMapper;

    private String TEST_USER1_USERNAME = "admin";
    private String TEST_USER1_EMAIL = "admin@abv.bg";
    private String TEST_USER1_PASS = "parola123";

    @BeforeEach
    public void init() {

        Set<Role> roles = new HashSet<>();
        Role role = new Role(UserRole.ADMIN);
        role.setId(1L);
        roles.add(role);

        this.testUser = new User()
                .setUsername(TEST_USER1_USERNAME)
                .setAuthorities(roles)
                .setEmail(TEST_USER1_EMAIL)
                .setPassword(TEST_USER1_PASS);

        this.userRepository = Mockito.mock(UserRepository.class);
        this.passwordEncoder = Mockito.mock(PasswordEncoder.class);
        this.roleService = Mockito.mock(RoleService.class);
        this.modelMapper = new ModelMapper();

    }

    @Test
    public void userServiceFindUserByEmail() {
        Mockito.when(this.userRepository.findUserByEmail(TEST_USER1_EMAIL))
                .thenReturn(this.testUser);

        UserService userService = new UserServiceImpl(this.userRepository,this.passwordEncoder,this.roleService,this.modelMapper);

        User expected = this.testUser;
        User actual = userService.findByEmail(TEST_USER1_EMAIL);

        assertEquals(expected.getEmail(),actual.getEmail());
    }


    @Test
    public void userServiceFindUserEmailInvalid() {
        Mockito.when(this.userRepository.findUserByEmail(TEST_USER1_EMAIL))
                .thenReturn(this.testUser);

        UserService userService = new UserServiceImpl(this.userRepository,this.passwordEncoder,this.roleService,this.modelMapper);

        User actual = userService.findByEmail(TEST_USER1_EMAIL);

        assertNotEquals("",actual.getEmail());
    }

    @Test
    public void userServiceFindUserInfoByEmailInvalidThrows() {
        Mockito.when(this.userRepository.findUserByEmail(TEST_USER1_EMAIL))
                .thenReturn(null);

        UserService userService = new UserServiceImpl(this.userRepository,this.passwordEncoder,this.roleService,this.modelMapper);

        EntityNotFoundException thrown = assertThrows(
                EntityNotFoundException.class,
                () -> userService.findUserInfoByEmail("Invalid")
        );

      assertTrue(thrown.getMessage().contains("User does not exist"));
      assertEquals(thrown.getClass(),EntityNotFoundException.class);
    }

    @Test
    public void userServiceFindUserInfoByEmailValid() throws EntityNotFoundException {
        Mockito.when(this.userRepository.findUserByEmail(TEST_USER1_EMAIL))
                .thenReturn(this.testUser);

        UserService userService = new UserServiceImpl(this.userRepository,this.passwordEncoder,this.roleService,this.modelMapper);

        UserViewInfoModel actual = userService.findUserInfoByEmail(TEST_USER1_EMAIL);

       assertEquals(this.testUser.getEmail(),actual.getEmail());
    }

    @Test
    public void userServiceFinalAllUsers() {
        Mockito.when(this.userRepository.findAll())
                .thenReturn(List.of(this.testUser));

        UserService userService = new UserServiceImpl(this.userRepository,this.passwordEncoder,this.roleService,this.modelMapper);

        List<UserViewInfoModel> actual = userService.findAllUsers();

        assertEquals(1,actual.size());
    }

    @Test
    public void userServiceFinalAllUsersCorrectEmail() {
        Mockito.when(this.userRepository.findAll())
                .thenReturn(List.of(this.testUser));

        UserService userService = new UserServiceImpl(this.userRepository,this.passwordEncoder,this.roleService,this.modelMapper);

        List<UserViewInfoModel> actual = userService.findAllUsers();

        assertEquals(this.testUser.getEmail(),actual.get(0).getEmail());
    }
}
