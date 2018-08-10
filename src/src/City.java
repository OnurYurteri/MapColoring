/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src;
import java.util.ArrayList;
import java.util.List;
import org.jacop.core.IntVar;
/**
 *
 * @author Onur Yurteri, Guillaume DUBOIS-MALAFOSSE
 */
public class City {
    public int id;
    public String name;
    public ArrayList<Integer> neb = new ArrayList<Integer>();//Neighbour Cities 
    
    public City(int _id, String _name){
        id=_id;
        name=_name;
    }
}
