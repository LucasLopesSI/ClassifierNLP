/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package patentclassifier;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;
import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayes;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ArffSaver;
import weka.core.converters.ConverterUtils.DataSource;
import weka.filters.Filter;

/**
 *
 * @author Lucas
 */
public class PatentClassifier {
    static String path = "";
    static String chromeDriver = "";
    public static void TestClassifier(){
        LinkedList<String> Validation = new LinkedList<String>();
        //Y
          Validation.add("Substation circuit breaker automation kit");
          Validation.add("System Using High Frequency Carrier Chains Through Medium Voltage And Low Voltage Electricity Distribution Networks Electricity Metering Device User Terminal Gas Insulated Device Containing Measurement Transformers And A Grounding Device Apparatus Containing Measurement Transformers Current Capacitive Coupling Device Appliance Acp And Acs Remote Terminal Unit And Multifunctional Portable Terminal");
          Validation.add("Electronic Electric Power Meter With Integrated Digital Certification Mechanism For Secure Communication");
          Validation.add("Remote Meter Reading Device");
          Validation.add("Antenna Accessory Scheme for Mounting an Antenna on a Meter");
          Validation.add("Digital Current Transformer for Monitoring the Use of Electro-electronic Equipment - Digital Ammeter");
          
         //B
          Validation.add("High-Al Steel Sheet Excellent In Working Capacity And Method For Producing It");
          Validation.add("Method for Production of Aluminum Alloy Plates for Bearings");
          Validation.add("Device to cross-section a laminated strip");
          Validation.add("Preforms By Process Of Me For Brazing");
          Validation.add("Casting Reduction Device");
          Validation.add("Sand Mold and Method to Form a Sand Mold");
          Validation.add("Protective Gas Measurement Device And Process");
          
         //A
          Validation.add("Apparatus And Clothing");
          Validation.add("Smoking System And Electric Method");
          Validation.add("Automatic Inflatable Vest");
          Validation.add("Tailored shoe for one foot Method to build the same and Tailored shoe kit");
          Validation.add("Protective Clothing, Especially Of Braided Metal Rings");
          Validation.add("Facial Mask For Oral Medical Treatment And Method To Use Facial Mask For Oral Medical Treatment");
          for(String title : Validation){
                System.out.println("Campo "+ClassifyFieldInstance.getField(title));
          }
          
    }
    
    
    public static void printRttfFile(){
     LinkedList<String>tecnological_classes=new LinkedList<String>();
                LinkedList<String>scientific_classes=new LinkedList<String>();
                String file_path="C:\\Users\\Carlos\\Desktop\\github\\ClassifierNLP\\patents_extraction\\id_ipc_cpc_field";
                List<String[]> lines = ReadandFillPatentFile.patentReader(file_path);
                
                for(String[] line : lines){
                    List<String>lin = ReadandFillPatentFile.patentReader(line);
                    for(String li : lin){
                        String[] aux = li.split("#");
                        if(!tecnological_classes.contains(aux[0]))
                                tecnological_classes.add(aux[0]);
                        
                        String[]scientifics = aux[1].split(",");
                        
                        for(String scientific: scientifics){
                            if(!scientific_classes.contains(scientific))
                                    scientific_classes.add(scientific);
                            if(!scientific.equals("0") && !scientific.equals("")){
                                System.out.println(aux[0]+","+"\""+scientific+"\"");
                            }
                        }
                    }
                }
                
                tecnological_classes.sort(null);
                for(String aux: tecnological_classes){
                    System.out.print("\""+aux+"\""+",");
                }
//               
                System.out.println();
                for(String aux: scientific_classes){
                    System.out.print("\""+aux+"\""+",");
                }
    }
    
