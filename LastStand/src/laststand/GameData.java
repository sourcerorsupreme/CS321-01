/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package laststand;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Random;
import com.fasterxml.jackson.databind.ObjectMapper;
/**
 *
 * @author Peter
 * Class used to provide random items/ enemies for Battle Class
 */
public class GameData {
    public List<Entity> entity;
    public List<Item> item;
    
    public GameData(){
        
    }
    /**
     * 
     * @param filename Json file named where data will be read from
     * Will map "entities" and "items" to the Entity Class and Item Class
     */
    public GameData(String filename){
        ObjectMapper mapper = new ObjectMapper();
        try{
            GameData temp = mapper.readValue(new File(filename),GameData.class);
            this.entity = temp.entity;
            this.item = temp.item;
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * Return a random entity from the entities read in from the JSON file.
     * @return 
     */
    public Entity getRandomEntity(){
        Random rand = new Random();
        Entity randomEntity = entity.get(rand.nextInt(entity.size()));
        return randomEntity;
    }
    
    /**
     * Return a random item from the items read in from the JSON file
     * @return 
     */
    public Item getRandomItem(){
        Random rand = new Random();
        Item randomItem = item.get(rand.nextInt(item.size()));
        return randomItem;
    }
    /**
     * Save player information to json file
     * @param player
     * @param filename 
     */
    public static void saveGame(Entity player, String filename){
        ObjectMapper mapper = new ObjectMapper();
        try{
            mapper.writeValue(new File(filename), player);
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
    public static Entity loadSave(String filename){
        ObjectMapper mapper = new ObjectMapper();
            try{
                Entity entity = mapper.readValue(new File(filename),Entity.class);
                return entity;
            }
            catch (IOException e) {
                e.printStackTrace();
                return null;
            }
    }
    public static int countJsonFiles(){
        File directory = new File("./");
        
        if (!directory.isDirectory()){
            throw new IllegalArgumentException("Path is not a directory");
        }
        File[] files = directory.listFiles((directory1, name) -> {
            boolean isJson = name.toLowerCase().endsWith(".json");
            return isJson && !name.equals("data.json");
        });

        return files != null ? files.length : 0;
    }
    
    public static File[] getFiles(){
        File directory = new File("./");
        
        if (!directory.isDirectory()){
            throw new IllegalArgumentException("Path is not a directory");
        }
        
        File[] files = directory.listFiles((directory1, name) -> {
            boolean isJson = name.toLowerCase().endsWith(".json");
            return isJson && !name.equals("data.json");
        });

        return files;
    }
    public static String extractName(String name){
        int lastBackslash = name.lastIndexOf('\\');
        int lastDot = name.lastIndexOf('.');
        return name.substring(lastBackslash + 1, lastDot);
    }
}

