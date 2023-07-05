import java.util.HashMap;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class main{ 

    public static void main(String[] args){
        Scanner userinput = new Scanner(System.in);
        HashMap<ShoppingItem,Integer> inventory = new HashMap<ShoppingItem,Integer>();
        HashMap<String, Integer[]>nutritionMap = new HashMap<String, Integer[]>(); // preset map via txt file 
        nutritionMapFromTXT(nutritionMap);
        double balance = 200.00; // initial money balance

        System.out.println("==================================================================================");
        System.out.println("                            SHOPPING LIST GENERATOR                               ");
        System.out.println("==================================================================================");

        while(true){
            System.out.print("Enter a command or enter \"help\" for a list of commands: ");
            String command = userinput.nextLine();

            if(command.equalsIgnoreCase("help")){
                String message = "COMMANDS:";
                message += "\n===============================================";
                message += "\"quit\" -- quit the program";
                System.out.println(message);
            }else if(command.equalsIgnoreCase("quit")){
                break;
            }
        }

        
        
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
            
            while(filereader.hasNextLine()){
                String targetLine = filereader.nextLine();
                Scanner linereader = new Scanner(targetLine);
                linereader.useDelimiter(",");
                Integer[] nutrition = new Integer[5];

                String name = linereader.next();
                for(int i=0; i<5; i++){
                    nutrition[i] = Integer.parseInt(linereader.next());
                }
                map.put(name,nutrition);
                linereader.close();
            }

            filereader.close();
        }catch(FileNotFoundException e){
            e.getMessage();
        }
    }
}