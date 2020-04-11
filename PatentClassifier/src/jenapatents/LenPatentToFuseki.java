/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jenapatents;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.ResourceFactory;

/**
 *
 * @author Lucas
 */
public class LenPatentToFuseki {
    public static void main(String[]args) throws Exception{
        String patents = "http://us.patents.aksw.org/ontology/",
                rdf = "http://www.w3.org/1999/02/22-rdf-syntax-ns#",
                rdfs="http://www.w3.org/2000/01/rdf-schema#";
        String[] paths={"C:\\Users\\Lucas\\Desktop\\patents_extraction\\lens_data\\lens_b\\lens_b24_b25_b26_b27_b28_b29.csv",
        "C:\\Users\\Lucas\\Desktop\\patents_extraction\\lens_data\\lens_b\\lens_b30_b31_b32_b33_b41_b42_b43_b44.csv",
        "C:\\Users\\Lucas\\Desktop\\patents_extraction\\lens_data\\lens_b\\lens_b60_b61_b62_b63_b64_b65_b66_b67_b68_b81_b82_b99.csv",
        "C:\\Users\\Lucas\\Desktop\\patents_extraction\\lens_data\\lens_c\\lens_c01_c02_c03_c04_c05.csv",
        "C:\\Users\\Lucas\\Desktop\\patents_extraction\\lens_data\\lens_c\\lens_c06_c08.csv",
        "C:\\Users\\Lucas\\Desktop\\patents_extraction\\lens_data\\lens_c\\lens_c07_c09_c10.csv",
        "C:\\Users\\Lucas\\Desktop\\patents_extraction\\lens_data\\lens_c\\lens_c11_c12_c13_c14_c21_c22_c23_c25_c30_c40_c99.csv",
        "C:\\Users\\Lucas\\Desktop\\patents_extraction\\lens_data\\lens_y\\lens_y02.csv",
        "C:\\Users\\Lucas\\Desktop\\patents_extraction\\lens_data\\lens_y\\lens_y04.csv",
        "C:\\Users\\Lucas\\Desktop\\patents_extraction\\lens_data\\lens_y\\lens_y10.csv",
        "C:\\Users\\Lucas\\Desktop\\patents_extraction\\lens_data\\lens_B.csv",
        "C:\\Users\\Lucas\\Desktop\\patents_extraction\\lens_data\\lens_d.csv",
        "C:\\Users\\Lucas\\Desktop\\patents_extraction\\lens_data\\lens_e.csv",
        "C:\\Users\\Lucas\\Desktop\\patents_extraction\\lens_data\\lens_f.csv",
        "C:\\Users\\Lucas\\Desktop\\patents_extraction\\lens_data\\lens_g.csv"};
        for(String path:paths){
            BufferedReader br = new BufferedReader(new FileReader(path));
            Model base = ModelFactory.createDefaultModel();
            String titles = br.readLine();
            String[]titles_a= titles.split(",");
            while(br.ready()){
                String line_data = br.readLine();
                String[]dados=line_data.split(",");
                for(int i =1;i<dados.length;i++){
                    try{
                    base.add(ResourceFactory.createResource(patents+"Patent"+dados[4]),ResourceFactory.createProperty(patents+titles_a[i].replace(" ","")),dados[i]);
                    }catch(Exception e){}
                }
                base.add(ResourceFactory.createResource(patents+"Patent"+dados[4]),ResourceFactory.createProperty(rdf+"type"),ResourceFactory.createResource(patents+"Patent"));

            }
            base.write(new FileOutputStream(path.replace(".csv",".ttl")),"turtle");
            br.close();
        }
    }
}
