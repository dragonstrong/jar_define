package graph;

/**图的通用接口*/
import java.util.List;

public interface Graph<V>
{
    /**获取顶点个数*/
    public int getSize();
    /**获取所有顶点*/
    public List<V> getVertices();

    /**获取所有的边*/
    public List<UnweightedGraph.Edge> getEdges();

    /**打印所有的边*/
    public void printEdges();

    /**返回特定下标处的顶点*/
    public V getVertex(int index);
    /**返回顶点的编号*/
    public int getIndex(V v);

    /**返回某个顶点的邻居*/
    public List<Integer> getNeighbors(int index);

    /**返回顶点的度*/
    public int getDegree(int index);

    /**清除图*/
    public void clear();
    /**添加顶点*/
    public boolean addVertex(V v);

    /**添加边*/
    public boolean addEdge(int  u,int v);

    /**返回dfs树*/
    public UnweightedGraph<V>.Tree dfs(int v);

    /**返回bfs树*/
    public UnweightedGraph<V>.Tree bfs(int v);



}