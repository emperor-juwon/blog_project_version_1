package site.metacoding.springblogv1.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

@Controller
public class UserController {
    
@GetMapping("/joinForm")
public String joinForm() {
    return "user/joinForm";
}

@PostMapping("/join")
public String join() {
    return "redirect:/user/loginForm";
}

@GetMapping("/loginForm")
public String loginForm() {
    return "user/loginForm";
}

@PostMapping("/login")
public String login() {
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
