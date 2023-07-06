public class DeliveryItem extends ShoppingItem{
    
    private String deliveryDate;
    private String deliveryService; 

    public DeliveryItem(String name, double cost, int quantity, String deliveryDate, String deliveryService){
        super(name,cost,quantity);
        this.deliveryDate = deliveryDate;
        this.deliveryService = deliveryService;
    }

    public String getDetails(){
        return super.getDetails() + " || Delivery Date: " + deliveryDate + " || Delivery Service: " + deliveryService;
    }

    public String getType(){
        return "DeliveryItem";
    }
}
