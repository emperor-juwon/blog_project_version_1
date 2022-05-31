package site.metacoding.springblogv1.web;

import java.util.Optional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import site.metacoding.springblogv1.domain.user.User;
import site.metacoding.springblogv1.domain.user.UserRepository;
import site.metacoding.springblogv1.web.dto.ResponseDto;

@Controller
public class UserController {

    //DI
    private UserRepository userRepository;
    private HttpSession session;

       public UserController(UserRepository userRepository, HttpSession session) {
           this.userRepository = userRepository;
           this.session = session;
    }
    
@GetMapping("/joinForm")
public String joinForm() {
    return "user/joinForm";
}

@GetMapping("/api/user/username/same-check")
public @ResponseBody ResponseDto<?> sameCheck(String username) {
    User userEntity = userRepository.mUsernameSameCheck(username);

    if (userEntity == null) {
        return new ResponseDto<String>(1, "성공", "같은아이디없어");
    } else {
        return new ResponseDto<String>(-1, "실패", "중복아이디있어");
    }
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
public String loginForm(HttpServletRequest request, Model model) {

    if (request.getCookies() != null) {
        Cookie[] cookies = request.getCookies();
    for (Cookie cookie : cookies) {
        System.out.println("쿠키값: " + cookie.getName());
        if (cookie.getName().equals("remember")) {
            model.addAttribute("remember", cookie.getValue());
        }
    }
    }
    
    return "user/loginForm";
}

//SELECT * FROM user WHERE username = ? AND password = ?
@PostMapping("/login")
public String login(HttpServletResponse response, User user) {

        User userEntity = userRepository.mLogin(user.getUsername(), user.getPassword());

        if (userEntity == null) {
            System.out.println("id혹은pw가 틀렸다 ");
        } else {
            System.out.println("login 성공");

            //session에 user정보 기록
            session.setAttribute("principal", userEntity); 

            if (user.getRemember() != null && user.getRemember().equals("on")) {
                response.addHeader("Set-Cookie", "remember=" + user.getUsername());
            }
        }
    return "redirect:/";
}

@GetMapping("/s/user/{id}")
public String detail(@PathVariable Integer id, Model model) {
    
    //유효성 검사
    User principal = (User) session.getAttribute("principal");
    
    //(1) 인증체크
    if (principal == null) {
        return "error/page1";
    }
    //(2)권한체크
    if (principal.getId() != id) {
        return "error/page1";
    }

    //핵심로직
    Optional<User> userOp = userRepository.findById(id);

    if (userOp.isPresent()) {
        User userEntity = userOp.get();
        model.addAttribute("user", userEntity);
        return "user/detail";
    } else {
        return "error/page1";
    }
}

@GetMapping("/s/user/updateForm")
public String updateForm() {
    return "user/updateForm";
}

@PutMapping("/s/user/{id}")
public String update(@PathVariable Integer id) {
    return "redirect:/user/" + id;
}

@GetMapping("/logout")
public String logout() {
    session.invalidate();
    return "redirect:/";
}
}
