/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package patentclassifier;

import java.util.Comparator;
import java.util.LinkedList;

/**
 *
 * @author Lucas
 */
class field_freq2 implements Comparable{
    String field;
    double freq;

    public field_freq2(String field, double freq) {
        this.field = field;
        this.freq = freq;
    }
    
    
    @Override
    public int compareTo(Object o) {
        if(((field_freq2)o).freq<=this.freq){
            return -1;
        }else{
            return 1;
        }
    }
    
}

class SortingComparator implements Comparator<field_freq2> { 
  
    @Override
    public int compare(field_freq2 o, field_freq2 b) { 

        if(((field_freq2)o).freq<=b.freq){
            return 1;
        }else{
            return -1;
        }
    } 
}

public class order_to_pareto {
    public static void main(String[]args){
        LinkedList<field_freq2>lines = new LinkedList<field_freq2>();
        String[] fields = {"General Science & Technology","Information & Communication Technologies","Engineering","Physics & Astronomy","Agriculture"," Fisheries & Forestry","Economics & Business ","Clinical Medicine","Enabling & Strategic Technologies","Earth & Environmental Sciences","Biology","Chemistry","Public Health & Health Services","Biomedical Research","Social Sciences","Built Environment & Design"};
        double[]nums={1,2,3,4,10,10,2,4,6,2,5,4,2,2,1,1};
        int sum=0;
        
        for(int i=0;i<nums.length;i++){
            if(i!=5)
            sum+=nums[i];
        }
        
        for(int i=0;i<nums.length;i++){
            if(i!=5)
            lines.add(new field_freq2(fields[i], ((double)nums[i]/sum)*100));
        }
        lines.sort(new SortingComparator());
        for(field_freq2 line:lines){
            System.out.print(line.field+"\t");
        }
        System.out.println("");
        for(field_freq2 line:lines){
            System.out.print(line.freq+"\t");
        }
        
    }
}





