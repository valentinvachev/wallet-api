package bg.wallet.www.project.security;

import bg.wallet.www.project.models.User;
import bg.wallet.www.project.repositories.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import java.util.Collection;
import java.util.HashSet;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User userDb = userRepository.findUserByEmail(email);

        if (userDb == null) {
            throw new UsernameNotFoundException("User not found in the database");
        }

        Collection<SimpleGrantedAuthority> authorities = new HashSet<>();

        userDb.getAuthorities()
                .forEach(au->authorities.add(new SimpleGrantedAuthority(au.getAuthority().name())));

        return new org.springframework.security.core.userdetails.User(userDb.getEmail(),userDb.getPassword(),authorities);
    }
}
