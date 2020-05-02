package KnowBase;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.LinkedList;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import org.apache.commons.io.IOUtils;
import org.apache.jena.atlas.json.JsonArray;
import org.apache.jena.atlas.json.JsonObject;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Carlos
 */
public class KnowledgeBaseClassifier {
    
    public static LinkedList<String> TagEntitiesInDescription(String description, double confidence){
        TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager(){
        public X509Certificate[] getAcceptedIssuers(){return null;}
        public void checkClientTrusted(X509Certificate[] certs, String authType){}
        public void checkServerTrusted(X509Certificate[] certs, String authType){}
        }};
        StringBuilder result = new StringBuilder();
        try{
            URL url = new URL("https://api.dbpedia-spotlight.org/en/annotate?text="+URLEncoder.encode(description, StandardCharsets.UTF_8.toString())+"&confidence="+confidence+"&format=json");
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, trustAllCerts, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = rd.readLine()) != null) {
               result.append(line);
            }
            rd.close();
            }
        catch(Exception e){
            e.printStackTrace();
        }
        
      String plain_tag= result.toString();
      LinkedList<String>tags = new LinkedList<String>();
      for(int i=0;i<plain_tag.length()-4;i++){
          if(plain_tag.charAt(i) == 'h'&& plain_tag.charAt(i+1) == 'r' && plain_tag.charAt(i+2) == 'e'
                  && plain_tag.charAt(i+3) == 'f'){
              int j=i+6;
              for(;plain_tag.charAt(j)!='"';j++){};
              String tag = plain_tag.substring(i+6,j);
              tags.add(tag);
          }
      }
      return tags;
    }
    
    public static LinkedList<String> queryInDBPEDIA(String endpoint, String query) {
        StringBuilder result = new StringBuilder();
        try{
            endpoint = endpoint+"?query=" + URLEncoder.encode(query, StandardCharsets.UTF_8.toString())+"";
            URL url = new URL(endpoint);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
//            conn.setRequestProperty("Accept", "application/sparql-results+json");
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = rd.readLine()) != null) {
               result.append(line);
            }
            rd.close();
            }
        catch(Exception e){
            e.printStackTrace();
        }
        String plain_tag= result.toString();
      LinkedList<String>tags = new LinkedList<String>();
      for(int i=0;i<plain_tag.length()-4;i++){
          if(plain_tag.charAt(i) == 'h'&& plain_tag.charAt(i+1) == 'r' && plain_tag.charAt(i+2) == 'e'
                  && plain_tag.charAt(i+3) == 'f'){
              int j=i+6;
              for(;plain_tag.charAt(j)!='"';j++){};
              String tag = plain_tag.substring(i+6,j);
              tags.add(tag);
          }
      }
      return tags;
    }
    
}
