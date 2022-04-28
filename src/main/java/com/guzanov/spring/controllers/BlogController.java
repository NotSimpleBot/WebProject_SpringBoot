package com.guzanov.spring.controllers;

import com.guzanov.spring.models.Post;
import com.guzanov.spring.repositories.PostDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Optional;

@Controller
public class BlogController {
    @Autowired
    private PostDAO postDAO;

    @GetMapping("/blog")
    public String blogMain(Model model) {
        Iterable<Post> iterable = postDAO.findAll(); //массив со всеми статьями
        model.addAttribute("posts", iterable);
        return "blog-main";
    }

    @GetMapping("/blog/{id}/edit")
    public String editBlog(@PathVariable("id") long id, Model model) {
        if (!postDAO.existsById(id))
            return "redirect:/blog";
        Optional<Post> optional = postDAO.findById(id);
        optional.ifPresent(post -> model.addAttribute("post", post));
        return "blog-add";
    }
    @GetMapping("/blog/{id}/remove")
    public String removeBlog(@PathVariable("id") long id, Model model) {
        if (!postDAO.existsById(id))
            return "redirect:/blog";
        Optional<Post> optional = postDAO.findById(id);
        optional.ifPresent(post -> postDAO.delete(post));
        return "redirect:/blog";
    }

    @GetMapping("/blog/add")
    public String blogAdd(Model model) {
        Post post = new Post();
        model.addAttribute("post", post);
        return "blog-add";
    }

    @PostMapping("/blog/add")
    public String blogAddPost(@RequestParam long id, @RequestParam String title, @RequestParam String anons, @RequestParam String full_text, Model model) {
        Post result;
        Optional<Post> optionalPost = postDAO.findById(id);
        result = optionalPost.orElseGet(Post::new);
        result.setTitle(title);
        result.setAnons(anons);
        result.setFullText(full_text);
        postDAO.save(result);
        return "redirect:/blog";
    }

    @GetMapping("/blog/{id}")
    public String getBlogById(@PathVariable(value = "id") long id, Model model) {
        if (!postDAO.existsById(id))
            return "redirect:/blog";
        Optional<Post> optionalPost = postDAO.findById(id);
        ArrayList<Post> res = new ArrayList<>();
        optionalPost.ifPresent(res::add);
        model.addAttribute("post", res);
        return "blog-info";
    }
}
