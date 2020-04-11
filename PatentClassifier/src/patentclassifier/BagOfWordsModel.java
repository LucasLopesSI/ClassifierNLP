/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package patentclassifier;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import weka.core.Instances;
import weka.core.stemmers.SnowballStemmer;
import weka.core.stopwords.StopwordsHandler;
import weka.filters.Filter;
import weka.filters.MultiFilter;
import weka.filters.unsupervised.attribute.StringToWordVector;

/**
 *
 * @author Lucas
 */
public class BagOfWordsModel {
    
    public static HashMap<String,LinkedList<String>> patentReader(String file_path){
        HashMap<String,LinkedList<String>> patents = new HashMap<String,LinkedList<String>>();
        try{
            BufferedReader br = new BufferedReader(new FileReader(file_path)); 

            while(br.ready()){
                String linha = br.readLine(); 
                String[] patent = linha.split("\t");
                if(patents.get(patent[1])==null){
                    patents.put(patent[1],new LinkedList<String>());
                    
                }
                
                if(!patents.get(patent[1]).contains(patent[0].toLowerCase().replace("\""," ").replace(",", " ")))
                        patents.get(patent[1]).add(patent[0].toLowerCase().replace("\""," ").replace(",", " "));
            }
            
            FileWriter arq = new FileWriter("./src/patentclassifier/rawCitationClass.arff");
            PrintWriter gravarArq = new PrintWriter(arq);
 
            gravarArq.print("% 1. Title: Citation Classification\n" +
            "   % \n" +
            "   % 2. Sources:\n" +
            "   %      (a) Creator: Lucas Lopes Resende\n" +
            "   %      (b) Donor: USPTO\n" +
            "   %      (c) Date: April, 2020\n" +
            "   % \n" +
            "   @RELATION Patent\n" +
            "");
            gravarArq.print("@ATTRIBUTE citation {");
            for(String key: patents.keySet()){
                LinkedList<String> citations = patents.get(key);
                for(String citation : citations){
                    gravarArq.print("\""+citation+"\",");
                }
            }
            gravarArq.print("}\n");
            gravarArq.print("@ATTRIBUTE class {");
            for(String key: patents.keySet()){
                    gravarArq.print("\""+key+"\",");
            }
            gravarArq.print("\n@DATA\n");
            for(String key: patents.keySet()){
                LinkedList<String> citations = patents.get(key);
                for(String citation : citations){
                    gravarArq.print("\""+citation+"\",\""+key+"\"\n");
                }
            }
            gravarArq.close();
            br.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        return patents;
    }
    
    public static Filter makeBagOfWords(Instances trainingInstancesSet){
        StringToWordVector currentFilter = new StringToWordVector();
        currentFilter.setStopwordsHandler(new MyStopWordsHandler("C:\\Users\\Lucas\\Desktop\\patents_extraction\\_stopwords.txt"));
        currentFilter.setAttributeIndices("first");
        currentFilter.setStopwordsHandler(new RegExStopwords("([0-9]|@|n\\/a|[\\%\\€\\$\\£])"));
        SnowballStemmer stemmer = new SnowballStemmer();
        stemmer.setStemmer("English");
        currentFilter.setStemmer(stemmer);
        currentFilter.setAttributeNamePrefix("current_");
        currentFilter.setOutputWordCounts(false);
        StringToWordVector previousFilter = new StringToWordVector();
        previousFilter.setAttributeIndices("first-1");
        previousFilter.setAttributeNamePrefix("previous_");
        previousFilter.setOutputWordCounts(false);
        StringToWordVector followingFilter = new StringToWordVector();
        followingFilter.setAttributeIndices("first-2");
        followingFilter.setAttributeNamePrefix("following_");
        followingFilter.setOutputWordCounts(false);
        MultiFilter multiFilter = new MultiFilter();
        try{
        multiFilter.setInputFormat(trainingInstancesSet);
        }catch(Exception e){
            e.printStackTrace();
        }
        multiFilter.setFilters(new Filter[] { currentFilter, previousFilter, followingFilter });
        return multiFilter;
    }
}
class Field{
    String field_name;

    public Field(String field_name) {
        this.field_name = field_name;
    }
}
class MyStopWordsHandler implements StopwordsHandler{

    BufferedReader bufferedReader ;
    HashSet<String> myStopWords;
    String text;
    public MyStopWordsHandler(String filename) {
        // TODO Auto-generated constructor stub
        myStopWords = new HashSet<String>();
        try{
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            String line;
            text = "";
            while((line=reader.readLine())!=null){
                myStopWords.add(line);
            }
            reader.close();
            System.out.println(myStopWords.size());
        }catch(Exception e){
            e.printStackTrace();
        }

        //myStopWords.add("the");
        //myStopWords.add("there");
    }

    public boolean isStopword(String word) {
        // TODO Auto-generated method stub
        return myStopWords.contains(word);
    }

}

class RegExStopwords implements StopwordsHandler {

    private final Pattern pattern;

    public RegExStopwords(String regexString) {
        pattern = Pattern.compile(regexString);
    }

    @Override
    public boolean isStopword(String s) {
        Matcher matcher = pattern.matcher(s);
        return matcher.find();
    }
}