package cn.hogrider.shareplatform.controller;

import cn.hogrider.shareplatform.entity.Task;
import cn.hogrider.shareplatform.entity.User;
import cn.hogrider.shareplatform.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserHandler {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/findAll")
    public List<User> findAll(){
        return userRepository.findAll();
    }

    @GetMapping("/findByUsername/{username}")
    public User findByUsername(@PathVariable("username") String username){
        return userRepository.findByUsername(username);
    }
    @GetMapping("/findByEmail/{email}")
    public User findByEmail(@PathVariable("email") String email){
        return userRepository.findByEmail(email);
    }

    @PostMapping("/save")
    public String save(@RequestBody User user){
        User check = userRepository.findByUsername(user.getUsername());
        if (check==null){
             if(userRepository.findByEmail(user.getEmail())!=null){
                 return "existEmail";
             }
            User result = userRepository.save(user);
            if(result != null){
                return user.getUsername();
            } else {
                return "error";
            }
        } else {
            return "existUsername";
        }

    }
    @PostMapping("/update")
    public String update(@RequestBody User user){
        User result = userRepository.save(user);
        if(result != null){
            return "success";
        } else {
            return "error";
        }
    }
    @GetMapping("/update/{username}")
    public User update(@PathVariable("username")  String username){
        return userRepository.findByUsername(username);
    }
    @PostMapping("/login")
    public String selectOne(@RequestBody User user, HttpSession session){
        String username = user.getUsername();
        String password = user.getPassword();
        User result = userRepository.findByUsername(username);
        if (result==null){
            System.out.println(110);
            return "error";
        }
        String pwd = result.getPassword();
        if (pwd.equals(password)) {
            session.setAttribute("loginUser", username);
            return username;
        }
        else{
            System.out.println(111);
            return "error";
        }
    }
}
