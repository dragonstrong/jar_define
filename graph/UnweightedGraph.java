package graph;
import java.util.List;
import java.util.*;
/**有向无权图*/
public class UnweightedGraph<V> implements Graph<V>
{
    protected List<V> vertices = new ArrayList<>(); // 顶点列表
    protected List<List<Edge>> neighbors=new ArrayList<>(); // 与顶点相连的边列表

    /** 构造空图*/
    public UnweightedGraph() {}

    /** 根据顶点和边数组建图 */
    public UnweightedGraph(V[] vertices, int[][] edges)
    {
        for (int i = 0; i < vertices.length; i++)  //添加顶点
            addVertex(vertices[i]);

        createAdjacencyLists(edges);  //添加边
    }

    /** 根据顶点和边列表建图 */
    public UnweightedGraph(List<V> vertices, List<Edge> edges)
    {
        for (int i = 0; i < vertices.size(); i++)
            addVertex(vertices.get(i));
        createAdjacencyLists(edges);
    }


    /** 根据数组创建边 */
    public void createAdjacencyLists(int[][] edges)
    {
        for (int i = 0; i < edges.length; i++)
            addEdge(edges[i][0], edges[i][1]);
    }

    /** 根据边列表创建边 */
    public void createAdjacencyLists(List<Edge> edges)
    {
        for (Edge edge: edges)
            addEdge(edge.u, edge.v);
    }

    /** 返回顶点个数 */
    @Override
    public int getSize()
    {
        return vertices.size();
    }

    /** 返回顶点列表 */
    @Override
    public List<V> getVertices()
    {
        return vertices;
    }

    /** 返回指定下标处的顶点 */
    @Override
    public V getVertex(int index)
    {
        return vertices.get(index);
    }

    /** 获取顶点的下标 */
    @Override
    public int getIndex(V v)
    {
        return vertices.indexOf(v);
    }

    /** 返回相邻顶点列表 */
    @Override
    public List<Integer> getNeighbors(int index)
    {
        List<Integer> result = new ArrayList<>();
        for (Edge e: neighbors.get(index))
            result.add(e.v);

        return result;
    }

    /** 返回指定顶点的度*/
    @Override
    public int getDegree(int v)
    {
        return neighbors.get(v).size();
    }

    /** 打印边：每个顶点分别打印与之相连的边*/
    @Override
    public void printEdges()
    {
        for (int u = 0; u < neighbors.size(); u++)
        {
            System.out.print(getVertex(u) + " (" + u + "): ");
            for (Edge e: neighbors.get(u))
                System.out.print("(" + getVertex(e.u) + ", " + getVertex(e.v) + ") ");

            System.out.println();
        }
    }

    /**获取边的集合*/
    @Override
    public List<Edge> getEdges()
    {
        List<Edge> res=new ArrayList<>();
        for(int i=0;i<neighbors.size();i++)
        {
            for(Edge o:neighbors.get(i))
                res.add(o);
        }
        return res;
    }


    /** 清空图 */
    @Override
    public void clear()
    {
        vertices.clear();
        neighbors.clear();
    }
    /** 添加顶点  */
    @Override
    public boolean addVertex(V vertex)
    {
        if (!vertices.contains(vertex))   //不存在则加入
        {
            vertices.add(vertex);
            neighbors.add(new ArrayList<Edge>());
            return true;
        }
        else
            return false;
    }

    /** 添加边 */
    protected boolean addEdge(Edge e)
    {
        //要加的边两个顶点不在已有顶点编号范围内
        if (e.u < 0 || e.u > getSize() - 1)
            throw new IllegalArgumentException("No such index: " + e.u);

        if (e.v < 0 || e.v > getSize() - 1)
            throw new IllegalArgumentException("No such index: " + e.v);

        //查询该边是否存在
        if (!neighbors.get(e.u).contains(e))
        {
            neighbors.get(e.u).add(e);  //不存在加入  有向无权图
            return true;
        }
        else   //已存在
        {
            return false;
        }
    }

    /** 添加边 */
    @Override
    public boolean addEdge(int u, int v)
    {
        return addEdge(new Edge(u, v));
    }

    /** 内部类：Edge */
    public static class Edge
    {
        public int u; // Starting vertex of the edge
        public int v; // Ending vertex of the edge

