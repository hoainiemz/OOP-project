package std;

import graph.GraphEditor;
import graph.Node;
import json.JSON;
import twitter.CrawlOptions;
import twitter.Twitter;

import java.io.IOException;

public class Environment {
    public static void search() throws InterruptedException, IOException {
        CrawlOptions options = new CrawlOptions();
        Twitter agent = new Twitter(options);
        agent.search();
    }

    public static void crawl() throws InterruptedException, IOException {
        CrawlOptions options = new CrawlOptions();
        Twitter agent = new Twitter(options);
        agent.crawl(JSON.loadFromJSON("usernames.json"));
    }

    public static void main(String[] args) throws InterruptedException, IOException     {
//        crawl();
        GraphEditor graph = new GraphEditor();
        graph.load();
        graph.addEdge(new Node("no1"), new Node("no2"));
    }
}
