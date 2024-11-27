package twitter;

import graph.*;
import json.JSON;
import org.openqa.selenium.By;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import std.Str;

import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Twitter{
    private CrawlOptions options;
    private WebDriver driver;
    private Actions actions;
    private WebDriverWait wait;
    private int counter = 0;

    public Twitter(CrawlOptions options) throws InterruptedException {
        this.options = options;
        ChromeOptions driverOptions = new ChromeOptions();
        driverOptions.setPageLoadStrategy(PageLoadStrategy.NORMAL);
        System.setProperty("webdriver.chrome.driver", "/usr/bin/chromedriver");
        this.driver = new ChromeDriver(driverOptions);
        wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        visit("https://nitter.poast.org/");
        TimeUnit.SECONDS.sleep(5);
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    public void visit(String url) throws InterruptedException {
        if (!url.startsWith("https://")) {
            url = "https://" + url;
        }
        this.driver.navigate().to(url);
    }

    private WebElement findElement(String selector) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(selector)));
    }

    public void crawlTweet(Node tweet, GraphEditor graph) throws InterruptedException, IOException {
        visit(tweet.getUrl());
        graph.addNode(tweet);
        while (true) {
            int cnt = 0;
            List<WebElement> replies = driver.findElements(By.cssSelector("#r > .reply.thread.thread-line"));
            for (WebElement reply : replies) {
                String handle = reply.findElement(By.cssSelector("a.username")).getAttribute("innerHTML");
                if (handle.charAt(0) == '@') {
                    handle = handle.substring(1);
                }
                if (handle.compareTo(tweet.getUser()) == 0) {
                    continue;
                }
                Node commenter = new Node(handle);
                crawlUser(commenter, graph, false);
                graph.addEdge(commenter, tweet);
                cnt++;
                if (cnt >= options.getMaxRepliesPerTweet()) {
                    break;
                }
            }
            graph.save();
            if (cnt >= options.getMaxRepliesPerTweet()) {
                break;
            }
            try {
                WebElement showMore = driver.findElement(By.cssSelector("#r > .show-more"));
                showMore.click();
            }
            catch(Exception NoSuchElementException) {
                break;
            }
        }
    }

    public void crawlUser(Node user, GraphEditor graph, boolean getTweet) throws InterruptedException, IOException {
        if (!getTweet || !graph.addNode(user)) {
            return;
        }
        visit(user.getUrl());
        int followers = Str.stoi(findElement("body > div > div > div.profile-tab.sticky > div > div.profile-card-extra > div.profile-card-extra-links > ul > li.followers > span.profile-stat-num").getAttribute("innerHTML"));
        if (followers < options.getKolMinFollower()) {
            return;
        }
        setCounter(getCounter() + 1);
        List<String> tweets = new ArrayList<>();
        int cnt = 0;
        while (true) {
            wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("body > div.container > div > div.timeline-container > div > .show-more")));
            List<WebElement> showMore = driver.findElements(By.cssSelector("body > div.container > div > div.timeline-container > div > .show-more"));
            List<WebElement> timeline = driver.findElements(By.cssSelector("body > div.container > div > div.timeline-container > div > div.timeline-item"));
            if (timeline.size() < 5 || cnt >= options.getMaxTweetPerUser()) {
                break;
            }
            for (int i = 1; cnt < options.getMaxTweetPerUser() && i < timeline.size(); i++) {
                WebElement tweet = timeline.get(i);
                String href = tweet.findElement(By.cssSelector("a.tweet-link")).getAttribute("href");
                tweets.add(href);
                cnt++;
            }
            graph.save();
            showMore.get(showMore.size() - 1).click();
        }
        for (String url : tweets) {
            Node tweet = Node.constructFromTweet(url);
            crawlTweet(tweet, graph);
            if (user.getUser().compareTo(tweet.getUser()) == 0) {
                graph.addEdge(tweet, user);
            }
            else {
                graph.addEdge(user, tweet);
            }
        }
        graph.save();
        System.out.println("Done! :))");
    }

    public void crawlKeyword(String keyword, ArrayList<String> list) throws InterruptedException, IOException {
        String url = "https://nitter.poast.org/search?f=users&q=" + keyword;
        visit(url);
        int cnt = 0;
        while (true) {
            if (cnt >= options.getMaxUserPerKeyword()) {
                break;
            }
            WebElement showMore;
            ArrayList<WebElement> timeLine = (ArrayList<WebElement>) driver.findElements(By.cssSelector("body > div > div > div.timeline > .timeline-item:not(.show-more)"));
            try {
                showMore = driver.findElement(By.cssSelector("body > div > div > div.timeline > .show-more:not(.timeline-item)"));
            }
            catch(Exception NoSuchElementException) {
                break;
            }
            for (WebElement user : timeLine) {
                String handle = user.findElement(By.cssSelector("a.username")).getAttribute("innerHTML");
                if (handle.charAt(0) == '@') {
                    handle = handle.substring(1);
                }
                list.add(handle);
                cnt++;
                if (cnt >= options.getMaxUserPerKeyword()) {
                    break;
                }
            }
            JSON.dumpToJSON(list, "usernames.json");
            showMore.click();
        }
    }

    public void search() throws IOException, InterruptedException {
        ArrayList<String> keyWords = JSON.loadFromJSON("searchingkeywords.json");
        ArrayList<String> handleList = new ArrayList<>();
        for (String keyWord : keyWords) {
           crawlKeyword(keyWord, handleList);
        }
    }

    void crawl(ArrayList<String> handles, GraphEditor graph) throws IOException, InterruptedException {
        for (String handle : handles) {
            crawlUser(new Node(handle), graph, true);
        }
        System.out.println("Done! :)");
        System.out.println("Crawled datas from " + getCounter() + " KOLs.");
    }
}
