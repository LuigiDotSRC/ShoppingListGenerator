import java.text.DecimalFormat;
import java.util.HashMap;

public class ShoppingItem implements Purchasable{

    private String name; 
    private double cost; 
    private int quantity; 
    private DecimalFormat numberFormat = new DecimalFormat("#.00");
    private final int NAME_SPACING_OFFSET = 25;

    public ShoppingItem(String name, double cost, int quantity){
        this.name = name;
        this.quantity = quantity;

        if(splitDecimal(cost) == 99){
            cost += 0.01;
        }

        this.cost = cost; 
    }

    public String getDetails(){
        return "Name: " + alignString(name, NAME_SPACING_OFFSET) + " || Cost Per Unit: $" + numberFormat.format(cost) + " || Quantity: " + quantity + " || Total Cost: $" + numberFormat.format(cost*quantity);
    }

    public String getName(){
        return name;
    }

    public double getIndividualCost(){
        return cost;
    }

    public void incrementQuantity(int amount){
        quantity += amount;
    }

    public int getQuantity(){
        return quantity;
    }

    public static Double splitDecimal(Double decimal){
        String[] sections = decimal.toString().split("\\.");
        Double result = Double.parseDouble(sections[1]);
        return result;
    }

    public double getCost(){
        return cost*quantity;
    }

    public boolean isDuplicate(ShoppingItem other){
        if(other.getIndividualCost() == cost && other.getName() == name){
            return true;
        }
        return false;
    }

    public double buyItem(double balance, HashMap<ShoppingItem,Integer> inventory){

        for(ShoppingItem key : inventory.keySet()){
            if(key.isDuplicate(this)){
                System.out.println("DUPLICATE INV");
                inventory.put(key, inventory.get(key) + this.quantity);
                balance -= cost*quantity;
                return balance;
            }
        }

        inventory.put(this,quantity);
        balance -= cost*quantity;
        return balance;
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
