package bg.wallet.www.project.repositories;

import bg.wallet.www.project.enums.UserRole;
import bg.wallet.www.project.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {
    Role findByAuthority(UserRole role);
}
