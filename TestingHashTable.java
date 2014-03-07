import java.io.*;
import java.util.Scanner;

public class TestingHashTable {
    
    
    private static final int M=1777; //Array size 
    private static int probe=0; //Number of probes
    
    static private String[] hashTable = new String[M]; //Hash table
    private static int N=0;
    
    
    static long ELFhash(String key) {

        //Hash Function

        long h = 0;
        for(int i=0; i<key.length(); i++) {
            h = (h << 4) + (int) key.charAt(i);
            long g = h & 0xF0000000L;
            if(g != 0) h ^= g >>> 24;
            h &= ~g;
        }
        return h % M;
    }
    
    static int hashInsert(String line) {
        
        //Insert key to table

        int home;
        int pos  = home = (int)ELFhash(line);
        for(int i=0; hashTable[pos] != null; i++) {
            pos = (home + i) % M;
            if (hashTable[pos] != null && hashTable[pos].equals(line))
                    return -1;
        }
            
        hashTable[pos] = line;
        return pos;
    }
    
    static boolean isValidKey(String length, String[] max, String key){

        //Test if an input key is valid
        
        String[] keyParts = key.trim().replaceAll(" +"," ").split(" ");
        if (keyParts.length != Integer.parseInt(length))
            return false;
        else
            for(int i=0; i<Integer.parseInt(length); i++) {
                if (Integer.parseInt(keyParts[i]) > Integer.parseInt(max[i] )) {
                    return false;
                }
                if (Integer.parseInt(keyParts[i]) < 0){
                    return false;
                }
            }
        return true;
    }
    
    static String searchKey(String length, String[] max, String key) {

        //Search with the key 
        
        key = key.trim().replaceAll(" +", " ");
        if(!isValidKey(length,max,key))
            return "Not a Valid Key";
            
        int home;
        int pos = home = (int)ELFhash(key);
        for(int i=0; hashTable[pos] != null; i++) {
            pos = (home + i) % M;
            probe ++;
            if (hashTable[pos] !=null && hashTable[pos].equals(key))
                return "This key is in the position of: " + Integer.toString(pos);
        }
        return "Not found in the hash table";
    }
    
    static String[] hashToIndex(String[] hashTable){

        // Put the keys inserted in an array with continuous indices
        
        for(int i=0; i<M; i++) {
            if (hashTable[i] != null)
                    N++;
        }
        String[] index = new String[N+1];
        int j=0;
        for(int i=0; i<M; i++) {
            if(hashTable[i] != null){
                index[j] = hashTable[i];
                j++;
            }
        }
        return index;
    }
    
    static String searchIndex(String index) { 

        // Search the key with index value

        String[] indices = hashToIndex(hashTable);
        if(indices[Integer.parseInt(index)] == null)
            return "Search index out of bounds";
        return "The key in this index is" + indices[Integer.parseInt(index)];
    }
    
    public static void main(String[] args) {


        long start = System.currentTimeMillis();

        File file = new File("ncd3"); //Load the file you like :)
        
        try {
                Scanner scan = new Scanner(file);
                
                String length = scan.nextLine(); //read the 1st line of vector length
                length = length.trim();  //String length

                String max = scan.nextLine().trim().replaceAll(" +", " ");  //read the 2nd line of max(i) vector
                
                String[] maxVector = max.split(" ");
                
                while (scan.hasNextLine()) {
                    String line = scan.nextLine();
                    if (scan.hasNextLine()){
                        line = line.trim().replaceAll(" +", " ");
                        ELFhash(line);
                        hashInsert(line);
                    }
                }
                long end = System.currentTimeMillis();
                
                System.out.println("Key insertion has finished.");
                System.out.println("Total time: " + (end-start) + "ms");
                System.out.println("Table size: " + M);
                
                String[] index = hashToIndex(hashTable);
                System.out.println("Number of valid data in table: " + N);
                System.out.println("");
                
                while(true) {

                    //Interact with users

                    System.out.println("Enter 0 for search with key and 1 for search with index.");
                    Scanner keyboard = new Scanner(System.in);
                
                    int myInt = keyboard.nextInt();

                    if (myInt==0){
                        System.out.println("Please enter a key, space between each element.");
                        
                        Scanner keyboard1 = new Scanner(System.in);
                        String myString1 = keyboard1.nextLine();
                        
                        System.out.println(searchKey(length, maxVector,myString1));
                        System.out.println("Number of probes: " + probe);
                        System.out.println("");
                    }
                    
                    if (myInt==1){
                        System.out.println("Please enter a index.");
                        Scanner keyboard2 = new Scanner(System.in);
                        String myString2 = keyboard2.nextLine();
                        System.out.println(searchIndex(myString2));
                    }
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        
    }
}
