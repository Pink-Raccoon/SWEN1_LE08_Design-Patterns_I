package ch.zhaw.soe.swen1.le08.forum.domain;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

/**
 * A discussion inside a topic of a form. 
 */
public class Discussion {
    private String name;
    private List<Contribution> contributions = new ArrayList<Contribution>();

    public Discussion(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    protected List<Contribution> getContributions() {
        return contributions;
    }
    
    public Contribution addUserContribution(User user, String content, Instant creationDateTime){
        // No test for duplicates
        Contribution result = new Contribution(content, user, creationDateTime);
        contributions.add(result);
        return result;
    }


}
