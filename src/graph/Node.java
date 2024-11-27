package graph;

import std.Str;

import java.util.LinkedHashMap;

public class Node{
    private String user;
    private  String xpath;

    public Node(LinkedHashMap<String, String> src) {
        this.user = src.get("user");
        this.xpath = src.get("xpath");
    }

    public Node() {
        user = "";
        xpath = "";
    }

    public Node(String user) {
        this.user = user;
        xpath = "";
    }

    public Node(String user, String xpath) {
        this.user = user;
        this.xpath = xpath;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getXpath() {
        return xpath;
    }

    public void setXpath(String xpath) {
        this.xpath = xpath;
    }

    public boolean isUser() {
        return xpath.compareTo("") == 0;
    }

    /**
     * the url of the user/tweet
     * @return
     */
    public String getUrl() {
        if (isUser()) {
            return "https://nitter.poast.org/" + user;
        }
        return "https://nitter.poast.org/" + user + "/status/" + xpath;
    }

    /**
     * @param url
     * @return the adress of the new node constructed by url
     */
    public static Node constructFromTweetUrl(String url) {
        String name = null, xpath = null;
        int u = url.lastIndexOf('/');
        xpath = url.substring(u + 1);
        int l = Str.nthIndexOf(url, '/', 3);
        int r = url.indexOf('/', l + 1);
        name = url.substring(l + 1, r);
        return new Node(name, xpath);
    }
}
