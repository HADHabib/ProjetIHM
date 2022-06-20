package com.example.demo1;

import javafx.geometry.Point2D;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.concurrent.TimeUnit;


public class DonnesAnimales {
    Animal currentAnimal;
    public DonnesAnimales(){
        currentAnimal= new Animal();
    }
    void SetAnimal(Animal animal){
        this.currentAnimal=animal;
    }
    Animal getAnimal(){
        return this.currentAnimal;
    }
    String nameToUrl(String name){
        String res = "https://api.obis.org/v3/occurrence/grid/3?scientificname=";
        res+=name;
        return res;
    }
    public static JSONObject readUrl(String url){
        String json = "";
        HttpClient client = HttpClient.newBuilder().version(HttpClient.Version.HTTP_1_1)
                .followRedirects(HttpClient.Redirect.NORMAL)
                .connectTimeout(Duration.ofSeconds(20))
                .build();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .timeout(Duration.ofMinutes(2))
                .header("Content-Type","application/json")
                .GET()
                .build();
        try{
            json = client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                    .thenApply(HttpResponse::body).get(10, TimeUnit.SECONDS);
        }catch(Exception e ){
            e.printStackTrace();
        }
        return new JSONObject(json);
    }

    void readJsonFromUrl(String url){
        JSONObject jsonweb=readUrl(url);
        JSONArray resultatRecherche = jsonweb.getJSONArray("features");
        for(int i =0;i<resultatRecherche.length();i++) {
            JSONObject article = resultatRecherche.getJSONObject(i);
            JSONArray coord = article.getJSONObject("geometry").getJSONArray("coordinates");
            JSONArray signalements = article.getJSONObject("properties").getJSONArray("n");
            System.out.println(signalements.get(0));
            for(int k=0;k<coord.length();k++){
                for(int j=0;j<coord.getJSONArray(k).length();j++) {
                    float lat = Float.parseFloat(String.valueOf(coord.getJSONArray(k).getJSONArray(j).get(0)));
                    float lon = Float.parseFloat(String.valueOf(coord.getJSONArray(k).getJSONArray(j).get(1)));
                    Location loc = new Location("selectedGeoHash", lat, lon);
                    this.currentAnimal.addCoord(new Coord(lat,lon,GeoHashHelper.getGeohash(loc)));
                }
            }
        }
    }
    private static String readAll(Reader rd) throws IOException{
        StringBuilder sb = new StringBuilder();
        int cp ;
        while((cp=rd.read())!= -1){
            sb.append((char)cp);
        }
        return sb.toString();
    }
    void readJsonFromFile(String filename){
        try(Reader reader = new FileReader(filename)){
            BufferedReader rd = new BufferedReader(reader);
            String jsonText =readAll(rd);
            JSONObject jsonRoot = new JSONObject(jsonText);
            JSONArray resultatRecherche = jsonRoot.getJSONArray("features");
            for(int i =0;i<resultatRecherche.length();i++) {
                JSONObject article = resultatRecherche.getJSONObject(i);
                JSONArray coord = article.getJSONObject("geometry").getJSONArray("coordinates");
                int signalements = article.getJSONObject("properties").getInt("n");
                this.currentAnimal.addSignalement(signalements);

                for(int k=0;k<coord.length();k++){
                    for(int j=0;j<coord.getJSONArray(k).length();j++) {
                        float lat = Float.parseFloat(String.valueOf(coord.getJSONArray(k).getJSONArray(j).get(0)));
                        float lon = Float.parseFloat(String.valueOf(coord.getJSONArray(k).getJSONArray(j).get(1)));
                        Location loc = new Location("selectedGeoHash", lat, lon);
                        this.currentAnimal.addCoord(new Coord(lat,lon,GeoHashHelper.getGeohash(loc)));
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    Animal JSONObjectToAnimal(){
        return null;
    }
    String GeoGPStoGeohash(Point2D point){
        return null;
    }

}
