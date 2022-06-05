package ch.zhaw.soe.swen1.le08.forum.domain;

import java.time.Clock;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents the whole forum with its list of topics and users.
 */
public class Forum {
    private Clock clock;
    private List<User> users = new ArrayList<>();
    private List<Topic> topics = new ArrayList<>();

    public Forum(Clock clock) {

        this.clock = clock;
    }

    /**
     * Returns the list of users. Just for tests.  
     */
    protected List<User> getUsers() {
        return users;
    }
    
    /**
     * Returns the list of topics. Just for tests.  
     */
    protected List<Topic> getTopics() {
        return topics;
    }

    /**
     * Returns the topic with the specified name or null if there is no such topic. 
     */
    protected Topic getTopicForName(String name){
        for(Topic topic : topics){
            if (topic.getName().equals(name)){
                return topic;
            }
        }
        return null;
    }
    
    /**
     * Returns the user with the specified name or null if there is no such user. 
     */
    protected User getUserForName(String name){
        for(User user : users){
            if (user.getName().equals(name)){
                return user;
            }
        }
        return null;
    }
    
    /**
     * Creates and adds a new topic with the specified attributes. 
     */
    public void addNewTopic(String sessionId, String topicName, String description){
        checkSessionId(sessionId);
        Objects.requireNonNull(topicName, "Topic name must be not null");
        Topic existingTopic = getTopicForName(topicName);
        if (existingTopic != null){
            throw new RuntimeException("A topic with the name " + topicName + " already exists");
        }
        Topic topic = new Topic(topicName, description);
        topics.add(topic);
    }
    
   /**
     * Adds a new contribution to the specified topic, discussion for the specified user. 
     * The topic and discussion must have been created before. 
     * @throws SessionExpirationException 
     */
    public void addUserContribution(String sessionId, 
            String topicName, String discussionName, String contribution) {
        Objects.requireNonNull(topicName, "Topic name must be not null");
        Objects.requireNonNull(discussionName, "Discussion name must be not null");
        User user = checkSessionId(sessionId);
        Topic topic = getTopicForName(topicName);
        if (topic == null){
            throw new RuntimeException("Invalid topic name");
        }
        Instant creationTimeDate = Instant.now(clock);
        topic.addUserContribution(user, discussionName, contribution, creationTimeDate);
    }

    /**
     * Adds a new contribution to the specified topic. 
     * @throws SessionExpirationException 
     */
    public void addNewDiscussion(String sessionId,  
            String topicName, String discussionName){
        checkSessionId(sessionId);
        Topic topic = getTopicForName(topicName);
        Objects.requireNonNull(topicName, "Topic name must be not null");
        Objects.requireNonNull(discussionName, "Discussion name must be not null");
        topic.addNewDiscussion(discussionName);
    }
        
    private User checkSessionId(String sessionId) {
        // Dummy implementation. Session handling omitted for clarity. 
        if (users.isEmpty()) {
            throw new RuntimeException("Invalid session");
        }
        return users.get(0); 
    }
    

}
