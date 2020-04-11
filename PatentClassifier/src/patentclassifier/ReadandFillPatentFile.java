/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package patentclassifier;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Lucas
 */
public class ReadandFillPatentFile {
        
    public static List<String[]> patentReader(String file_path){
        List<String[]> patents = new LinkedList<>();
        try{
            BufferedReader br = new BufferedReader(new FileReader(file_path)); 

            while(br.ready()){ 
                String linha = br.readLine(); 
                String[] patent = linha.split("\t");
                patents.add(patent);
            } 
            br.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        return patents;
    }
    
    public static List<String> patentReader(String[] patents){
        List<String> data_line = new LinkedList<String>();
        String[] ipcs = patents[1].split(";");
        for(String ipc : ipcs){
            try{
                if(!data_line.contains(ipc.replace(" ", "").substring(0,1)+"#"+patents[3])){
                    data_line.add(ipc.replace(" ", "").substring(0,1)+"#"+patents[3]);
                }
            }catch(Exception e){
            }
        }
        return data_line;
    }
}
