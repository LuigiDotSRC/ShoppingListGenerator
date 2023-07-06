import java.util.HashMap;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class main{ 

    public static void main(String[] args){

        // initial variables
        Scanner userinput = new Scanner(System.in);
        ArrayList<ShoppingList> listCollection = new ArrayList<ShoppingList>(); // holds list of all shoppinglist item
        HashMap<ShoppingItem,Integer> inventory = new HashMap<ShoppingItem,Integer>(); // inventory hashmap
        HashMap<String, Integer[]>nutritionMap = new HashMap<String, Integer[]>(); // preset map via txt file 
        nutritionMapFromTXT(nutritionMap);
        final double STARTING_BALANCE = 200.00;
        double balance = STARTING_BALANCE; 
        

        System.out.println("==================================================================================");
        System.out.println("                            SHOPPING LIST GENERATOR                               ");
        System.out.println("==================================================================================");

        // program loop
        while(true){
            try{
                System.out.print("\nEnter a command or enter \"help\" for a list of commands  > ");
                String command = userinput.nextLine();

                if(command.equalsIgnoreCase("help")){
                    String message = "COMMANDS:";
                    message += "\n===============================================";
                    message += "\n\"quit\"          -- quit the program";
                    message += "\n\"makelist\"      -- make a new list";
                    message += "\n\"deletelist\"    -- delete a list";
                    message += "\n\"removeitem\"    -- delete a list item";
                    message += "\n\"makeitem\"      -- make a new list item";
                    message += "\n\"alllist\"       -- show all list summaries ";
                    message += "\n\"listdetail\"    -- diplay a list detail";
                    message += "\n\"getnutrition\"  -- display an item's nutrition values";
                    message += "\n\"buyitem\"       -- buy an item from a list";
                    message += "\n\"showinventory\" -- display the inventory list";


                    System.out.println(message);
                }
                
                else if(command.equalsIgnoreCase("quit")){ // break from program loop
                    break;
                }
                
                else if(command.equalsIgnoreCase("makelist")){ // make a new shoppinglist item and append to collection 
                    System.out.print("Enter a name for the new list  > ");
                    String name = userinput.nextLine();
                    System.out.print("Enter a type for the new list  > ");
                    String type = userinput.nextLine();

                    boolean duplicateList = false;
                    for(int i=0; i<listCollection.size(); i++){
                        if(listCollection.get(i).getName().equals(name)){ //check for duplicate lists
                            duplicateList = true;
                        }
                    }

                    if(!duplicateList){
                        listCollection.add(new ShoppingList(name, type));
                        System.out.println("List created!");
                    }else{
                        System.out.println("List already exists");
                    }
                    
                }
                
                else if(command.equalsIgnoreCase("deletelist")){ // delete a list from the collection 
                    System.out.print("Enter the name of the list to delete  > ");
                    String name = userinput.nextLine();

                    for(int i=0; i<listCollection.size(); i++){
                        if(listCollection.get(i).getName().equals(name)){
                            listCollection.remove(i);
                            System.out.println("List removed!");
                        }
                    }
                }
                
                else if(command.equalsIgnoreCase("removeitem")){ // remove an item from a specified list 
                    System.out.print("Enter the name of the item's list  > ");
                    String name = userinput.nextLine();

                    System.out.print("Enter the name of the item  > ");
                    String itemname = userinput.nextLine();

                    for(int i=0; i<listCollection.size(); i++){
                        if(listCollection.get(i).getName().equals(name)){ // find list in collection
                            for(int j=0; j<listCollection.get(i).getList().size(); j++){ // find item in list
                                if(listCollection.get(i).getList().get(j).getName().equals(itemname)){
                                    listCollection.get(i).getList().remove(j);
                                    System.out.println("Item removed!");
                                }
                            }
                        }
                    }
                }
                
                else if(command.equalsIgnoreCase("makeitem")){ // make an item and put inside list 
                    System.out.print("Enter the name of the item's list  > ");
                    String name = userinput.nextLine();

                    System.out.print("Enter the name of the item  > ");
                    String itemname = userinput.nextLine();

                    System.out.print("Enter the unit cost of the item  > ");
                    double unitcost = Double.parseDouble(userinput.nextLine());
                    
                    System.out.print("Enter the quantity of the item  > ");
                    int quantity = Integer.parseInt(userinput.nextLine());
                    
                    System.out.print("Enter the item type [Food, Delivery, Other]  > ");
                    String type = userinput.nextLine();

                    for(int i=0; i<listCollection.size(); i++){ // find list in collection 
                        if(listCollection.get(i).getName().equals(name)){ 
                            if(type.equals("Food")){ // GroceryItem object init 
                                System.out.print("Enter the item expire date DD/MM/YY  > ");
                                String expdate = userinput.nextLine();

                            
                                boolean itemIsPreset = false; 
                                for(String key : nutritionMap.keySet()){ // check if the nutrition value preset exists within the dictionary
                                    if(key.equals(itemname)){
                                        itemIsPreset = true;
                                        Integer nutritionstats[] = nutritionMap.get(key);
                                        listCollection.get(i).addItem(new GroceryItem(itemname, unitcost, quantity, nutritionstats, expdate));
                                        System.out.println("Item added");
                                    }
                                }

                                if(itemIsPreset == false){
                                    System.out.print("Enter Calories  > ");
                                    int calories = Integer.parseInt(userinput.nextLine());

                                    System.out.print("Enter Protein  > ");
                                    int protein = Integer.parseInt(userinput.nextLine());

                                    System.out.print("Enter Fat  > ");
                                    int fat = Integer.parseInt(userinput.nextLine());

                                    System.out.print("Enter Carbohydrates  > ");
                                    int carbs = Integer.parseInt(userinput.nextLine());

                                    System.out.print("Enter Sodium  > ");
                                    int sodium = Integer.parseInt(userinput.nextLine());

                                    int[] nutritionstats = {calories,protein,fat,carbs,sodium};
                                    listCollection.get(i).addItem(new GroceryItem(itemname, unitcost, quantity, nutritionstats, expdate));
                                    System.out.println("Item added");
                                }
                            }else if(type.equals("Delivery")){ // DeliveryItem object init 
                                System.out.print("Enter the item expected delivery date DD/MM/YY  > ");
                                String deliverydate = userinput.nextLine();

                                System.out.print("Enter the delivery service  > ");
                                String deliveryservice = userinput.nextLine();

                                listCollection.get(i).addItem(new DeliveryItem(itemname, unitcost, quantity, deliverydate, deliveryservice));
                                System.out.println("Item added");
                            }else if(type.equals("Other")){ // ShoppingItem object init 
                                listCollection.get(i).addItem(new ShoppingItem(itemname, unitcost, quantity));
                                System.out.println("Item added");
                            }

                        }
                    }
                }

                else if(command.equalsIgnoreCase("alllist")){ // display all list summaies 
                    String message = "ALL LISTS: \n===============================================";
                    for(int i=0; i<listCollection.size(); i++){ // iterate through all lists in collection 
                        message += "\n" + listCollection.get(i).getSummary();
                    }
                    System.out.println(message);
                }
                
                else if(command.equalsIgnoreCase("listdetail")){ // list the detials of a specific shoppinglist
                    System.out.print("Enter the name of the list  > ");
                    String listname = userinput.nextLine();

                    for(int i=0; i<listCollection.size(); i++){
                        if(listCollection.get(i).getName().equals(listname)){
                            System.out.println(listCollection.get(i).getDetails());
                        }
                    }
                }

                else if(command.equalsIgnoreCase("getNutrition")){ // list the nutrition values of a specific item 
                    System.out.print("Enter the name of the list  > ");
                    String listname = userinput.nextLine();
                    
                    System.out.print("Enter the name of the item  > ");
                    String itemname = userinput.nextLine();

                    for(int i=0; i<listCollection.size(); i++){ // find the list within the colleciton 
                        if(listCollection.get(i).getName().equals(listname)){
                            ShoppingList target = listCollection.get(i);
                            
                            for(int j=0; j<target.getList().size();i++){ // find the object within the list 
                                if(target.getList().get(j).getType().equals("GroceryItem") && target.getList().get(j).getName().equals(itemname)){ // target must be of type GroceryItem and have target name
                                    Object obj = target.getList().get(j);
                                    GroceryItem targetitem = (GroceryItem) obj; // refactor from Parent class to Child class to access more methods 
                                    System.out.println(targetitem.getNutrition());
                                }
                            }

                            
                        }
                    }
    
                }

                else if(command.equalsIgnoreCase("buyitem")){ // buy an item from a list, move it to inventory and change balance
                    System.out.print("Enter the name of the list  > ");
                    String listname = userinput.nextLine();
                    
                    System.out.print("Enter the name of the item  > ");
                    String itemname = userinput.nextLine();

                    for(int i=0; i<listCollection.size(); i++){
                        if(listCollection.get(i).getName().equals(listname)){ // find list within collection 
                            ShoppingList target = listCollection.get(i);
                            
                            for(int j=0; j<target.getList().size(); j++){ // find object within list 
                                if(target.getList().get(j).getName().equals(itemname)){
                                    balance = target.getList().get(j).buyItem(balance,inventory); // update balance 
                                    System.out.println("Item purchased! Current balance: $" + Double.toString(balance));
                                }
                            }

                            
                        }
                    }
                }

                else if(command.equalsIgnoreCase("showinventory")){ // display inventory hashmap 
                    System.out.println("INVENTORY: \n=======================================\n");
                    printInventoryMap(inventory);
                }

            } catch(NumberFormatException exceptionnumformat){
                System.out.println("Invalid Input");
                System.out.println(exceptionnumformat.getMessage());
            } 
        
        }
        //java.lang.NumberFormatException
        userinput.close();
        
    }

    public static void printNutritionMap(HashMap<String,Integer[]> map){
        for(String key : map.keySet()){ 
            String valueset = "";
            for(int i=0; i<5; i++){
                valueset += map.get(key)[i] + ", ";
            }
            System.out.println(key + " : " + valueset);
        }
    }

    public static void printInventoryMap(HashMap<ShoppingItem,Integer> map){
        for(ShoppingItem key : map.keySet()){
            System.out.println(key.getName() + " : " + map.get(key));
        }
    }

    public static void nutritionMapFromTXT(HashMap<String, Integer[]> map){
        try{
            Scanner filereader = new Scanner(new File("nutritiondata.txt"));
            
            while(filereader.hasNextLine()){ // read through all lines in txt file 
                String targetLine = filereader.nextLine();
                Scanner linereader = new Scanner(targetLine); // scan through single line in txt file 
                linereader.useDelimiter(",");
                Integer[] nutrition = new Integer[5]; 

                String name = linereader.next();
                for(int i=0; i<5; i++){
                    nutrition[i] = Integer.parseInt(linereader.next()); // update params 
                }
                map.put(name,nutrition); // place in hashmap 
                linereader.close();
            }

            filereader.close();
        }catch(FileNotFoundException e){
            e.getMessage();
        }
    }
}
