import java.io.*;
import java.util.Scanner;

public class TestingHashTable {
    //here we go!
    
    private int M=27127; //hashtable initial size 
    private int probe=0; //number of probes
    private File file;
    private String[] hashTable = new String[M]; //hash table
    private int N;
    private String[] indexArray  = new String[M]; // array for searching with index 
    private int index = 0;
    
    public TestingHashTable(int M, String fileName) {
        // constructor 
        
        this.M = M;
        this.file = new File(fileName);
    }
    
    public long ELFhash(String key) {
        // hash function
        
        long h = 0;
        for(int i=0; i<key.length(); i++) {
            h = (h << 4) + (int) key.charAt(i);
            long g = h & 0xF0000000L;
            if(g != 0) h ^= g >>> 24;
            h &= ~g;
        }
        
        return h % M;
    }
    
    public int hashInsert(String line) {
        // collision resolution policy of linear probing
        
        int home;
        int pos  = home = (int)ELFhash(line);
        
        for(int i=0; hashTable[pos] != null; i++) {
            pos = (home + i) % M;
            if (hashTable[pos] != null && hashTable[pos].equals(line))
                    return -1;
        }
            
        hashTable[pos] = line;
        indexArray[index] = line;
        index++;
        return pos;
        
    }
    
    public boolean isValidKey(String length, String[] max, String key){
        // return true if input is a valid key
        
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
    
    public String searchKeyInHashTable(String key) {
        // search with key in hash table and return probes
        
        key = key.trim().replaceAll(" +", " ");
        
            
        int home;
        int pos = home = (int)ELFhash(key);
        for(int i=0; hashTable[pos] != null; i++) {
            pos = (home + i) % M;
            probe ++;
            if (hashTable[pos] != null && hashTable[pos].equals(key))
                return "This key is in the position of: " + Integer.toString(pos) + ".";
        }
        
        return "Not found in the hash table.";
    }
    
    public String searchKeyInIndexArray(String key) {
        
        key = key.trim().replaceAll(" +", " ");
        for(int i=0;i<index;i++){
            if(this.indexArray[i].equals(key))
                return "The key you enter is with index " + i;
        }
        return "Not found in the index array.";
    }
    
    public String[] hashToIndex(String[] hashTable){
        // associate each key with an unique index by each key's occurrence 
        
        for(int i=0; i<M; i++) {
            if (hashTable[i] != null)
                    this.N++;
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
    
    
    
    public String searchIndex(String index) { 
        
        //search key with index
        
        if(indexArray[Integer.parseInt(index)] == null)
            return "Search index out of bounds.";
        
        return "The key in this index is " + indexArray[Integer.parseInt(index)];
        
    }
    
    public int getIndexSize(){
        // get number of valid key
        
        for(int i=0; i<M; i++) {
            if (hashTable[i] != null)
                    this.N++;
        }
        return N;
    }
    
    public int getTableSize(){
        // get number of table size
        
        return M;
    }
    
    public static void main(String[] args) {
        
        long start = System.currentTimeMillis(); //start time
        
        try {
                TestingHashTable hash = new TestingHashTable(27127,"sat10000");
            
                Scanner scan = new Scanner(hash.file);
                
                String length = scan.nextLine(); // read the 1st line of vector length
                length = length.trim();  // string length

                String max = scan.nextLine().trim().replaceAll(" +", " "); 
                //read the 2nd line of max(i) vector
                
                String[] maxVector = max.split(" ");
                
                while (scan.hasNextLine()) {
                    String line = scan.nextLine();
                    if (scan.hasNextLine()){
                        line = line.trim().replaceAll(" +", " ");
                        hash.ELFhash(line);
                        hash.hashInsert(line);
                    }
                }
                
                long end = System.currentTimeMillis(); //end time
                
                System.out.println("Key insertion has finished.");
                System.out.println("Total time: " + (end-start) + "ms");
                System.out.println("Table size: " + hash.getTableSize());
                
                System.out.println("Number of valid data in table: " + hash.getIndexSize());
                System.out.println("Index now is " + hash.index);
                System.out.println("The last element in the index array is " + hash.indexArray[hash.index-1]);
                System.out.println("");
                
                while(true) {
                    System.out.println("Enter 0 for search with key and 1 for search with index.");
                    Scanner keyboard = new Scanner(System.in);
                
                    int myInt = keyboard.nextInt();
                    if (myInt==0){
                        System.out.println("Please enter a key, space(s) between each element.");
                        
                        Scanner keyboard1 = new Scanner(System.in);
                        String myString1 = keyboard1.nextLine();
                        
                        if(hash.isValidKey(length, maxVector, myString1)) {
                            System.out.println(hash.searchKeyInHashTable(myString1));
                            System.out.println("Number of probes: " + hash.probe);
                            System.out.println(hash.searchKeyInIndexArray(myString1));
                            System.out.println("");
                        }
                        else 
                            System.out.println("Not a valid key.");
                    }
                    
                    if (myInt==1){
                        System.out.println("Please enter a index.");
                        Scanner keyboard2 = new Scanner(System.in);
                        String myString2 = keyboard2.nextLine();
                        System.out.println(hash.searchIndex(myString2));
                        System.out.println("");
                    }
                }
                
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        
    }
}
