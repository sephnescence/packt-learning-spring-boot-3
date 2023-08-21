package io.sephnescence.github.learningspringboot3.ch2;

import org.junit.jupiter.api.Test;

// Normally intelliJ is good with automatically figuring out what I mean when typing a class that hasn't been
// imported yet, but it has had trouble with assertThat for the months I've been using Java. Interesting. Just
// need to memorise it I guess
import static org.assertj.core.api.Assertions.*;

public class CoreDomainTest {
    @Test
    void newVideoEntityShouldHaveNullId() {
        VideoEntity videoEntity = new VideoEntity("alice", "title", "description");
        assertThat(videoEntity.getId()).isNull(); // AssertJ method - import static org.assertj.core.api.Assertions.*
        assertThat(videoEntity.getUsername()).isEqualTo("alice");
        assertThat(videoEntity.getName()).isEqualTo("title");
        assertThat(videoEntity.getDescription()).isEqualTo("description");
    }

    @Test
    void videoEntityToStringShouldWork() {
        VideoEntity videoEntity = new VideoEntity("alice", "title", "description");
        String videoEntityToString = videoEntity.toString();
        assertThat(videoEntityToString).isEqualTo("VideoEntity{id=null, username='alice', name='title', description='description'}");
    }

    @Test
    void ensureMutatorsAffectState() {
        VideoEntity videoEntity = new VideoEntity("alice", "title", "description");

        videoEntity.setId(99L);
        assertThat(videoEntity.getId()).isEqualTo(99L);

        videoEntity.setUsername("bob");
        assertThat(videoEntity.getUsername()).isEqualTo("bob");

        videoEntity.setName("name");
        assertThat(videoEntity.getName()).isEqualTo("name");

        videoEntity.setDescription("blurb");
        assertThat(videoEntity.getDescription()).isEqualTo("blurb");
    }
}
