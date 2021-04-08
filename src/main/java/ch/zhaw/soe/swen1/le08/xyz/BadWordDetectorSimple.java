package ch.zhaw.soe.swen1.le08.xyz;

import java.util.HashSet;
import java.util.Set;

/**
 * Simple implementation of BadWordDetector. 
 *
 */
public class BadWordDetectorSimple implements BadWordDetector {
    private Set<String> badWords;

    public BadWordDetectorSimple(Set<String> badWords) {
        this.badWords = badWords;
    }

    public BadWordDetectorSimple() {
        this.badWords = new HashSet<>();
        this.badWords.add("rot");
        this.badWords.add("blau");
        this.badWords.add("orange");
    }

    @Override
    public boolean isBadWord(String word) {
        return badWords.contains(word);
    }
}
