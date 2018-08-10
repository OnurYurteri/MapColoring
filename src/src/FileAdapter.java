/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import src.City;
/**
 *
 * @author Onur Yurteri, Guillaume DUBOIS-MALAFOSSE
 */
public class FileAdapter {
    public String path;
    City[] cities;
    
    public FileAdapter(String _path){
        this.path=_path;
    }
    
    public City[] Parse() throws IOException{
        cities= new City[countLines(this.path)+1]; // initialization of cities with given files line count
        try (BufferedReader br = new BufferedReader(new FileReader(this.path))) {
        String line;
            while ((line = br.readLine()) != null) {
            String[] data = line.split(" "); //splits the line with space elem[0]=>ID, elem[1]=>CityName, elem[2]=>NeighbourCityIds
            City currCity= new City(Integer.parseInt(data[0]),data[1]); // initialization of City object
            String[] rel= data[2].split("-"); //splits the string with dash to isolate the id of neighbours
                for (int i = 0; i < rel.length; i++) {
                    currCity.neb.add(Integer.parseInt(rel[i]));//Adds neighbour id to current city's relation arraylist
                }
            cities[Integer.parseInt(data[0])]=currCity;//adding current city to cities array
            }
        }
        catch(FileNotFoundException e){
            System.out.println("File not found on given directory: "+this.path);
            System.out.println(e.getMessage());
        }
        return cities;
    }
    
    public int countLines(String filename) throws IOException {
        boolean success=false;
        InputStream is=null;
        try {
            is = new BufferedInputStream(new FileInputStream(filename));
            success=true;
            byte[] c = new byte[1024];
            int readChars = is.read(c);
            if (readChars == -1) {
            // bail out if nothing to read
                return 0;
            }
        // make it easy for the optimizer to tune this loop
            int count = 0;
            while (readChars == 1024) {
                for (int i=0; i<1024;) {
                    if (c[i++] == '\n') {
                        ++count;
                    }
                }
                readChars = is.read(c);
            }

        // count remaining characters
            while (readChars != -1) {
                System.out.println(readChars);
                for (int i=0; i<readChars; ++i) {
                    if (c[i] == '\n') {
                        ++count;
                    }
                }
                readChars = is.read(c);
            }
            return count == 0 ? 1 : count;
        }
        catch(FileNotFoundException e){
            System.out.print("File not found on given directory: ");
            System.out.println(e.getMessage());
            System.exit(0);
            return -1;
        }
        finally {
            if (success) {
                is.close();
            }
        }
    }
}
