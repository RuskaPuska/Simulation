package Simulation;

import java.util.*;

public class Test {
    Node addOrGetNode(HashMap<Integer, Node> graph, int value) {
        if (value == -1) {
            return null;
        }

        if (graph.containsKey(value)) {
            return graph.get(value);
        }

        Node node = new Node(value);
        graph.put(value, node);
        return node;
    }

    HashMap<Integer, Node> createGraph(int[][] graphData) {
        HashMap<Integer, Node> graph = new HashMap<>();

        for (int[] row : graphData) {
            Node node = addOrGetNode(graph, row[0]);
            Node adjacentNode = addOrGetNode(graph, row[1]);
            if (adjacentNode == null) {
                continue;
            }

            Edge edge = new Edge(adjacentNode);
            node.edges.add(edge);
            adjacentNode.parents.put(node, edge);
        }
        return graph;
    }

//    void DFS(Node node, HashSet<Node> passed) {
////        print(node.value);
//        passed.add(node);
//        for(Edge edge: node.edges) {
//            if(!passed.contains(edge.adjacentNode)) {
//                DFS(edge.adjacentNode, passed);
//            }
//        }
//    }

    void DFS(Node node, HashSet<Node> passed) {
        Stack<Node> stack = new Stack<>();
        stack.push(node);

        while (stack.size() != 0) {
            node = stack.peek();

            if (!passed.contains(node)) {
//                print(node.value);
                passed.add(node);
            }

            boolean hasChildren = false;
            for (Edge edge : node.edges) {
                if (!passed.contains(edge.adjacentNode)) {
                    stack.push(edge.adjacentNode);
                    hasChildren = true;
                    break;
                }
            }
            if (!hasChildren) {
                stack.pop();
            }
        }
    }

    boolean getPath(Node start, Node end, HashSet<Node> passed, LinkedList<Node> path) {
        if (start == end) {
            path.addFirst(start);
            return true;
        }
            passed.add(start);
        for (Edge edge : start.edges) {
            if (!passed.contains(edge.adjacentNode)) {
                if (getPath(edge.adjacentNode, end, passed, path)) {
                    path.addFirst(start);
                    return true;
                }
            }
        }
        return false;
    }

    void getPathAll(Node start, Node end, HashSet<Node> passed, List<LinkedHashSet<Node>> paths) {
        if (start == end) {
            paths.add((LinkedHashSet<Node>) passed.clone());
            paths.get(paths.size() - 1).add(end);
        }
        passed.add(start);
        for (Edge edge : start.edges) {
            if (!passed.contains(edge.adjacentNode)) {
                getPathAll(edge.adjacentNode, end, passed, paths);
            }
        }
        passed.remove(start);
    }

    void BFS(Node node, HashSet<Node> visitingOrPassed) {
        LinkedList<Node> queue = new LinkedList<>();
        queue.add(node);
        visitingOrPassed.add(node);

        while (!queue.isEmpty()) {
            node = queue.removeFirst();
            for (Edge edge: node.edges) {
                if (!visitingOrPassed.contains(edge.adjacentNode)) {
                    queue.addLast(edge.adjacentNode);
                    }
                }
            }
        }

        PathNode_test findShortestPath(Node start, Node end) {
        HashSet<Node> visitingOrPassed = new HashSet<>();
        LinkedList<PathNode_test> queue = new LinkedList<>();

        queue.addLast(new PathNode_test(start, null));
        visitingOrPassed.add(start);
        while (!queue.isEmpty()) {
            PathNode_test pathNode = queue.removeFirst();

            if (pathNode.node == end) {
                return pathNode;
            }

            for (Edge edge: pathNode.node.edges) {
                if (visitingOrPassed.contains(edge.adjacentNode)) {
                    continue;
                }
                if (edge.adjacentNode == end) {
                    return new PathNode_test(edge.adjacentNode, pathNode);
                }
                queue.addLast(new PathNode_test(edge.adjacentNode, pathNode));

                visitingOrPassed.add(pathNode.node);
            }
        }
        return null;
    }

    LinkedList<Node> extractPath(PathNode_test endNode) {
        LinkedList<Node> path = new LinkedList<>();
        while (endNode != null) {
            path.addFirst(endNode.node);
            endNode = endNode.parent;
            }
        return path;
        }
    }

class Node {
    int value;
    LinkedHashSet<Edge> edges = new LinkedHashSet<>();
    LinkedHashMap<Node, Edge> parents = new LinkedHashMap<>();

    Node(int value) {
        this.value = value;
    }
}

class Edge {
    Node adjacentNode;

    Edge(Node adjacentNode) {
        this.adjacentNode = adjacentNode;
    }
}

class PathNode_test {
    public Node node;
    public PathNode_test parent;
    public PathNode_test(Node node, PathNode_test parent) {
        this.node = node;
        this.parent = parent;
    }
}