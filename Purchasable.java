import java.util.HashMap;
public interface Purchasable {
    
    public double buyItem(double balance, HashMap<ShoppingItem,Integer> inventory); 

}
