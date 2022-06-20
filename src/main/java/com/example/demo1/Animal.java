package com.example.demo1;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


public class Animal {
    String scientificname;
    String Speciesname;
    ArrayList<ArrayList<Coord>> Coordinates;
    ArrayList<InfoSignalement> Signalements;
    String dateMin;
    String dateMax;
    public Animal(String name){
        this.scientificname=name;
        this.Coordinates = new ArrayList<ArrayList<Coord>>();
        this.Signalements= new ArrayList<InfoSignalement>();
        this.setSignalements();
    }
    public void setSignalements(){
        String url = "https://api.obis.org/v3/occurrence?scientificname=";
        url+= scientificname;
        JSONObject objet = DonnesAnimales.readUrl(url);
        JSONArray array = objet.getJSONArray("results");
        for(int i =0; i<array.length();i++){
            Signalements.add(new InfoSignalement(array.getJSONObject(i)));
        }
    }
    void addCoord(ArrayList<Coord> coord){
        this.Coordinates.add(coord);
    }
    void addSignalement(InfoSignalement Sig){
        this.Signalements.add(Sig);
    }
}
