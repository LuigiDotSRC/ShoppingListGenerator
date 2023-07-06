public class GroceryItem extends ShoppingItem {
    
    private int[] nutrition = new int[5]; //cals, protein, fat, carbs, sodium
    private String expirationDate;
    private final int NAME_SPACING_OFFSET = 25;

    public GroceryItem(String name, double cost, int quantity, int[] nutrition, String expirationDate){
        super(name, cost, quantity);
        this.nutrition = nutrition;
        this.expirationDate = expirationDate;
    }

    public GroceryItem(String name, double cost, int quantity, Integer[] Integernutrition, String expirationDate){
        super(name, cost, quantity);
        this.expirationDate = expirationDate;

        int result[] = new int[Integernutrition.length];

        for (int i = 0; i < Integernutrition.length; i++) {
            result[i] = Integernutrition[i].intValue();
        
        this.nutrition = result;
}
    }

    public String getDetails(){
        return super.getDetails() + " || Calories: " + nutrition[0] + " || EXP date: " + expirationDate;
    }

    public String getNutrition(){
        return "Name: " + alignString(super.getName(), NAME_SPACING_OFFSET) + " || Calories: " + Integer.toString(nutrition[0]) + "Protein: " + Integer.toString(nutrition[1]) + "Fat: " + Integer.toString(nutrition[2]) + "Carbs: " + Integer.toString(nutrition[3]) + "Sodium: " + Integer.toString(nutrition[4]);
    }

    public String getType(){
        return "GroceryItem";
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


