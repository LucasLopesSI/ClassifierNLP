/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package patentclassifier;

import com.google.common.io.Files;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 *
 * @author Lucas
 */
public class trasnlateText {
    
    public static String translateToEnglish(String text, String path){
         String endpoint = "https://translate.google.com.br/?hl=pt-BR#view=home&op=translate&sl=pt&tl=en";
         System.setProperty("webdriver.chrome.driver", PatentClassifier.chromeDriver);
        WebDriver driver = new ChromeDriver();
         String traducao="";
        driver.get(endpoint);
         System.out.println(driver.getTitle());
        try{
            File save_result = new File(path.replace(".csv","")+"_english.txt");
            save_result.createNewFile();
            String[]texts = text.split("\n");
            PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(save_result.getAbsolutePath(), true)));
 
            for(String line : texts){
                try{
                    driver.findElement(By.xpath("/html/body/div[2]/div[2]/div[1]/div[2]/div[1]/div[1]/div[1]/div[2]/div/div/div[1]/textarea")).sendKeys("# "+line+" #");
                    Thread.sleep(2000);
                    String pagetxt = driver.findElement(By.xpath("/html/body")).getText();
                    driver.findElement(By.xpath("/html/body/div[2]/div[2]/div[1]/div[2]/div[1]/div[1]/div[1]/div[2]/div/div/div[1]/textarea")).clear();
                    java.nio.file.Files.write(Paths.get(save_result.getAbsolutePath()), (line+"#"+pagetxt.split("#")[1]+"\n").getBytes(), StandardOpenOption.APPEND);
                }catch(Exception e){
                    java.nio.file.Files.write(Paths.get(save_result.getAbsolutePath()), (line+"#"+line+"\n").getBytes(), StandardOpenOption.APPEND);
                    e.printStackTrace();
                }
            }
            out.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        driver.close();
        System.out.println(traducao);
        return traducao;
    }
    
    static String endpoint = "https://script.google.com/macros/s/AKfycbyKshj81xDMyri8AWDGKFzsEeukg_A8EEgPNOb4Ko6An27y2CDG/exec";
     public static String translate(String langFrom, String langTo, String text) throws IOException {
        String urlStr = endpoint +
                "?q=" + URLEncoder.encode(text, "UTF-8") +
                "&target=" + langTo +
                "&source=" + langFrom;
         System.out.println(urlStr);
        URL url = new URL(urlStr);
        StringBuilder response = new StringBuilder();
         HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestProperty("User-Agent", "Mozilla/5.0");
         BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        return response.toString();
    }
     
    public static void printWords(){
        HashMap<String,HashMap<String,Double>> Pt_frequency_table = new HashMap<>();
        
        for(String field:PrepareBagOfWords.frequency_table.keySet()){
              System.out.println(field+"#################");
              HashMap<String,Double> a = PrepareBagOfWords.frequency_table.get(field);
              Pt_frequency_table.put(field, new HashMap<String,Double>());
              for(String word:a.keySet()){
                    System.out.println(word);
              }
        }
    }
}
