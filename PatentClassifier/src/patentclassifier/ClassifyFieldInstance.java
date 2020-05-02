/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package patentclassifier;

import java.util.HashMap;
import java.util.LinkedList;

/**
 *
 * @author Lucas
 */
public class ClassifyFieldInstance {
    public static String getField(String citation){
        System.out.println("");
        System.out.println(citation);
        citation = citation.toLowerCase();
        double max=0;
        double sec_max=0;
        String best="";
        String second="";
        String[] palavra_citacao = citation.split(" ");
        new NLPparser();
        palavra_citacao = NLPparser.getLemaWord(palavra_citacao);
        LinkedList<String> palavra_citacao2 = new LinkedList<String>();
        for(String palavra:palavra_citacao){
            if(!palavra_citacao2.contains(palavra))
                palavra_citacao2.add(palavra);
        }
        double local_count=0;
        LinkedList<Double>sum_weigths = new LinkedList<Double>();
        for(String field:PrepareBagOfWords.frequency_table.keySet()){
            local_count=0;
            for(String palavra:palavra_citacao2){
                HashMap<String,Double> a = PrepareBagOfWords.frequency_table.get(field);
                double peso=0;
                for(String field1:PrepareBagOfWords.frequency_table.keySet()){
                    
                    HashMap<String,Double> a1 = PrepareBagOfWords.frequency_table.get(field1);
                    if(a1.get(palavra)!=null){
                        peso+= a1.get(palavra);
                    }
                }
                
                if(a.get(palavra)!=null){
                    if(peso!=0){
                            local_count+= (double)a.get(palavra)/Math.pow(peso,1);
                    }
                    else{
                        local_count+= (double)a.get(palavra);
                    }
                }
            }
            sum_weigths.add(local_count);
            if(local_count>max){
                sec_max = max;
                max=local_count;
                second=best;
                best=field;
            }
        }
        double sum_weigth = 0;
        for(int i=0;i<sum_weigths.size();i++){
            sum_weigth+= sum_weigths.get(i);
        }
        max=max/sum_weigth;
        sec_max=sec_max/sum_weigth;
        System.out.println("Confidence best \t"+max+" Confidence second\t"+sec_max );
        return best+"\t"+second;
    }
}
