package src;

/**
 *
 * @author Onur Yurteri, Guillaume DUBOIS-MALAFOSSE
 */

import java.io.IOException;
import src.City;
import src.Relations;

public class main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        FileAdapter fAdap; //initialization of FileAdapter object
        if (args.length>0) { //if there's any filenames given as argument
            fAdap= new FileAdapter(args[0]); 
        }
        else{
            fAdap= new FileAdapter("parse.txt"); //default filename
        }
      City[] cities = fAdap.Parse(); // Fileadapter class parses file into city object array
      Relations rel = new Relations(cities); //Relations class create relations on jacop and graphstream
      rel.initCities(); //rendering of graph
    }
}
