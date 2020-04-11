/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package patentclassifier;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.CoreDocument;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import opennlp.tools.lemmatizer.LemmatizerME;
import opennlp.tools.lemmatizer.LemmatizerModel;
import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;
import weka.core.stopwords.StopwordsHandler;

/**
 *
 * @author Lucas
 */
public class PrepareBagOfWords {
    static HashMap<String,HashMap<String,Double>> frequency_table = new HashMap<>();
    static HashMap<String,Double>total = new HashMap<String,Double>();
       public static void trainBagOfWordsModel(){
           
        InputStream modelIn = null;
        POSModel model = null;
        try{
            modelIn = new FileInputStream("C:\\Users\\Lucas\\Documents\\apache-opennlp-1.9.2\\en-pos-maxent.bin");
            model = new POSModel(modelIn);
        }catch(Exception e){
            e.printStackTrace();
        }
        
        LemmatizerModel model2 = null;
        InputStream modelIn2 = null;
        try{
            modelIn2 = new FileInputStream("C:\\Users\\Lucas\\Documents\\apache-opennlp-1.9.2\\bin\\en-lemmatizer.bin");
            model2 = new LemmatizerModel(modelIn2);
        }catch(Exception e){
            e.printStackTrace();
        }
        
           StopWords.MyStopWordsHandler();
           String path="C:\\Users\\Lucas\\Desktop\\patents_extraction\\bagOfWordsClassifier\\ClassifierTest2";
            try{
                BufferedReader br = new BufferedReader(new FileReader(path)); 
                int cont=0;
                while(br.ready()){

                    try{
                        String linha = br.readLine(); 
                        linha = linha.toLowerCase();
                        String[] splited = linha.split(",\"");
                        if(!frequency_table.containsKey(splited[0])){
                            frequency_table.put(splited[0], new HashMap<String,Double>());
                        }
                        HashMap<String,Double> words = frequency_table.get(splited[0]);
                        String[]to_add = NLPparser.getNounsInPhrase(splited[1].replace(",", " ").replace("\""," ").replace(";"," "),model);
                        to_add = NLPparser.getLemaWord(to_add, model2);
                        for(int j=0;j<to_add.length;j++){
                            String actual = to_add[j];
                            actual = actual.replace(" ", "");
                            boolean adicionou=false;
                            if(!StopWords.isStopword(actual) && actual.length()>2){
                                if(words.get(actual)!=null){
                                    words.put(actual, words.get(actual).doubleValue()+1);
                                }else{
                                    words.put(actual,1.0);
                                }
                            }
                         
                        }
                        System.out.println(cont);
                        cont++;
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }
                
                
//                for(String word: frequency_table.keySet()){
//                    HashMap<String,Double> words = frequency_table.get(word);
//                    
//                    for(String wor : words.keySet()){
//                        if(total.get(word)!=null){
//                            total.put(word, total.get(word).doubleValue()+words.get(wor).doubleValue());
//                        }else{
//                            total.put(word, 1.0);
//                        }
//                    }
//                }
////                for(String word: total.keySet()){
////                    System.out.println(word+" freq: "+total.get(word));
////                }
//                
//                for(String word: frequency_table.keySet()){
//                    HashMap<String,Double> words = frequency_table.get(word);
//                    
//                    for(String wor : words.keySet()){
//                        if(words.get(wor)!=null){
//                            words.put(wor, ((double)words.get(wor).doubleValue()/total.get(word)));
//                        }
//                    }
//                }
           }catch(Exception e){
               e.printStackTrace();
           }
       }
       
       public static void readTrainedModel(String path){
           try{
                BufferedReader br = new BufferedReader(new FileReader(path)); 

                while(br.ready()){ 
                    String linha = br.readLine(); 
                    String[] patent = linha.split("\t");
                    
                    if(frequency_table.get(patent[0].replace("\"",""))==null){
                        HashMap<String,Double> words = new HashMap<String,Double>();
                        words.put(patent[1], Double.valueOf(patent[2]));
                        frequency_table.put(patent[0].replace("\"",""),words);
                    }
                    else{
                        frequency_table.get(patent[0].replace("\"","")).put(patent[1], Double.valueOf(patent[2]));
                    }
                } 
                br.close();
            }catch(Exception e){
                e.printStackTrace();
            }
       }
}
class Word{
    String word;
    int num;

    public Word(String word) {
        this.word = word;
        this.num = 1;
    }
    
}
class StopWords{

    static BufferedReader bufferedReader ;
    static HashSet<String> myStopWords;
    static String text;
    public static void MyStopWordsHandler() {
        String filename="C:\\Users\\Lucas\\Desktop\\patents_extraction\\_stopwords.txt";
        // TODO Auto-generated constructor stub
        myStopWords = new HashSet<String>();
        try{
            BufferedReader br = new BufferedReader(new FileReader(filename)); 
                int cont=0;
                while(br.ready()){
                String linha = br.readLine();
                myStopWords.add(linha.replace("","").replace("\n", ""));
            }
            br.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        //myStopWords.add("the");
        //myStopWords.add("there");
    }

    public static boolean isStopword(String word) {
        // TODO Auto-generated method stub
        
        return myStopWords.contains(word);
    }
    
}

class NLPparser{
    public static String[] getGramaticalClass(String[]sent){
         InputStream modelIn = null;
        try{
            modelIn = new FileInputStream("C:\\Users\\Lucas\\Documents\\apache-opennlp-1.9.2\\en-pos-maxent.bin");
        }catch(Exception e){
            e.printStackTrace();
        }

            try {
                POSModel model = new POSModel(modelIn);
                POSTaggerME tagger = new POSTaggerME(model);
                String tags[] = tagger.tag(sent);
                return tags;
            }catch(Exception e){
                e.printStackTrace();
            }
            return null;
    }
    
    public static String[] getLemaWord(String[]words, LemmatizerModel model){
        try {
            LemmatizerME lemmatizer = new LemmatizerME(model);
          String[] lemmas = lemmatizer.lemmatize(words, getGramaticalClass(words));
          return lemmas;
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
    
    public static String[] getNounsInPhrase(String text,POSModel model){
        LinkedList<String> only_nouns = new LinkedList<String>();
            try {
                
                POSTaggerME tagger = new POSTaggerME(model);
                String sent[] = text.split(" ");		  
                String tags[] = tagger.tag(sent);
                int cont =0;
//                for(String sentt : sent){
//                    System.out.print(sentt+" ");
//                }
//                System.out.println("");
                for(String tag: tags){
//                    System.out.print(tag+" ");
                    if(tag.equals("NN") || tag.equals("JJ") || tag.equals("NNS")){
                        only_nouns.add(sent[cont]);
                    }
                    cont++;
                }
            }catch(Exception e){
                e.printStackTrace();
            }
//            System.out.println("");
            Object[] a = only_nouns.toArray();
            String[] a1 = new String[a.length];
            int cont=0;
            for(Object ob : a){
                a1[cont]=(String)ob;
                cont++;
            }
//            System.out.println(a1.length);
            return a1;
    }
}
