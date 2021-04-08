package ch.zhaw.soe.swen1.le08.forum.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * Combined tests for forum domain logic. Because domain classes are quite simple, 
 * only integration tests have been written. 
 */
@ExtendWith(MockitoExtension.class)
public class ForumTest {
    private Forum forum;
    private User user;
    byte[] accessToken;
    private Instant reference;

    @BeforeEach
    public void setUp() throws Exception {
        reference = Instant.ofEpochSecond(1000000);
        // use a clock which returns always the same time, so testing is easy
        forum = new Forum(Clock.fixed(reference, ZoneId.systemDefault()));
        user = new User("User", "email");
        accessToken = new byte[] { 0 };
        forum.getUsers().add(user);
    }
    
    @Test
    public void addContributionToDiscussionAndAssertIt() {
        String sessionId = null; // Dummy implementation of session handling
        
        // user, topic and discussion must be created first
        forum.addNewTopic(sessionId,"Topic", "Description");
        forum.addNewDiscussion(sessionId, "Topic", "Discussion");
        
        // add contribution to discussion
        forum.addUserContribution(sessionId, "Topic", "Discussion", "Content");
                
        // assert newly added contribution
        Topic topic = forum.getTopics().get(0); 
        Discussion discussion = topic.getDiscussionForName("Discussion");
        assertEquals(1, discussion.getContributions().size());
        Contribution contribution = discussion.getContributions().get(0);
        assertNotNull(contribution);
        assertEquals("Content", contribution.getContent());
        assertSame(user, contribution.getUser());
        assertEquals(reference, contribution.getCreationDateTime());        
    }
    
}
