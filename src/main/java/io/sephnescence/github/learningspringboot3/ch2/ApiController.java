package io.sephnescence.github.learningspringboot3.ch2;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ApiController {
    private final VideoService videoService;

    public ApiController(VideoService videoService) {
        this.videoService = videoService;
    }

    @GetMapping("/api/videos")
    public List<Video> all() {
        return this.videoService.getVideos();
    }

    @PostMapping("/api/videos")
    public Video post(@RequestBody Video newVideo) { // Note RequestBody instead of ModelAttribute
        this.videoService.saveNewVideo(newVideo);

        return newVideo;
    }
}
