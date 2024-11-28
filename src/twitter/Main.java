package twitter;

import graph.*;
import std.Str;
import json.JSON;
import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        CrawlOptions options = new CrawlOptions();
        Twitter agent = new Twitter(options);
//        agent.crawl(JSON.loadFromJSON("usernames.json"));
        agent.search();
    }
}
