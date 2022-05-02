package site.metacoding.springblogv1.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

@Controller
public class PostController {
    
        @GetMapping("/post/writeForm")
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

        @GetMapping("/post/{id}/updateForm")
        public String updateForm(@PathVariable Integer id) {
            return "post/updateForm";
        }

        @DeleteMapping("/post/{id}")
        public String delete(@PathVariable Integer id) {
            return "redirect:/";
        }

        @PutMapping("/post/{id}")
        public String update(@PathVariable Integer id) {
            return "redirect:/post/" + id;
        }

        @PostMapping("/post")
        public String write() {
            return "redirect:/";
        }
    }
