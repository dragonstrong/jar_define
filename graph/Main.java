import java.util.*;
import graph.UnweightedGraph;
public class Main
{
    public static void main(String[] args)
    {
        Scanner input=new Scanner(System.in);


        String[] vertices={"NanChang","Beijing","ShangHai","YiChun","GuangZhou","ShenZhen"};
        int[][] edges={{0,1},{3,1},{1,3},{2,4},{3,5},{3,4},{3,2}};
        UnweightedGraph<String> graph=new UnweightedGraph<>(vertices,edges);

        /**获取顶点集合*/
        List<String> ver=graph.getVertices();
        System.out.println("顶点集合：");
        for(String o:ver)
            System.out.print(o+" ");

        /**获取边集合*/
        List<UnweightedGraph.Edge> res=graph.getEdges();  //获取边的集合
        System.out.println("\n边的集合,以顶点下标的形式：");
        for(UnweightedGraph.Edge e:res)
            System.out.print(e.toString()+" ");

        /**打印边*/
        System.out.println("\n打印所有的边，以每个顶点临边的形式：");
        graph.printEdges();

    }
}

