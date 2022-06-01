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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.RequiredArgsConstructor;
import site.metacoding.springblogv1.domain.user.User;
import site.metacoding.springblogv1.service.UserService;
import site.metacoding.springblogv1.web.dto.ResponseDto;

@RequiredArgsConstructor
@Controller
public class UserController {

    //DI
    private final HttpSession session;
    private final UserService userService;


    @GetMapping("/joinForm")
    public String joinForm() {
        return "user/joinForm";
    }

    @GetMapping("/api/user/username/same-check")
    public @ResponseBody ResponseDto<?> sameCheck(String username) {

        String data = userService.유저네임중복검사(username);
        return new ResponseDto<>(1, "통신성공", data);
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

        //핵심로직
        userService.회원가입(user);

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
public String login(User user, HttpServletResponse response) {

    User userEntity = userService.로그인(user);

    if (userEntity != null) {
            //session에 user정보 기록
            session.setAttribute("principal", userEntity); 
        if (user.getRemember() != null && user.getRemember().equals("on")) {
            response.addHeader("Set-Cookie", "remember=" + user.getUsername());
        }
            return"redirect:/";
        }else{
        return "redirect:/loginForm";
        }
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
        User userEntity = userService.유저정보보기(id);

        if (userEntity == null) {
            return "error/page1";
        } else {
            model.addAttribute("user", userEntity);
            return "user/detail";
        }
       
    }

    @GetMapping("/s/user/updateForm")
    public String updateForm() {
        return "user/updateForm";
    }

    @PutMapping("/s/user/{id}")
    public @ResponseBody ResponseDto<String> update(@PathVariable Integer id, @RequestBody User user) {
 
        User principal = (User) session.getAttribute("principal");

        //1. 인증체크
        if (principal == null) {
            return new ResponseDto<String>(-1, "인증실패", null);
        }

        if (principal.getId() != id) {
            return new ResponseDto<String>(-1, "권한 없음", null);
        }

        User userEntity = userService.유저수정(id, user);
        session.setAttribute("pricipal", userEntity);

        return new ResponseDto<String>(1, "성공", null);
    }

    @GetMapping("/logout")
    public String logout() {
        session.invalidate();
        return "redirect:/";
    }
}
