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
    private Set<Node> userList;
    private ArrayList<Pair<Node, Node>> edgesList;
    private Map<Node, String> mapper;
    public GraphEditor() {
        userList = new TreeSet<>(new NodeComparator());
        edgesList = new ArrayList<>();
        mapper = new TreeMap<>(new NodeComparator());
    }

    public boolean addNode(Node node) {
        return userList.add(node);
    }

    public void addEdge(Node u, Node v) {
        edgesList.add(new Pair<Node, Node>(u, v));
    }

    private static void displayGraph(Graph<String, DefaultEdge> graph) throws IOException {
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


//    private static void displayGraph(Graph<String, DefaultEdge> graph) {
//        // Tạo mxGraph từ đồ thị JGraphT
//        mxGraph mxGraph = new mxGraph();
//
//        // Lấy đối tượng mặc định của mxGraph
//        Object parent = mxGraph.getDefaultParent();
//
//        // Bắt đầu cập nhật đồ thị
//        mxGraph.getModel().beginUpdate();
//        try {
//            // Lưu vị trí các đỉnh
//            java.util.Map<String, Object> vertexMap = new java.util.HashMap<>();
//
//            // Tạo các biến kiểm soát cho vị trí của các đỉnh
//            int userX = 100;   // X cho các đỉnh "User"
//            int tweetX = 1000;  // X cho các đỉnh "Tweet"
//            int yStart = 100;  // Y bắt đầu cho các đỉnh
//            int verticalSpacing = 200;  // Khoảng cách giữa các đỉnh theo chiều dọc
//
//            // Đếm số đỉnh bắt đầu với "User" và "Tweet"
//            int userCount = 0;
//            int tweetCount = 0;
//
//            // Độ phân tán ngẫu nhiên cho các node
//            for (String vertex : graph.vertexSet()) {
//                int y = 0;
//                int randomXOffset = (int) (Math.random() * 100) - 100; // Ngẫu nhiên từ -50 đến 50
//                int randomYOffset = (int) (Math.random() * 100) - 100; // Ngẫu nhiên từ -50 đến 50
//
//                if (vertex.startsWith("User")) {
//                    y = yStart + userCount * verticalSpacing + randomYOffset;
//                    userCount++;
//                } else if (vertex.startsWith("Tweet")) {
//                    y = yStart + tweetCount * verticalSpacing + randomYOffset;
//                    tweetCount++;
//                }
//
//                // Xác định vị trí x của đỉnh
//                int x = vertex.startsWith("User") ? userX + randomXOffset : tweetX + randomXOffset;
//
//                // Thêm đỉnh vào đồ thị
//                Object vertexCell = mxGraph.insertVertex(parent, null, vertex, x, y, 80, 30);
//                vertexMap.put(vertex, vertexCell);
//            }
//
//            // Thêm các cạnh vào mxGraph
//            for (DefaultEdge edge : graph.edgeSet()) {
//                String source = graph.getEdgeSource(edge);
//                String target = graph.getEdgeTarget(edge);
//
//                Object sourceVertex = vertexMap.get(source);
//                Object targetVertex = vertexMap.get(target);
//
//                if (sourceVertex != null && targetVertex != null) {
//                    mxGraph.insertEdge(parent, null, "", sourceVertex, targetVertex);
//                }
//            }
//
//            // Vẽ khung bao quanh các node "User"
//            int userMinX = userX - 500;  // Vị trí X tối thiểu cho "User"
//            int userMaxX = userX + 100 + 50 * userCount;  // Vị trí X tối đa cho "User"
//            int userMinY = yStart - 100;  // Vị trí Y tối thiểu cho "User"
//            int userMaxY = yStart + 100 * userCount + 100;  // Vị trí Y tối đa cho "User"
//
//            // Thêm khung cho "User" với viền màu xanh và nền màu xanh nhạt
//            mxGraph.insertVertex(parent, null, "", userMinX, userMinY, userMaxX - userMinX, userMaxY - userMinY,
//                    "shape=rectangle;strokeWidth=1;strokeColor=#000000;fillColor=none;");
//
//            // Vẽ khung bao quanh các node "Tweet"
//            int tweetMinX = tweetX - 100;  // Vị trí X tối thiểu cho "Tweet"
//            int tweetMaxX = tweetX + 100 + 100 * tweetCount;  // Vị trí X tối đa cho "Tweet"
//            int tweetMinY = yStart - 100;  // Vị trí Y tối thiểu cho "Tweet"
//            int tweetMaxY = yStart + 100 * tweetCount + 100;  // Vị trí Y tối đa cho "Tweet"
//
//            // Thêm khung cho "Tweet" với viền màu xanh lá cây và nền màu xanh nhạt
//            mxGraph.insertVertex(parent, null, "", tweetMinX, tweetMinY, tweetMaxX - tweetMinX, tweetMaxY - tweetMinY,
//                    "shape=rectangle;strokeWidth=1;strokeColor=#000000;fillColor=none;");
//
//        } finally {
//            mxGraph.getModel().endUpdate();  // Kết thúc cập nhật đồ thị
//        }
//
//        // Tạo cửa sổ đồ thị
//        SwingUtilities.invokeLater(() -> {
//            JFrame frame = new JFrame();
//            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//
//            // Tạo JPanel và thiết lập đồ thị vào nó
//            JPanel panel = new JPanel(new BorderLayout());
//            mxGraphComponent graphComponent = new mxGraphComponent(mxGraph);
//            panel.add(graphComponent, BorderLayout.CENTER);
//
//            // Thêm panel vào cửa sổ
//            frame.getContentPane().add(panel);
//
//            // Thiết lập kích thước cửa sổ và hiển thị
//            frame.setSize(800, 600);
//            frame.setVisible(true);
//
//            // Đảm bảo giao diện được làm mới ngay lập tức
//            panel.revalidate();
//            panel.repaint();
//        });
//    }

    public void build() throws IOException {
        Graph<String, DefaultEdge> graph = new DefaultDirectedGraph<>(DefaultEdge.class);
        int cnt = 0;
        for (Node u : userList) {
            if (u.isUser()) {
                mapper.put(u, u.getUser());
                graph.addVertex(u.getUser());
            }
            else {
                mapper.put(u, Str.itos(++cnt));
                graph.addVertex(Str.itos(cnt));
            }
        }
        for (Pair<Node, Node> pnn : edgesList) {
            String u = mapper.get(pnn.getKey()), v = mapper.get(pnn.getValue());
            graph.addEdge(mapper.get(pnn.getKey()), mapper.get(pnn.getValue()));
        }
        displayGraph(graph);
        System.out.println("Image created!");
    }

    public void save() throws IOException {
        JSON.dumpToJSON(new ArrayList<Node>(userList), "nodes.json");
        JSON.dumpToJSON(edgesList, "edges.json");
    }

    public void load() throws IOException {
        ArrayList<LinkedHashMap<String, String>> arr1 = JSON.loadFromJSON("nodes.json");
        userList = new TreeSet<>(new NodeComparator());
        for (LinkedHashMap<String, String> tmp : arr1) {
            userList.add(new Node(tmp));
        }
        ArrayList<LinkedHashMap<LinkedHashMap<String, String>, LinkedHashMap<String, String> >> arr2 = JSON.loadFromJSON("edges.json");
        edgesList = new ArrayList<>();
        for (LinkedHashMap<LinkedHashMap<String, String>, LinkedHashMap<String, String> > tmp : arr2) {
            edgesList.add(new Pair<Node, Node>(new Node(tmp.get("key")), new Node(tmp.get("value"))));
        }
    }

    public void longMemorySave() throws IOException {
        JSON.dumpToJSON(new ArrayList<Node>(userList), "nodes_data.json");
        JSON.dumpToJSON(edgesList, "edges_data.json");
    }
}
