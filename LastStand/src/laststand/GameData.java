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
}