        /** 有向边：u->v */
        public Edge(int u, int v)
        {
            this.u = u;
            this.v = v;
        }

        public boolean equals(Object o) {
            return u == ((Edge)o).u && v == ((Edge)o).v;
        }

        public String toString()
        {
            return "("+u+","+v+")";

        }
    }

    @Override /** 返回从顶点下标v开始的dfs树 */
    public Tree dfs(int v)
    {
        List<Integer> searchOrder = new ArrayList<>();
        int[] parent = new int[vertices.size()];
        for (int i = 0; i < parent.length; i++)
            parent[i] = -1; // Initialize parent[i] to -1

        // Mark visited vertices
        boolean[] isVisited = new boolean[vertices.size()];

        // Recursively search
        dfs(v, parent, searchOrder, isVisited);

        // Return a search tree
        return new Tree(v, parent, searchOrder);
    }

    /** Recursive method for DFS search */
    private void dfs(int u, int[] parent, List<Integer> searchOrder,
                     boolean[] isVisited) {
        // Store the visited vertex
        searchOrder.add(u);
        isVisited[u] = true; // Vertex v visited

        for (Edge e : neighbors.get(u)) {
            if (!isVisited[e.v]) {
                parent[e.v] = u; // The parent of vertex e.v is u
                dfs(e.v, parent, searchOrder, isVisited); // Recursive search
            }
        }
    }

    @Override /** Starting bfs search from vertex v */
    /** To be discussed in Section 28.7 */
    public Tree bfs(int v) {
        List<Integer> searchOrder = new ArrayList<>();
        int[] parent = new int[vertices.size()];
        for (int i = 0; i < parent.length; i++)
            parent[i] = -1; // Initialize parent[i] to -1

        java.util.LinkedList<Integer> queue =
                new java.util.LinkedList<>(); // list used as a queue
        boolean[] isVisited = new boolean[vertices.size()];
        queue.offer(v); // Enqueue v
        isVisited[v] = true; // Mark it visited

        while (!queue.isEmpty()) {
            int u = queue.poll(); // Dequeue to u
            searchOrder.add(u); // u searched
            for (Edge e: neighbors.get(u)) {
                if (!isVisited[e.v]) {
                    queue.offer(e.v); // Enqueue w
                    parent[e.v] = u; // The parent of w is u
                    isVisited[e.v] = true; // Mark it visited
                }
            }
        }

        return new Tree(v, parent, searchOrder);
    }

    /** 内部类Tree*/

    public class Tree
    {
        private int root; // The root of the tree
        private int[] parent; // Store the parent of each vertex
        private List<Integer> searchOrder; // Store the search order

        /** Construct a tree with root, parent, and searchOrder */
        public Tree(int root, int[] parent, List<Integer> searchOrder) {
            this.root = root;
            this.parent = parent;
            this.searchOrder = searchOrder;
        }

        /** Return the root of the tree */
        public int getRoot() {
            return root;
        }

        /** Return the parent of vertex v */
        public int getParent(int v) {
            return parent[v];
        }

        /** Return an array representing search order */
        public List<Integer> getSearchOrder() {
            return searchOrder;
        }

        /** Return number of vertices found */
        public int getNumberOfVerticesFound() {
            return searchOrder.size();
        }

        /** Return the path of vertices from a vertex to the root */
        public List<V> getPath(int index) {
            ArrayList<V> path = new ArrayList<>();

            do {
                path.add(vertices.get(index));
                index = parent[index];
            }
            while (index != -1);

            return path;
        }

        /** Print a path from the root to vertex v */
        public void printPath(int index) {
            List<V> path = getPath(index);
            System.out.print("A path from " + vertices.get(root) + " to " +
                    vertices.get(index) + ": ");
            for (int i = path.size() - 1; i >= 0; i--)
                System.out.print(path.get(i) + " ");
        }

        /** Print the whole tree */
        public void printTree() {
            System.out.println("Root is: " + vertices.get(root));
            System.out.print("Edges: ");
            for (int i = 0; i < parent.length; i++) {
                if (parent[i] != -1) {
                    // Display an edge
                    System.out.print("(" + vertices.get(parent[i]) + ", " +
                            vertices.get(i) + ") ");
                }
            }
            System.out.println();
        }
    }
}
