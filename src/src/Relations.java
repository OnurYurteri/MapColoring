/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src;
import src.City;
import org.jacop.core.*; //Jacop imports
import org.jacop.constraints.*;
import org.jacop.search.*;
import org.jacop.set.core.*;
import org.jacop.set.constraints.*;
import org.jacop.set.search.*;
import org.graphstream.graph.*; //GraphStream imports for graphic visualization
import org.graphstream.graph.implementations.*;
import org.graphstream.ui.j2dviewer.*;
/**
 *
 * @author Onur Yurteri, Guillaume DUBOIS-MALAFOSSE"
 */
public class Relations {
    City[] cities;
    
    public Relations(City[] _cities){
    cities=_cities;
    }
    
    public void initCities(){
        //Initialization of necessary jacop variables and constraints
        
        Store store = new Store();
        IntVar[] x = new IntVar[cities.length];
        for (int i = 0; i < cities.length; i++) {
            x[i] = new IntVar(store, String.valueOf(i), 1, cities.length); 
        }
        for (int i = 0; i < cities.length; i++) {
            for (int j = 0; j < cities[i].neb.size(); j++) {
                store.impose( new XneqY(x[i], x[cities[i].neb.get(j)]) ); 
            }
        }
        Search<IntVar> label = new DepthFirstSearch<IntVar>();
        SelectChoicePoint<IntVar> select = 
        new InputOrderSelect<IntVar>(store, x, 
                                        new IndomainMin<IntVar>()); 
        boolean result = label.labeling(store, select); 
        if ( result ) 
        {
            for (int i = 0; i < cities.length; i++) {
                System.out.println(x[i]); //.value() for only color values
            }
        }
        else 
            System.out.println("*** No");
        
        System.setProperty("org.graphstream.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer"); //For using css on renderer
        Graph graph = new SingleGraph("Map Coloring"); 
        for (int i = 0; i < cities.length; i++) {
            graph.addNode(cities[i].name); //Adds city node
            graph.getNode(cities[i].name).addAttribute("ui.label", cities[i].name); //For visual improvements
        }
        for (int i = 0; i < cities.length; i++) {
            if(cities[i].neb.size()!=0){ // if city has neighbours
                for (int j = 0; j < cities[i].neb.size(); j++) { //iterate through neighbour cities
                    if (graph.getEdge(cities[i].neb.get(j)+"-"+i) == null) { //Checks if relation done before
                        graph.addEdge(i+"-"+cities[i].neb.get(j),cities[i].name,cities[cities[i].neb.get(j)].name); //adding relation as edge 
                    }
                }
            }
        }
        String[] colors= new String[5]; //Colors
        colors[0]="black";
        colors[1]="red";
        colors[2]="blue";
        colors[3]="green";
        colors[4]="orange";
        
        //For visual improvements 
        String cssClass="node { size: 30px; text-mode: normal; text-alignment:above; text-visibility-mode:normal; text-background-mode:rounded-box; text-background-color:gray; text-padding:5px; text-size:15px; }\n"; //
        for (int i = 0; i < cities.length; i++) {
            cssClass+="node#"+cities[i].name+" { fill-color: "+colors[x[i].value()]+"; }\n"; //defines color to node from information gathered by jacop
        }
        System.out.println(cssClass);
        graph.addAttribute("ui.stylesheet", cssClass);//adds stylesheet to renderer
        graph.display();//displays
    }
}

