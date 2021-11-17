package bg.wallet.www.project.services;

import bg.wallet.www.project.enums.UserRole;
import bg.wallet.www.project.models.Role;

public interface RoleService {
    Role create(UserRole userRole);
    Role findByAuthority(UserRole userRole);
}
