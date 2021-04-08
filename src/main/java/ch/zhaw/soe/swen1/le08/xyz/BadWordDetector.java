package ch.zhaw.soe.swen1.le08.xyz;

/** 
 * Interface for detecting single bad words. 
 *
 */
public interface BadWordDetector {
    /**
     * Returns true if the provided word is considered bad language. 
     * @param word
     * @return
     */
    boolean isBadWord(String word);
}
