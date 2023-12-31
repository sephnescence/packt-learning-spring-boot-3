package io.sephnescence.github.learningspringboot3.ch2;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.List;

@Service // Spring Boot Annotation for Component Scanning
public class VideoEntityService {
    private final VideoEntityRepository videoEntityRepository;

    public VideoEntityService(VideoEntityRepository videoEntityRepository) {
        this.videoEntityRepository = videoEntityRepository;
    }
    public List<VideoEntity> getVideoEntities() {
        return this.videoEntityRepository.findAll();
    }
    public List<VideoEntity> search(VideoSearch videoSearch) {
        // System.out.println("Name: " + videoSearch.name());
        // System.out.println("Description: " + videoSearch.description());
        // Empty strings don't pass StringUtils.hasText
        if (StringUtils.hasText(videoSearch.name()) && StringUtils.hasText(videoSearch.description())) {
            System.out.println("Searching for both name and description");
            return this.videoEntityRepository.findByNameContainsOrDescriptionContainsAllIgnoreCase(videoSearch.name(), videoSearch.description());
        }

        if (StringUtils.hasText(videoSearch.name())) {
            System.out.println("Searching for name");
            return this.videoEntityRepository.findByNameContainsIgnoreCase(videoSearch.name());
        }

        if (StringUtils.hasText(videoSearch.description())) {
            System.out.println("Searching for description");
            return this.videoEntityRepository.findByDescriptionContainsIgnoreCase(videoSearch.description());
        }

        return Collections.emptyList();
    }

    public void saveNewVideoEntity(VideoEntity newVideoEntity, String username) {
        /*
         Not that this is actually persisting to the db, it is persisted in memory while
          the server remains alive. Meaning another request to the index page will see
          any video entities that have been added before
         The book notes this is not thread safe and also notes that it
          will make no attempt to fix this. So I refused to use the book's List.copyOf(),
          and instead added a constructor to this class to instantiate a list of Video Entities
          that we can add to. e.g.
         List<VideoEntity> extend = new ArrayList<>(videoEntities);
         extend.add(newVideoEntity);
         this.videoEntities = List.copyOf(extend);
        */

        this.videoEntityRepository.saveAndFlush(new VideoEntity(username, newVideoEntity.getName(), newVideoEntity.getDescription()));
    }

    public void delete(Long videoId) {
        /*
        findById() returns Optional<VideoEntity>, so we can call map even though it's going to be one video entity, and
        throw a RuntimeException if no video entities were returned
         */
        this.videoEntityRepository
                .findById(videoId)
                .map(videoEntity -> {
                    this.videoEntityRepository.delete(videoEntity);
                    return true;
                })
                .orElseThrow(() -> new RuntimeException("No video at " + videoId));
    }

    @PostConstruct
    void initDatabase() {
        this.videoEntityRepository.save(new VideoEntity("alice", "Hideo 1", "Kojimaaa"));
        this.videoEntityRepository.save(new VideoEntity("alice", "Hideo 2", "Kojimaaa"));
        this.videoEntityRepository.save(new VideoEntity("bob", "Hideo 3", "Kojimaaa"));
    }
}
