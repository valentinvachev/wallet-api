package bg.wallet.www.project.services.impl;

import bg.wallet.www.project.enums.UserRole;
import bg.wallet.www.project.models.Role;
import bg.wallet.www.project.repositories.RoleRepository;
import bg.wallet.www.project.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {

    private RoleRepository roleRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }


    @Override
    public Role create(UserRole userRole) {
        if (userRole.equals(UserRole.ADMIN)) {
           return this.roleRepository.save(new Role(UserRole.ADMIN));
        } else {
            return this.roleRepository.save(new Role(UserRole.USER));
        }
    }

    @Override
    public Role findByAuthority(UserRole userRole) {
        return this.roleRepository.findByAuthority(userRole);
    }
}
