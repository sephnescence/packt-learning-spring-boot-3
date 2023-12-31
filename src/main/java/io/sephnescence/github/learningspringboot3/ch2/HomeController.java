package io.sephnescence.github.learningspringboot3.ch2;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller // Spring Boot Annotation for Component Scanning
public class HomeController {
    private final VideoEntityService videoEntityService;

    public HomeController(VideoEntityService videoEntityService) {
        System.out.println(SecurityContextHolder.getContext().getAuthentication());
        this.videoEntityService = videoEntityService;
    }

    @GetMapping("/") // Annotation for Get Request
    public String index(Model model, Authentication authentication) {
        model.addAttribute("videoEntities", this.videoEntityService.getVideoEntities());
        model.addAttribute("authentication", authentication);

        return "index";
    }

    @GetMapping("/multi-field-search") // Annotation for Get Request
    public String multiFieldSearchGet() {
        return "redirect:/";
    }

    @PostMapping("/new-video-entity") // Annotation for Post Request
    public String newVideo(@ModelAttribute VideoEntity newVideoEntity, Authentication authentication) {
        System.out.println("Saving new video entity");
        this.videoEntityService.saveNewVideoEntity(newVideoEntity, authentication.getName());

        return "redirect:/"; // Spring MVC directive - Returns HTTP 302
        // 302 means it was created just fine, but it will redirect to another location
        //  Note however that at least for now I don't know how to reference a route by name,
        //  assuming such a thing even exists
        // 201 would be for an api ok response, which doesn't need a redirect
    }

    @PostMapping("/delete-video-entity/{videoId}")
    public String deleteVideo(@PathVariable Long videoId) { // Despite being a string inherently, this will coerce it
        videoEntityService.delete(videoId);
        return "redirect:/";
    }

    @GetMapping("/react")
    public String react() {
        return "react";
    }

    @PostMapping("/multi-field-search")
    public String multiFieldSearchPost(
        @ModelAttribute VideoSearch search,
        Model model,
        Authentication authentication
    ) {
        List<VideoEntity> searchResults = videoEntityService.search(search);

        model.addAttribute("videoEntities", searchResults);
        model.addAttribute("authentication", authentication);

        return "index";
    }
}
