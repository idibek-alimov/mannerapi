package shopapi.shopapi.service.user;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import shopapi.shopapi.controller.user.extra.AuthenticationRequest;
import shopapi.shopapi.controller.user.extra.AuthenticationResponse;
import shopapi.shopapi.dto.user.UserInfoDto;
import shopapi.shopapi.filter.JwtService;
import shopapi.shopapi.models.user.User;
import shopapi.shopapi.repository.user.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;

    public List<User> getUsers(){
        return userRepository.findAll();
    }
    public User createUser(User user){
        return userRepository.save(user);
    }

    public AuthenticationResponse registerJonibek(AuthenticationRequest request){
        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();
        user = userRepository.save(user);
        this.setUserRole(user);
        this.upgradeToSeller();
        this.upgradeToManager();
        var accessToken = jwtService.generateToken(user,1);
        var refreshToken = jwtService.generateToken(user,2);
        return AuthenticationResponse.builder().access_token(accessToken).refresh_token(refreshToken).build();

    }
    public AuthenticationResponse register(AuthenticationRequest request){
        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();
        user = userRepository.save(user);
        this.setUserRole(user);
        var accessToken = jwtService.generateToken(user,1);
        var refreshToken = jwtService.generateToken(user,2);
        return AuthenticationResponse.builder().access_token(accessToken).refresh_token(refreshToken).build();
    }
    public User getCurrentUser(){
        if (!SecurityContextHolder.getContext().getAuthentication().getPrincipal().equals("anonymousUser")){
            return (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        }
        else {
            return null;
        }
    }
    public void changeName(String name){
        User user = this.getCurrentUser();
        if(user == null)
            return ;
        user.setName(name);
        userRepository.save(user);
    }
    public void changePhoneNumber(String number){
        User user = this.getCurrentUser();
        if(user == null)
            return ;
        user.setPhoneNumber(number);
        userRepository.save(user);
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request){
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(),request.getPassword()));
        var user = userRepository.findByUsername(request.getUsername());
        var accessToken = jwtService.generateToken(user,1);
        var refreshToken = jwtService.generateToken(user,2);
        return AuthenticationResponse.builder().access_token(accessToken).refresh_token(refreshToken).build();

    }
    public UserInfoDto getUserInfo(){
        User user = getCurrentUser();
        if(user == null)
            return null;
        return userToUserInfoDto(user);
    }
    public UserInfoDto userToUserInfoDto(User user){
        return UserInfoDto.builder()
                .name(user.getName())
                .phoneNumber(user.getPhoneNumber())
                .build();
    }
    public void upgradeToSeller(){
        userRepository.save(roleService.setSellerRole(this.getCurrentUser()));
    }
    public void upgradeToManager(){
        userRepository.save(roleService.setManagerRole(this.getCurrentUser()));
    }
    public void setUserRole(User user){
        userRepository.save(roleService.setUserRole(user));
    }

}
