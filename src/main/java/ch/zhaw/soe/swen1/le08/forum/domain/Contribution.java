package ch.zhaw.soe.swen1.le08.forum.domain;

import java.time.Instant;

/**
 * Contribution or post of one user with content. 
 */
public class Contribution {
    private final String content;
    private final User user;
    private final Instant creationDateTime;

    public Contribution(final String content, final User user, final Instant creationDateTime) {
        this.content = content;
        this.user = user;
        this.creationDateTime = creationDateTime;
        user.addContribution(this);
    }

    public User getUser() {
        return user;
    }

    public String getContent() {
        return content;
    }

    public Instant getCreationDateTime() {
        return creationDateTime;
    }
}
