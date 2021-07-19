package board.board.controller;

import board.board.domain.Post;
import board.board.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;



@Controller
public class PostController {

    private final PostService postService;


    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/posts/new")
    public String createForm(Model model){
        model.addAttribute("post", new PostForm());
        return "posts/createPostForm";
    }
    @PostMapping("/posts/new")
    public String create(PostForm form) {
        Post post = new Post();
        post.setName(form.getName());
        post.setContent(form.getContent());


        postService.join(post);

        return "redirect:/";
        //홈화면으로
    }

    @GetMapping("/posts")
    public String list(Model model){
        List<Post> posts = postService.findPosts();
        model.addAttribute("posts", posts);
        return "posts/postList";
    }


}