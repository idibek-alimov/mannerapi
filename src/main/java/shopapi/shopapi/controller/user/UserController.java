package shopapi.shopapi.controller.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shopapi.shopapi.controller.user.extra.AuthenticationRequest;
import shopapi.shopapi.controller.user.extra.AuthenticationResponse;
import shopapi.shopapi.dto.user.UserInfoDto;
import shopapi.shopapi.models.user.User;
import shopapi.shopapi.service.user.UserService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {
    private final UserService userService;

    @GetMapping("/check")
    @ResponseStatus(HttpStatus.OK)
    @CrossOrigin("*")
    public String sellerShit(){
        System.out.println(userService.getCurrentUser().getUsername());
        return "hellO";
    }
    @GetMapping("/change/name/{name}")
    @ResponseStatus(HttpStatus.OK)
    @CrossOrigin("*")
    public void changeName(@PathVariable("name")String name){
        userService.changeName(name);
    }
    @GetMapping("/change/number/{number}")
    @ResponseStatus(HttpStatus.OK)
    @CrossOrigin("*")
    public void changePhoneNumber(@PathVariable("number")String number){
        userService.changePhoneNumber(number);
    }

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    @CrossOrigin("*")
    public List<User> findAll(){
        return userService.getUsers();
    }

//    @PostMapping("/save")
//    @ResponseStatus(HttpStatus.CREATED)
//    public void createProduct(@RequestBody User user){
//        userService.createUser(user);
//    }
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    @CrossOrigin("*")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody AuthenticationRequest request
    ){
        return ResponseEntity.ok(userService.register(request));
    }
//    @CrossOrigin("*")
    @PostMapping("/authenticate")
    @ResponseStatus(HttpStatus.CREATED)
    @CrossOrigin("*")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request){
        System.out.println(request.getUsername()+" "+request.getPassword());
        return ResponseEntity.ok(userService.authenticate(request));
    }
    @GetMapping("/upgrade/seller")
    @ResponseStatus(HttpStatus.CREATED)
    @CrossOrigin("*")
    public void setSellerRole(){
        userService.upgradeToSeller();
    }
    @GetMapping("/userinfo")
    @ResponseStatus(HttpStatus.CREATED)
    @CrossOrigin("*")
    public UserInfoDto getUserInfo(){
        return userService.getUserInfo();
    }
    @GetMapping("/upgrade/manager")
    @ResponseStatus(HttpStatus.CREATED)
    @CrossOrigin("*")
    public void setManagerRole(){
        userService.upgradeToManager();
    }

}
