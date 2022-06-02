package site.metacoding.springblogv1.web;

import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.RequiredArgsConstructor;
import site.metacoding.springblogv1.domain.post.Post;
import site.metacoding.springblogv1.domain.post.PostRepository;
import site.metacoding.springblogv1.domain.user.User;
import site.metacoding.springblogv1.service.PostService;
import site.metacoding.springblogv1.web.dto.ResponseDto;

@RequiredArgsConstructor
@Controller
public class PostController {

    private final  HttpSession session;
    private final PostRepository postRepository;
    private final PostService postService;

                @GetMapping({"/","/post/list"})
                public String list(Model model, @RequestParam(defaultValue = "0") Integer page) {

                Page<Post> pagePosts = postService.글목록보기(page);
                model.addAttribute("posts", pagePosts);
                model.addAttribute("nextPage", page + 1);
                model.addAttribute("prevPage", page - 1);

                return "post/list";
            }


        @GetMapping("/s/post/writeForm")
        public String writeForm() {

            //인증 체크
            if (session.getAttribute("principal") == null) {
                return "redirect:/loginForm";
            }
            return "post/writeForm";
        }
    
        
              @GetMapping("/post/{id}")
        public String detail(@PathVariable Integer id, Model model) {

            Post postEntity = postService.글상세보기(id);

            Optional<Post> postOp = postRepository.findById(id);

            if (postEntity == null) {
                return "error/page1";
            } else {
                model.addAttribute("post", postEntity);
                return "post/detail";
            }
        }

      
        @GetMapping("/s/post/{id}/updateForm")
        public String updateForm(@PathVariable Integer id) {
            return "post/updateForm";
        }

        @DeleteMapping("/s/post/{id}")
        public @ResponseBody ResponseDto<String> delete(@PathVariable Integer id)
        {
            User principal = (User) session.getAttribute("principal");

            //인증 확인
            if (principal == null) {
                return new ResponseDto<String>(-1, "로그인이 필요합니다.", null);
            }
            Post postEntity = postService.글상세보기(id);

            //글 권한 확인
            if (principal.getId() != postEntity.getUser().getId()) {
                return new ResponseDto<String>(-1, "글 삭제할 권한이 없습니다.", null);
            }
            postService.글삭제하기(id);
            return new ResponseDto<String>(1, "성공", null);
        }

        @PutMapping("/s/post/{id}")
        public String update(@PathVariable Integer id) {
            return "redirect:/post/" + id;
        }

        @PostMapping("/s/post")
        public String write(Post post) {

            User principal = (User)session.getAttribute("principal");

            postService.글쓰기(post, principal);
            return "redirect:/";
        }
    }
