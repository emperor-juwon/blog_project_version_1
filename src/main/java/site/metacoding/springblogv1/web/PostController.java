package site.metacoding.springblogv1.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

@Controller
public class PostController {
    
        @GetMapping("/s/post/writeForm")
        public String writeForm() {
            return "post/writeForm";
        }
    
        @GetMapping({"/","/post/list"})
        public String list() {
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
        public String write() {
            return "redirect:/";
        }
    }
