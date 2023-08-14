package io.sephnescence.github.learningspringboot3.ch2;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service // Spring Boot Annotation for Component Scanning
public class VideoService {
    private final List<Video> videos = new ArrayList<>();

    public VideoService() {
        this.videos.add(new Video("Hideo 1"));
        this.videos.add(new Video("Hideo 2"));
        this.videos.add(new Video("Hideo 3"));
    }
    public List<Video> getVideos() {
        return videos;
    }

    public void saveNewVideo(Video newVideo) {
        /*
         Not that this is actually persisting to the db, it is persisted in memory while
          the server remains alive. Meaning another request to the index page will see
          any videos that have been added before
         The book notes this is not thread safe and also notes that it
          will make no attempt to fix this. So I refused to use the book's List.copyOf(),
          and instead added a constructor to this class to instantiate a list of Videos
          that we can add to. e.g.
         List<Video> extend = new ArrayList<>(videos);
         extend.add(newVideo);
         this.videos = List.copyOf(extend);
        */

        this.videos.add(newVideo);
    }
}
