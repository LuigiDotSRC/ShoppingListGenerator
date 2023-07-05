import java.text.DecimalFormat;
import java.util.ArrayList; 

public class ShoppingList {

    private String name; 
    private String type;  
    private int numberOfItems = 0; 
    private ArrayList<ShoppingItem> shoppingItemList = new ArrayList<ShoppingItem>();
    private DecimalFormat numberFormat = new DecimalFormat("#.00");
    private final int NAME_SPACING_OFFSET = 25;

    public ShoppingList(String name, String type){
        this.name = name; 
        this.type = type; 
    }

    public void addItem(ShoppingItem itemToAdd){
        boolean hasDuplicate = false;
        int targetIndex = 0;

        for(int i=0; i<shoppingItemList.size(); i++){
            if(shoppingItemList.get(i).isDuplicate(itemToAdd)){
                hasDuplicate = true; 
                targetIndex = i;
            }
        }

        if(hasDuplicate){
            shoppingItemList.get(targetIndex).incrementQuantity(itemToAdd.getQuantity());
        }else{
            shoppingItemList.add(itemToAdd);
        }
    
        numberOfItems++;
    }

    public void removeItem(String name){
        for(int i=0; i<shoppingItemList.size(); i++){
            if(shoppingItemList.get(i).getName().equals(name)){
                shoppingItemList.remove(i);
            }
        }
    }
    
    public String getSummary(){
        return "List Name: " + alignString(name, NAME_SPACING_OFFSET) + " || List Type: " + type + " || Number of items: " + numberOfItems + " || Total Cost: $" + this.getListTotalCost();
    }

    public ShoppingItem getItem(String name){
        for(int i=0; i<shoppingItemList.size(); i++){
            if(shoppingItemList.get(i).getName().equals(name)){
                return shoppingItemList.get(i);
            }
        }
        return null;
    }

    public String getDetails(){
        String message = "";
        message += this.getSummary() + "\n==================================================================================================\n";
        
        for(int i=0; i<shoppingItemList.size(); i++){
            String currentIndex = Integer.toString(i+1) + ". ";
            message += currentIndex + shoppingItemList.get(i).getDetails() + "\n";
        }

        return message;
    }

    public String getListTotalCost(){
        double result = 0.0;

        for(int i=0; i<shoppingItemList.size(); i++){
            result += shoppingItemList.get(i).getCost();
        }

        return (numberFormat.format(result)); 
         
    }

    public static String alignString(String txt, int strLength){
        // USE THIS METHOD WHEN PRINTING SUMMARY OR DETAILS 
        int lengthDifference = strLength - txt.length();
        String result = txt;

        if(lengthDifference < 0){
            return result.substring(0, strLength);
        }
        for(int i=0; i<lengthDifference; i++){
            result += " ";
        }

        return result; 
    }


}