    public static Instances apllyBagOfWords(Instances instances, Filter filter){
        
        Instances dataFiltered;
        // apply the StringToWordVector filter
        try {
            dataFiltered = weka.filters.Filter.useFilter(instances, filter);
        } catch (Exception e) {
            dataFiltered = instances;
            e.printStackTrace();
        }
        //save to file
         saveInstancesAsArff(dataFiltered,".\\src\\patentclassifier\\BagOfWords2.arff");
        return dataFiltered;
    }
    
    public static void saveInstancesAsArff(Instances dataFiltered, String path){
       ArffSaver saver = new ArffSaver();
        saver.setInstances(dataFiltered);
        try {
            saver.setFile(new File(path));
            saver.writeBatch();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        /****************************************************************
         * Código para leitura de txt e escrita de arff File
         ****************************************************************/
//         BagOfWordsModel.patentReader("C:\\Users\\Carlos\\Desktop\\github\\ClassifierNLP\\patents_extraction\\bagOfWordsClassifier\\rawCitationField.txt");
        /************************************************************************************************
         * Código para manipulação de arff e treinamento de classificadores com Weka
         ************************************************************************************************/
//        DataSource source = new DataSource("C:\\Users\\Lucas\\Documents\\NetBeansProjects\\PatentClassifier\\src\\patentclassifier\\rawCitationClass6.arff");
//        Instances citation = source.getDataSet();
//        Instances citation2 = apllyBagOfWords(citation, BagOfWordsModel.makeBagOfWords(citation));
//        ListIterator<Instance> instances = citation2.listIterator();
//        while(instances.hasNext())
//        System.out.println(instances.next());
            path = args[0];
            String translate = PatentReader.dataPatentReader.readLensPatentCSV(path);
            trasnlateText.translateToEnglish(translate,path);
            
//          LinkedList<String> concepts = KnowBase.KnowledgeBaseClassifier.TagEntitiesInDescription("Registered Shoes With Padded Socks", 0.3);
//                    
//          for(int i=0;i<concepts.size();i++){
//            Thread.sleep(1500);
//             String query="PREFIX dcterms: <http://purl.org/dc/terms/>\n" +
//                        "select distinct ?Concept where {\n";  
//            query+="<"+concepts.get(i)+">"+" dcterms:subject ?Concept .\n";
//            query+="}\n" +
//            "LIMIT 100";
//            System.out.println(KnowBase.KnowledgeBaseClassifier.queryInDBPEDIA("http://pt.dbpedia.org/sparql",query));
//            query="";
//          }
           
          
          
//        String text="\"BALANCED SOLUTIONS OF OXYGENED SOLVENTS WITH PHYSICAL AND CHEMICAL CHARACTERISTICS SIMILAR TO THE MONOMETHYL ETHER OF PROPYLENE Glycol AND MONOETHYL ETHER OF ETHYLENE Glycol. Balanced solutions of oxygenated solvents with physicochemical characteristics similar to propylene glycol monomethyl ether and ethylene glycol monoethyl ether based on a mixture of miscible solvents with different chemical functions are described. Such solutions comprise a range of formulations with a plurality of glycolic ethers derived from ethylene oxide or propene oxide, alcohols with straight and / or branched chains composed of 2 to 6 carbon atoms, ketones with carbon chains composed of 3 to 6 carbon atoms and alkyl acetates presenting in the alkyl functional group, carbon chains composed of 2 to 6 carbon atoms.";
//        text = text.replace(".","").replace(",","").replace("\"", "").replace("(","").replace(")","");
//        String text2="System, method and means that can be read by computer to identify the track assigned to a locomotive. a system (10) is provided to identify a track designation for a locomotive (12), which travels along the track (14). The system includes at least one receiver embedded (16 and 18) in the locomotive, so that it can communicate wirelessly with several satellites (20, 22, 24 and 26), in such a way as to provide a respective initial location for at least one of the embedded antennas (28 and 30) of the locomotive. In addition, the system (10) includes at least one receiver on the side of the road (32 and 34), which is wirelessly coupled to at least one of the embedded receivers. At least one receiver on the side of the road is positioned adjacent to the track, so that it communicates wirelessly with several satellites, in such a way that it can provide a respective first location corrected for the respective initial location of at least one of the embedded antennas, for at least one of the embedded receivers. A method (100) is also provided for identifying the track that has been assigned to a locomotive (12), which travels along a track (14).";
//        text2 = text2.replace(".","").replace(",","").replace("\"", "").replace("(","").replace(")","").replace("0","").replace("1","");
//        String text3 = "COUNTERPLATE FOR WALL PANELS, ROOMS AND FURNITURE MANUFACTURING. Object of this application for registration, aims to design a wooden panel, for use in the furniture industry, civil construction, decoration, visual communication and any and all works, where wood needs to be used. In other words, it is a panel that comes to replace and / or be a new option compared to MDF for medium density fiber boards, and HDF for high density panels, differing even more, due to the fact that the latter are not recommended for application in structures that need to support a lot of load, given that it has a resistance to rupture lower than that of conventional wood, being discouraged the application of this patent application in constructions that require great effort, such as roof framing, support of bridges, etc .. After several researches, we developed the \"PLATED PANEL FOR WALL PANELS, ROOMS AND FURNITURE MANUFACTURING\", with the exact objective of replacing MDF and HDF, which are products that have great versatility, at a relatively low cost. low. However, this results in products with inferior quality and durability, when compared to its other competitors.";
//        text3 = text3.replace(".","").replace(",","").replace("\"", "").replace("("," ").replace(")","");
//        String text4 = "GRAIN MEASURING DEVICE FOR USE ON A FISHING BOAT. The present invention refers to a device that allows the release of grains used as feed for fishing, in an alternating and automatic way using the very force of the water current due to its constructive disposition, through a circular compartment (01) that it will have a lower cover (02) for the side opening (03), having a lower opening (04) with marking (05), chassis (06) with side support (07), and also side holes (08) in addition to a hole bottom (F) for the release of the grains and, in its internal part, it contains a transversal Support (28). This circular compartment (01) will also have a backrest support (10) installed externally in the central part and a fixation support (13) installed in the upper part of it, in order to secure it to the edge of the Canoe or Boat, the release of the grains provided by the rotation coming from a Helix (25) that moves a Horizontal Axis (22) which in turn provides rotation to a vertical Axis (27) that when rotating passes a horizontal Pin (29) on a Dosing Disc (26), pushing the grains out due to the holes in this Disc being aligned to a Hole (F) in the bottom of the Circular Compartment (01).";
//        text4 = text4.replace(".","").replace(",","").replace("\"", "").replace("("," ").replace(")","");
//        String text5 = "WIRELESS OPTICAL CONVERTER. The said invention consists of a device (1) that comprises and three basic modules and converts data from energy meters that Not transmitted through an optical port, converts to a digital signal and transmits wirelessly through \"bluetooth\", \"WI technology -FI \",\" ZIGBEE \"or\" FM \"with these criteria depending on the need, possibility and or availability of the technique for the construction of the device.";
//        text5= text5.replace(".","").replace(",","").replace("\"", "").replace("("," ").replace(")","");
//        String text6 = "SURVEILLANCE SYSTEM FOR VEHICLE AUTOMOTORS. It describes how the present Invention Privilege, presents a monitoring system for vehicles of any type, making its use safer, therefore, it is compatible with micro cameras (1), a recording device (2), which can be an HD , a CD / DVD recorder, a VHS recorder or any other type of media recorder, a quaid, a protective case (3), a child recorder (4) and a signal transmission device (5) and a drive device (6) connected to the vehicle's ignition (G) (V). According to the above components, they are mounted on any vehicle (V), and the cameras (1) may be outside the format of capturing images of the entire interior of the vehicle, the amount of which may vary according to the type of vehicle to be monitored. Therefore, at a point on the vehicle (V), a protective box (3) is mounted, securely closed by a coded key or key, inside which the recording device (2) is mounted, the device being considered connected to the cameras (1) and the child recording device (4), which are activated by the drive device (6) connected directly to the vehicle's ignition (G) (V).";
//        text6 = text6.replace(".","").replace(",","").replace("\"", "").replace("("," ").replace(")","");
//        String text7 = "MATTRESS PROTECTIVE SHEET. The present patent application relates to a protective mattress sheet having retaining strips attached to the lateral ends of the sheet, as well as to a method for adjusting the protective sheet to the mattress.";
//        text7 =  text7.replace(".","").replace(",","").replace("\"", "").replace("(","").replace(")","");
//        String text8 = "Among other things, videos and commercials are downloaded to a mobile device for storage on the mobile device and later playout on the mobile device. The downloading includes downloading of metadata associated with the commercials and based on which the mobile device can select commercials for insertion into videos being played back to the user of the mobile device. The commercials to be downloaded are selected based on one or a combination of any two or more of the location of the user, the type of mobile device, and personally-identifiable information about a user of the mobile device.";
//        text8 = text8.replace(".","").replace(",","").replace("\"", "").replace("(","").replace(")","");
//        String text9 = "A processor-implemented method for providing merchants business advice, the method including: providing a merchant with a dashboard, the dashboard including information related to the merchant's business and a communication link to the merchant's financial institution; receiving a request from the merchant via the dashboard, the request being for a service provided by the merchant's financial institution; processing the request from the merchant by using data about the merchant's business; and providing a response to the request for viewing at the dashboard by the merchant.";
//        text9 = text9.replace(".","").replace(",","").replace("\"", "").replace("(","").replace(")","").replace(":","").replace(";", "").replace("-", " ");
////        text= trasnlateText.translate("pt","en",text);
////          PrepareBagOfWords.trainBagOfWordsModel();
//          String[] trainedBagOfWords = {"C:\\Users\\Carlos\\Desktop\\github\\ClassifierNLP\\PatentClassifier\\src\\patentclassifier\\TrainedBagWordsLematizado.txt"};
//          PrepareBagOfWords.readTrainedModel(trainedBagOfWords);
//          TestClassifier();
          
//        for(String field:PrepareBagOfWords.frequency_table.keySet()){
////              System.out.println(field);
//              HashMap<String,Double> a = PrepareBagOfWords.frequency_table.get(field);
//              for(String word:a.keySet()){
////                    System.out.println(field+"\t"+word+"\t"+a.get(word));
//              }
//        }

//        trasnlateText.printWords();
//        TestClassifier();
//        List<String[]> patents = ReadandFillPatentFile.patentReader("C:\\Users\\Carlos\\Desktop\\github\\ClassifierNLP\\patents_extraction\\PatentsUSPTO.tsv");
//        for(String[] patent:patents){
//            String description = patent[4];
//            description = description.replace("."," ").replace(","," ").replace("\"", " ").replace("("," ").replace(")"," ").replace(":"," ").replace(";", " ").replace("-", " ");
//            if(!description.contains("Current U"))
//                System.out.println("Campo "+ClassifyFieldInstance.getField(description));
//        }

          
          
//        System.out.println("Campo "+ClassifyFieldInstance.getField(text2));
//        System.out.println("Campo "+ClassifyFieldInstance.getField(text3));
//        System.out.println("Campo "+ClassifyFieldInstance.getField(text4));
//        System.out.println("Campo "+ClassifyFieldInstance.getField(text5));
//        System.out.println("Campo "+ClassifyFieldInstance.getField(text6));
//        System.out.println("Campo "+ClassifyFieldInstance.getField(text7));
//        System.out.println("Campo "+ClassifyFieldInstance.getField(text8));
//        System.out.println("Campo "+ClassifyFieldInstance.getField(text9));
    }
}
