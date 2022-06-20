package com.example.demo1;


import org.json.JSONArray;
import org.json.JSONObject;

public class InfoSignalement {
    String Order;
    String superclass;
    String RecordedBy;
    String species;
    public InfoSignalement(JSONObject resultatRecherche){
        try{
         this.Order=resultatRecherche.getString("order");
        }catch(Exception e){
            this.Order="";
        }

        try{
            this.superclass=resultatRecherche.getString("superclass");
        }catch(Exception e){
            this.superclass="";
        }
        try {
            this.RecordedBy = resultatRecherche.getString("recordedBy");
        }catch(Exception e){
            this.RecordedBy="";
        }
        try{
            this.species=resultatRecherche.getString("species");
        }catch(Exception e){
            this.species="";
        }
        System.out.println(species);


    }
}
