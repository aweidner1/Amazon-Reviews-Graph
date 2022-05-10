package FinalExam;


import java.util.*;
public class GraphType<E>
{
   HashMap<E,ArrayList<E>> graph;
   int vertexCount;
   int edgeCount;
   
   
   public GraphType() {
       
     vertexCount=0;
     edgeCount=0;
     graph = new HashMap<>();
    
       
    }
    
     public ArrayList<E> getAdjacency(E vertex) {
        
        return (graph.get(vertex));
    }
    
    public int degree(E vertex) {
        
       return getAdjacency(vertex).size(); 
        
    }
    
    public void addVertex(E vertex) {
        if (!graph.containsKey(vertex)) {
        graph.put(vertex,new ArrayList<E> ());
        vertexCount++;
    }
        
        
    }
    
    public boolean isEdge (E v1, E v2) {
       
        return (getAdjacency(v1).contains(v2));
        
        
    }
    
    public void addEdge (E v1, E v2) {
        if (graph.containsKey(v1) && graph.containsKey(v2)){
            ArrayList<E> a1 = getAdjacency(v2);
           
           a1.add(v1);
           
        
           // adds vertex v1 to the adjacency of vertex v2
           ArrayList<E> a2 = getAdjacency(v1);
          
           a2.add(v2);
           
           //adds v2 to the adjacency of v1
           edgeCount++;
        }
        else System.out.println("edge not added because one or more vertices are not found ...");
        
    }
 
}
