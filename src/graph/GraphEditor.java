package graph;


import com.mxgraph.layout.mxCircleLayout;
import com.mxgraph.layout.mxIGraphLayout;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.util.mxCellRenderer;
import com.mxgraph.view.mxGraph;
import org.jgrapht.*;
import org.jgrapht.ext.JGraphXAdapter;
import org.jgrapht.graph.*;
import std.Pair;
import std.Str;
import json.JSON;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

class NodeComparator implements Comparator<Node> {
    @Override
    public int compare(Node p1, Node p2) {
        int tmp = p1.getUser().compareTo(p2.getUser());
        if (tmp == 0) {
            return p1.getXpath().compareTo(p2.getXpath());
        }
        return tmp;
    }
}

public class GraphEditor {
    private ArrayList<Pair<Node, Node>> edgesList;
    private Map<Node, String> mapper;
    public GraphEditor() {
        edgesList = new ArrayList<>();
        mapper = new TreeMap<>(new NodeComparator());
    }

    /**
     * add edge u -> v to the graph
     * @param u
     * @param v
     */
    public void addEdge(Node u, Node v) {
        edgesList.add(new Pair<Node, Node>(u, v));
    }

    /**
     * visualize the graph
     * @param graph
     * @throws IOException
     */
    private static void visualizeGraph(Graph<String, DefaultEdge> graph) throws IOException {
        JGraphXAdapter<String, DefaultEdge> graphAdapter =
                new JGraphXAdapter<String, DefaultEdge>(graph);
//        graphAdapter.setLabelsVisible(false);
        graphAdapter.getEdgeToCellMap().forEach((edge, cell) -> cell.setValue(null));
        mxIGraphLayout layout = new mxCircleLayout(graphAdapter);
        layout.execute(graphAdapter.getDefaultParent());

        BufferedImage image = mxCellRenderer.createBufferedImage(graphAdapter, null, 2, Color.WHITE, true, null);
        File imgFile = new File("graph.png");
        ImageIO.write(image, "PNG", imgFile);
    }

    /**
     * @param user
     * @return the file contains data of user
     */
    static public String getJSONFilename(Node user) {
        return user.getUser() + ".json";
    }

    /**
     * save the graph to the user's JSON file
     * @throws IOException
     */
    public void save(Node user) throws IOException {
        JSON.dumpToJSON(edgesList, getJSONFilename(user));
    }

    /**
     *
     * @param user
     * @return true if this user has been crawled
     */
    public static boolean crawled(Node user) {
        String nodeJSON = getJSONFilename(user);
        if (Files.exists(Paths.get(nodeJSON))) {
            return true;
        }
        return false;
    }
//    public void load() throws IOException {
//        ArrayList<LinkedHashMap<String, String>> arr1 = JSON.loadFromJSON("nodes.json");
//        userList = new TreeSet<>(new NodeComparator());
//        for (LinkedHashMap<String, String> tmp : arr1) {
//            userList.add(new Node(tmp));
//        }
//        ArrayList<LinkedHashMap<LinkedHashMap<String, String>, LinkedHashMap<String, String> >> arr2 = JSON.loadFromJSON("edges.json");
//        edgesList = new ArrayList<>();
//        for (LinkedHashMap<LinkedHashMap<String, String>, LinkedHashMap<String, String> > tmp : arr2) {
//            edgesList.add(new Pair<Node, Node>(new Node(tmp.get("key")), new Node(tmp.get("value"))));
//        }
//    }
}
