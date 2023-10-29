package shopapi.shopapi.service.user;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shopapi.shopapi.models.user.Role;
import shopapi.shopapi.models.user.User;
import shopapi.shopapi.repository.user.RoleRepository;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional
public class RoleService {
    private final RoleRepository roleRepository;
    private Role getOrCreateRole(String roleName){
        Role role = roleRepository.findByName(roleName);
        return Objects.requireNonNullElseGet(role, () -> roleRepository.save(new Role(roleName)));
    }
    private User setRole(User user,String roleName){
        Set<Role> roles = new HashSet<>(Objects.requireNonNullElseGet(user.getRoles(), HashSet::new));
        Role role = getOrCreateRole(roleName);
        roles.add(role);
        user.setRoles(roles);
        return user;
    }
    public User setUserRole(User user){
        return setRole(user,"ROLE_USER");
    }
    public User setSellerRole(User user){
        return setRole(user,"ROLE_SELLER");
    }
    public User setManagerRole(User user){
        return setRole(user,"ROLE_MANAGER");
    }
}
