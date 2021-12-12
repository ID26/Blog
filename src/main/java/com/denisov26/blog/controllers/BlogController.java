package com.denisov26.blog.controllers;

import com.denisov26.blog.models.Post;
import com.denisov26.blog.services.PostService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class BlogController {

    private final PostService postService;

    public BlogController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/blog")
    public String blogMain(Model model) {
        Iterable<Post> posts = postService.findAll();
        model.addAttribute("posts", posts);
        return "blog-main";
    }

    @GetMapping("/blog/add")
    public String blogAdd(Model model) {
        return "blog-add";
    }

    @PostMapping("/blog/add")
    public String blogAddition(@RequestParam String title,
                               @RequestParam String anons,
                               @RequestParam String text,
                               Model model) {
        Post post = new Post(title, anons, text);
        postService.save(post);
        return "redirect:/blog";
    }

    @GetMapping("/blog/{id}")
    public String getPost(@PathVariable(value = "id") Long id, Model model) {
        if (!postService.existsById(id)) {
            return "redirect:/blog";
        }
        model.addAttribute("post", postService.findById(id));
        return "blog-details";
    }

    @GetMapping("/blog/{id}/edit")
    public String editBlog(@PathVariable(value = "id") Long id, Model model) {
        if (!postService.existsById(id)) {
            return "redirect:/blog";
        }
        model.addAttribute("post", postService.findById(id));
        return "blog-edit";
    }

    @PostMapping("/blog/{id}/edit")
    public String updatePost(@PathVariable(value = "id") Long id,
                             @RequestParam String title,
                             @RequestParam String anons,
                             @RequestParam String text,
                             Model model) {
        postService.updatePost(id, title, anons, text);
        return "redirect:/blog";
    }

    @PostMapping("/blog/{id}/delete")
    public String deletePost(@PathVariable(value = "id") Long id,
                             Model model) {
        postService.delete(id);
        return "redirect:/blog";
    }



}
