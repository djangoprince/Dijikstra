
import edu.princeton.cs.algs4.StdDraw;
import java.awt.Font;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.EdgeWeightedDigraph;
import edu.princeton.cs.algs4.IndexMinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.DirectedEdge;
import edu.princeton.cs.algs4.*;
//import java.util.*;
import java.util.Iterator;

public class DijkstraSPModified {
  private static double[] distTo;
  private DirectedEdge[] edgeTo;
  private IndexMinPQ<Double> pq;
  //newnew
  static DirectedEdge de ; // to get the first neighbor of s
  static DirectedEdge ee;
  static int x ;

  public DijkstraSPModified(EdgeWeightedDigraph G, int s) {
      for (DirectedEdge e : G.edges()) {
          if (e.weight() < 0)
              throw new IllegalArgumentException("edge " + e + " has negative weight");
      }
      //initialise
      distTo = new double[G.V()];
      edgeTo = new DirectedEdge[G.V()];

      validateVertex(s);
      //initialise weights
      for (int v = 0; v < G.V(); v++)
          distTo[v] = Double.POSITIVE_INFINITY;
      distTo[s] = 0.0;


      // relax vertices in order of distance from s
      pq = new IndexMinPQ<Double>(G.V());
      pq.insert(s, distTo[s]);
      while (!pq.isEmpty()) {

          int v = pq.delMin();

          for (DirectedEdge e : G.adj(v))
              relax(e);

      }

      // check optimality conditions
      assert check(G, s);
  }


  private void relax(DirectedEdge e) {
     int v = e.from(), w = e.to();
     if ((distTo[w] > distTo[v] + e.weight())) {
         distTo[w] = distTo[v] + e.weight();
         edgeTo[w] = e;
         if (pq.contains(w)) pq.decreaseKey(w, distTo[w]);
         else                pq.insert(w, distTo[w]);
     }
 }
//returns length of shortest dist from s to t
    public static double distTo(int v) {
        validateVertex(v);
        return distTo[v];
    }

    public boolean hasPathTo(int v) {
          validateVertex(v);
          return distTo[v] < Double.POSITIVE_INFINITY;
      }
      //returns shortest path from s to t
      public Iterable<DirectedEdge> pathTo(int v) {

        //verify validity
         validateVertex(v);
         if (!hasPathTo(v)) return null;

        Stack<DirectedEdge> path = new Stack<DirectedEdge>();

         for (DirectedEdge e = edgeTo[v]; e != null; e = edgeTo[e.from()]) {

             path.push(e);
         }
         return path;
     }

     private boolean check(EdgeWeightedDigraph G, int s) {

    // check that edge weights are nonnegative
    for (DirectedEdge e : G.edges()) {
        if (e.weight() < 0) {
            System.err.println("negative edge weight detected");
            return false;
        }
    }

    // check that distTo[v] and edgeTo[v] are consistent
    if (distTo[s] != 0.0 || edgeTo[s] != null) {
        System.err.println("distTo[s] and edgeTo[s] inconsistent");
        return false;
    }
    for (int v = 0; v < G.V(); v++) {
        if (v == s) continue;
        if (edgeTo[v] == null && distTo[v] != Double.POSITIVE_INFINITY) {
            System.err.println("distTo[] and edgeTo[] inconsistent");
            return false;
        }
    }

    for (int v = 0; v < G.V(); v++) {
        for (DirectedEdge e : G.adj(v)) {
            int w = e.to();
            if (distTo[v] + e.weight() < distTo[w]) {
                System.err.println("edge " + e + " not relaxed");
                return false;
            }
        }
    }

    for (int w = 0; w < G.V(); w++) {
        if (edgeTo[w] == null) continue;
        DirectedEdge e = edgeTo[w];
        int v = e.from();
        if (w != e.to()) return false;
        if (distTo[v] + e.weight() != distTo[w]) {
            System.err.println("edge " + e + " on shortest path not tight");
            return false;
        }
    }
    return true;
}

    private static void validateVertex(int v) {
    int V = distTo.length;
    if (v < 0 || v >= V)
        throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V-1));
}

public static void main(String[] args) {



  In in = new In(args[0]);
  EdgeWeightedDigraph G = new EdgeWeightedDigraph(in);
  int s = Integer.parseInt(args[1]);


  DijkstraSPModified  sp = new DijkstraSPModified(G, s);
    for (int t = 0; t < G.V(); t++) {
            if (sp.hasPathTo(t)) {
                StdOut.println("lets look for this edge     ");
                StdOut.printf("%d to %d (%.2f)  ", s, t, sp.distTo(t));

                for (DirectedEdge e : sp.pathTo(t)) {
                  //de is the normal first neighbor that he will find
                    de = e ;
                    StdOut.println("the normal first one is     ");
                    StdOut.println(de);
                    for (DirectedEdge ee : G.adj(s))
                    //if ee is same as de then do nothing
                    {if(ee==de){ }
                    else { //continue to find a different neighbor and break
                      StdOut.println("the different one is ");
                      StdOut.println(ee);
                      StdOut.println("the tail is  ");
                      //x is the tail of the edge that we will test
                      x=ee.to();
                      StdOut.println(x);
                    break;}
                  }
                    break;
                }


                StdOut.println();
              //  if ((sp.hasPathTo(ee.to()))&&(distTo(ee.to())==(distTo(de.to()))))
                //{
                //print the shortest different path here 
                  for (DirectedEdge e : sp.pathTo(x)) {
                    StdOut.print(e + "   ");


                //}
              }
            }
            else {
                StdOut.printf("%d to %d         no path\n", s, t);
            }
        }


}

}
