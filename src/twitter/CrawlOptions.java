package twitter;

import org.openqa.selenium.WebElement;

import java.util.ArrayList;

public class CrawlOptions {
    private int kolMinFollower;
    private int maxTweetsPerKol;
    private int maxRepliesPerTweet;
    private int maxUserPerKeyword;
    private final String FOLLOWER_SELECTOR =  "body > div > div > div.profile-tab.sticky > div > div.profile-card-extra > div.profile-card-extra-links > ul > li.followers > span.profile-stat-num";
    private final String SHOW_MORE_SELECTOR = "body > div.container > div > div.timeline-container > div > .show-more:not(.timeline-item)";
    private final String TIMELINE_ITEM_SELECTOR = "body > div.container > div > div.timeline-container > div > div.timeline-item:not(.show-more)";

    public String getShowMoreSelector() {
        return SHOW_MORE_SELECTOR;
    }

    public String getTimelineItemSelector() {
        return TIMELINE_ITEM_SELECTOR;
    }

    public String getFollowerSelector() {
        return FOLLOWER_SELECTOR;
    }

    CrawlOptions() {
        kolMinFollower = 400000;
        maxTweetsPerKol = 2;
        maxRepliesPerTweet = 2;
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
