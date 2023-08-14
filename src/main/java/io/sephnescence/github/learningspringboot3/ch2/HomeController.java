package io.sephnescence.github.learningspringboot3.ch2;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller // Spring Boot Annotation for Component Scanning
public class HomeController {
    private final VideoService videoService;

    public HomeController(VideoService videoService) {
        this.videoService = videoService;
    }

    @GetMapping("/") // Annotation for Get Request
    public String index(Model model) {
        model.addAttribute("videos", this.videoService.getVideos());

        return "index";
    }

    @PostMapping("/new-video") // Annotation for Post Request
    public String newVideo(@ModelAttribute Video newVideo) {
        this.videoService.saveNewVideo(newVideo);

        return "redirect:/"; // Spring MVC directive - Returns HTTP 302
        // 302 means it was created just fine, but it will redirect to another location
        //  Note however that at least for now I don't know how to reference a route by name,
        //  assuming such a thing even exists
        // 201 would be for an api ok response, which doesn't need a redirect
    }
}