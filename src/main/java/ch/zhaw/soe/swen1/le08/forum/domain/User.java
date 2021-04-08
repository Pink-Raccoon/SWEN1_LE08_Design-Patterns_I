package ch.zhaw.soe.swen1.le08.forum.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a user that participates in this forum.
 */
public class User {
    private String name;
    private String email;
    private List<Contribution> contributions = new ArrayList<Contribution>();

    public User(String name, String email) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    protected List<Contribution> getContributions() {
        return contributions;
    }

    public void addContribution(Contribution contribution) {
        contributions.add(contribution);
    }

    public int getNbrOfContributions() {
        return contributions.size();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
