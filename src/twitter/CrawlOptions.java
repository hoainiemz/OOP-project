package twitter;

import org.openqa.selenium.WebElement;

import java.util.ArrayList;

public class CrawlOptions {
    private int kolMinFollower;
    private int maxTweetsPerKol;
    private int maxRepliesPerTweet;
    private int maxUserPerKeyword;

    CrawlOptions() {
        kolMinFollower = 200000;
        maxTweetsPerKol = 120;
        maxRepliesPerTweet = 120;
        maxUserPerKeyword = 20;
    }

    public int getMaxUserPerKeyword() {
        return maxUserPerKeyword;
    }

    public void setMaxUserPerKeyword(int maxUserPerKeyword) {
        this.maxUserPerKeyword = maxUserPerKeyword;
    }

    public int getMaxTweetsPerKol() {
        return maxTweetsPerKol;
    }

    public void setMaxTweetsPerKol(int maxTweetsPerKol) {
        this.maxTweetsPerKol = maxTweetsPerKol;
    }

    public int getMaxRepliesPerTweet() {
        return maxRepliesPerTweet;
    }

    public void setMaxRepliesPerTweet(int maxRepliesPerTweet) {
        this.maxRepliesPerTweet = maxRepliesPerTweet;
    }

    public int getKolMinFollower() {
        return kolMinFollower;
    }

    public void setKolMinFollower(int kolMinFollower) {
        this.kolMinFollower = kolMinFollower;
    }

    public int getMaxTweetPerUser() {
        return maxTweetsPerKol;
    }

    public void setMaxTweetPerUser(int maxTweetsPerKol) {
        this.maxTweetsPerKol = maxTweetsPerKol;
    }
}
