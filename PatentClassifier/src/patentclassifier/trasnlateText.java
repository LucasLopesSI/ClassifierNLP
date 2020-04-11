/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package patentclassifier;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 *
 * @author Lucas
 */
public class trasnlateText {
    
    public static String translateToEnglish(String text) throws IOException {
         String endpoint = "https://www.deepl.com/translator";
         System.setProperty("webdriver.chrome.driver", "C:\\Users\\Lucas\\Documents\\NetBeansProjects\\JavaApplication1\\chromedriver80.exe");
        WebDriver driver = new ChromeDriver();
         String traducao="";
        driver.get(endpoint);
         System.out.println(driver.getTitle());
        try{
            driver.findElement(By.xpath("/html/body/div[2]/div[1]/div[1]/div[3]/div[2]/div[1]/textarea")).sendKeys(text);
            traducao = driver.findElement(By.xpath("/html/body/div[2]/div[1]/div[1]/div[4]/div[3]/div[1]/textarea")).getText();
        }catch(Exception e){
            e.printStackTrace();
        }
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
}
