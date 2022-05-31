package site.metacoding.springblogv1.web;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import lombok.RequiredArgsConstructor;
import site.metacoding.springblogv1.domain.post.Post;
import site.metacoding.springblogv1.domain.post.PostRepository;
import site.metacoding.springblogv1.domain.user.User;

@RequiredArgsConstructor
@Controller
public class PostController {

    private final  HttpSession session;
    private final PostRepository postRepository;
    
        @GetMapping("/s/post/writeForm")
        public String writeForm() {

            //인증 체크
            if (session.getAttribute("principal") == null) {
                return "redirect:/loginForm";
            }
            return "post/writeForm";
        }
    
        @GetMapping({"/","/post/list"})
        public String list(Model model) {
            model.addAttribute("posts", postRepository.findAll());
            return "post/list";
        }

        @GetMapping("/post/{id}")
        public String detail(@PathVariable Integer id) {
            return "post/detailForm";
        }

        @GetMapping("/s/post/{id}/updateForm")
        public String updateForm(@PathVariable Integer id) {
            return "post/updateForm";
        }

        @DeleteMapping("/s/post/{id}")
        public String delete(@PathVariable Integer id) {
            return "redirect:/";
        }

        @PutMapping("/s/post/{id}")
        public String update(@PathVariable Integer id) {
            return "redirect:/post/" + id;
        }

        @PostMapping("/s/post")
        public String write(Post post) {

            User principal = (User)session.getAttribute("principal");

            //인증체크
            if (session.getAttribute("principal") == null) {
                return "redirect:/loginForm";
            }

            post.setUser(principal);
            postRepository.save(post);

            return "redirect:/";
        }
    }
