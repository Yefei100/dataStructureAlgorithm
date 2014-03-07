import java.io.*;
import java.util.Scanner;

public class TestingHashTable {
	
	
	private int M=1777; //array size 
	private int probe=0; //number of probes
	File file;
	private String[] hashTable = new String[M]; //hash table
	private static int N=0;
	
	public TestingHashTable(int M, String fileName) {
		
		this.M  = M;
		this.file = new File(fileName);
		
	}
	
	public long ELFhash(String key) {
		
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
    
    public boolean isValidKey(String length, String[] max, String key){
    	
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
    
    public String searchKey(String length, String[] max, String key) {
    	
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
    
    public String[] hashToIndex(String[] hashTable){
    	
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
    
    public String searchIndex(String index) { 
    	
    	String[] indices = hashToIndex(hashTable);
    	if(indices[Integer.parseInt(index)] == null)
    		return "Search index out of bounds";
    	return "The key in this index is" + indices[Integer.parseInt(index)];
    	
    }
    
	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		
		
		try {
			    TestingHashTable hash = new TestingHashTable(1777, "ncd3");
			
	            Scanner scan = new Scanner(hash.file);
	            
	            String length = scan.nextLine(); //read the 1st line of vector length
	            length = length.trim();  //String length

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
            	long end = System.currentTimeMillis();
            	
        		System.out.println("Key insertion has finished.");
        		System.out.println("Total time: " + (end-start) + "ms");
        		System.out.println("Table size: " + hash.M);
        		
        		String[] index = hash.hashToIndex(hash.hashTable);
        		System.out.println("Number of valid data in table: " + N);
        		System.out.println("");
        		
        		while(true) {
        			System.out.println("Enter 0 for search with key and 1 for search with index.");
        			Scanner keyboard = new Scanner(System.in);
        		
        			int myInt = keyboard.nextInt();
        			if (myInt==0){
        				System.out.println("Please enter a key, space between each element.");
        				
        				Scanner keyboard1 = new Scanner(System.in);
        				String myString1 = keyboard1.nextLine();
        				
        				System.out.println(hash.searchKey(length, maxVector,myString1));
        				System.out.println("Number of probes: " + hash.probe);
        				System.out.println("");
        			}
        			
        			if (myInt==1){
        				System.out.println("Please enter a index.");
        				Scanner keyboard2 = new Scanner(System.in);
        				String myString2 = keyboard2.nextLine();
        				System.out.println(hash.searchIndex(myString2));
        			}
        		}
	        } catch (FileNotFoundException e) {
	            e.printStackTrace();
	        }
		
	}
}
