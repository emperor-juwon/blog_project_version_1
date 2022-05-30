package site.metacoding.springblogv1.web;

import java.lang.ProcessBuilder.Redirect;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import site.metacoding.springblogv1.domain.user.User;
import site.metacoding.springblogv1.domain.user.UserRepository;

@Controller
public class UserController {

    //DI
    private UserRepository userRepository;

       public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    
@GetMapping("/joinForm")
public String joinForm() {
    return "user/joinForm";
}

@PostMapping("/join")
public String join(User user) {
    //유효성체크
    if (user.getUsername() == null || user.getPassword() == null || user.getEmail() == null) {
        return "redirect:/joinForm";
    }
    if (user.getUsername().equals("") || user.getPassword().equals("") || user.getEmail().equals("")) {
        return "redirect:/joinForm";
    }

    // 핵심로직
    User userEntity = userRepository.save(user);
        

    return "redirect:/loginForm";
}

@GetMapping("/loginForm")
public String loginForm() {
    return "user/loginForm";
}

//SELECT * FROM user WHERE username = ? AND password = ?
@PostMapping("/login")
public String login(HttpServletRequest request, User user) {

         HttpSession session = request.getSession();

        User userEntity = userRepository.mLogin(user.getUsername(), user.getPassword());

        if (userEntity == null) {
            System.out.println("id혹은pw가 틀렸다 ");
        } else {
            System.out.println("login 성공");
            session.setAttribute("principal", userEntity);
        }
    return "redirect:/";
}

@GetMapping("/user/{id}")
public String detail(@PathVariable Integer id) {
    return "/user/detail";
}

@GetMapping("/user/{id}/updateForm")
public String updateForm() {
    return "user/updateForm";
}

@PutMapping("/user/{id}")
public String update(@PathVariable Integer id) {
    return "redirect:/user/" + id;
}

@GetMapping("/logout")
public String logout() {
    return "redirect:/";
}
}
