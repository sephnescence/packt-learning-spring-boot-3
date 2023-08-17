package io.sephnescence.github.learningspringboot3.ch2;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ApiController {
    private final VideoEntityService videoEntityService;

    public ApiController(VideoEntityService videoEntityService) {
        this.videoEntityService = videoEntityService;
    }

    @GetMapping("/api/videos")
    public List<VideoEntity> all() {
        return this.videoEntityService.getVideoEntities();
    }

    @PostMapping("/api/videos")
    public VideoEntity post(@RequestBody VideoEntity newVideoEntity, Authentication authentication) { // Note RequestBody instead of ModelAttribute
        this.videoEntityService.saveNewVideoEntity(newVideoEntity, authentication.getName());

        return newVideoEntity;
    }
}
