package PatentReader;

import java.io.File;
import java.util.LinkedList;
import java.util.Scanner;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Carlos
 */
public class dataPatentReader {
    
    public static LinkedList<String> getAllPatentsFilesPaths(){
        LinkedList<String>files = new LinkedList<String>();
        File folder = new File("C:\\Users\\Carlos\\Desktop\\github\\lens_patents");
        File[]files_or_folders = folder.listFiles();
        for(File a: files_or_folders){
            if(a.isDirectory()){
                File[]a1 = a.listFiles();
                for(File a2 : a1){
                    if(a2.getAbsolutePath().endsWith("csv"))
                       files.add(a2.getAbsolutePath());
                }
            }else{
                if(a.getAbsolutePath().endsWith("csv"))
                   files.add(a.getAbsolutePath());
            }
        }
        return files;
    }
    
    public static String readLensPatentCSV(String path){
        LinkedList<LinkedList<String>> patents_data = new LinkedList<LinkedList<String>>();
        String descriptions = "";
        try{
            Scanner readFile = new Scanner(new File(path));
            
            int cont = 0;
            while(readFile.hasNextLine()){
                String line = readFile.nextLine();
                String[]columns_title = null;
                try{
                    if(cont!=0){
                        if(!line.contains("\"")){
                            String[]dados = line.split(",");
                            descriptions+=dados[11].replace("\"","")+"\n";
                        }else{
                            int num1=0;
                            int num2=0;
                            for(int i=0;i<line.length();i++){
                                if(line.charAt(i)=='"'){
                                    if(num1==0){
                                        num1=i;
                                    }else{
                                        num2=i;
                                    }
                                }
                            }
                            descriptions+=line.substring(num1, num2).replace("\"","")+"\n";
                        }
                    }else{
                        columns_title = line.split(",");
                    }
                }catch(Exception e){
                    e.printStackTrace();
                }
                cont++;
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return descriptions ;
    }
    
}
