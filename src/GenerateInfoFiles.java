import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class GenerateInfoFiles {

	public GenerateInfoFiles() {
		// TODO Auto-generated constructor stub
	}
	
	private static String queryRandomData(String data) {
		// Method that takes a file name and selects a random data from within it
		List<String> datas = new ArrayList<>(); // Create a list to store data from the file

		try {
			// Open the file for reading
			BufferedReader br = new BufferedReader(new FileReader(data+".txt")); 
			
			// Read each line from the file and add it to the list, removing leading/trailing whitespace
			while ((data = br.readLine()) != null) {
				datas.add(data.trim()); 
			}

			br.close(); // Close the reader to release resources
		
			// Handle the case where the file is not found
		} catch (FileNotFoundException e) {
			System.out.println("Error: File "+data+".txt not found en queryRandomData"+e.getMessage());
			return null; // Return null or handle the error differently as needed	
		} catch (IOException e) {
			System.out.println("Error reading from file: " +data+".txt "+ e.getMessage());
			return null; // Return null or handle the error differently as needed
		}
		
		// Check if the list is empty
		if (datas.isEmpty()) {
			System.out.println("Warning: {data}.txt is empty.");
			return null;

		} else {
			// If the list is not empty, generate a random index and return the data at that index
			Random random = new Random();
			int randomIndex = random.nextInt(datas.size());
			return datas.get(randomIndex);
		}
	}

	private static StringBuilder generateRandomNumber(int longNumber) {
		
		 // Create a StringBuilder to store the random number
		StringBuilder randomNumber = new StringBuilder();
		// Create an instance of the Random class to generate random numbers
	    Random random = new Random();
	    
	    // Generate the first random digit (1-9)
	    int primerDigito = random.nextInt(9) + 1; // nextInt(n) generates a number between 0 (inclusive) and n (exclusive)
	    randomNumber.append(primerDigito); // Append the first digit to the random number
	    
	 // Generate the rest of the random digits
	    for (int i = 1; i < longNumber; i++) { // Starting from the second digit up to the desired length
	        int digito = random.nextInt(10); // Generate a random digit between 0 and 9
	        randomNumber.append(digito); // Append the generated digit to the random number
	    }
	    
	    return randomNumber; // Return the randomly generated number as a StringBuilder object
	}

	// Declare a static final ArrayList named TIPOS_IDENTIFICACION to store identification types
	private static final ArrayList<String> TIPOS_IDENTIFICACION = new ArrayList<>();
	// Static initialization block to add identification types to the ArrayList
	static {
		// Add various identification types to the ArrayList
		TIPOS_IDENTIFICACION.add("CC"); // Colombian Citizen ID
		TIPOS_IDENTIFICACION.add("TI"); // Card Identification Number
		TIPOS_IDENTIFICACION.add("CE"); // Foreigner ID
		TIPOS_IDENTIFICACION.add("PSP"); // Special Passport
	}
	
	// Method to generate a random identification type
	private static String generateIdentificationType() {
		// Create a Random object to generate random indices
		Random randomIdType = new Random();
		// Generate a random index within the bounds of the TIPOS_IDENTIFICACION ArrayList
		int indiceAleatorio = randomIdType.nextInt(TIPOS_IDENTIFICACION.size());
		// Retrieve the identification type at the randomly generated index
		return TIPOS_IDENTIFICACION.get(indiceAleatorio);
	}

	private static String extractIdTypeAndIdNumber(String name, int id) {
		 // Variables declaration
		String data;
		List<String> datas = new ArrayList<>();
		String cadena = "";
		String consulta = "";

		try {
			// Open the file for reading
			BufferedReader br = new BufferedReader(new FileReader("salesmanCount.txt")); // Assuming the file is in
			
			// Read each line from the file																					// the same.
			while ((data = br.readLine()) != null) {
				datas.add(data.trim()); // Trim any leading/trailing whitespace
			}
			br.close(); // Close the reader to release resources
		} catch (FileNotFoundException e) {
			 // Handle the case where the file is not found
			System.out.println("Error: File 'salesmanCount.txt' not found." + e.getMessage());
		} catch (IOException e) {
			 // Handle IO exception
			System.out.println("Error reading from file: " + e.getMessage());
		}
		
		// If the list of data is empty, print a warning and return null
		if (datas.isEmpty()) {
			System.out.println("Warning: salesmanCountFile.txt is empty.");
			return null;
		} else {
			// Iterate through the list to find matching name and ID
			int indice = 0;
			while (indice < datas.size()) {
				// read value of actual position 
				String valor = datas.get(indice);
				String valorLC = valor.toLowerCase();
				// Check if the current line contains both the name and ID
				if (valorLC.contains(name.toLowerCase()) && valorLC.contains(String.valueOf(id))) {
					cadena = datas.get(indice);
				}
				indice++;
			}
			
			 // Iterate through the list to find matching name and ID
			if (!cadena.isEmpty()) {
				 // Find the position of the first semicolon
				int primerPuntoComa = cadena.indexOf(";");
				if (primerPuntoComa != -1) {
					 // Find the position of the second semicolon
					int segundoPuntoComa = cadena.indexOf(";", primerPuntoComa + 1);
					if (segundoPuntoComa != -1) {
						// Extract the content between the first and second semicolon
						String consulwriterDataFilesta = cadena.substring(0, segundoPuntoComa); 
						return consulwriterDataFilesta;
					} else {
						return "No se encontró el segundo punto y coma";
					}
				} else {
					return "No se encontró el primer punto y coma";
				}
				
			} else {
				System.out.println("El nombre de vendedor o Id no coinciden");
			}
			 return null; 
		}
	}
	
	private static String extractRandomIdProductAndPriceProduct(int randomSalesCount){
		Random random = new Random();
		String stringOut=""; 
		List<String> datas = new ArrayList<>();
		try (BufferedReader br = new BufferedReader(new FileReader("productsCount.txt"))) {
	            String line;
	            // Read each line from the file
	            while ((line = br.readLine()) != null) {
	            	// Split the line by semicolon
	            	String[] parts = line.split(";");
	                if (parts.length == 3) { // Check if the line has correct format
	                    String idProduct = parts[0].trim();
	                    // Extract price per unit of product, remove '$' symbol and trim whitespace
	                    Integer priceProductbyUnit = Integer.valueOf(parts[2].replace("$"," ").trim());
	                    // Generate a random number and multiply it with the product's price per unit
	                    datas.add(idProduct+";$"+priceProductbyUnit*Integer.valueOf(generateRandomNumber(1).toString())); 
	                    
	                }
	            }
	        } catch (IOException | NumberFormatException e) {
	            e.printStackTrace();
	        }
		
		// Generate the specified number of random sales
		for (int i=0; i<randomSalesCount;i++) {
		stringOut=stringOut+datas.get(random.nextInt(datas.size()))+"\n";
		
		}
		return stringOut;
	}
		
	private static void writerDataFiles(String fileNameTXT, String dataFile) throws IOException {
	
		// file path
        String filePath = fileNameTXT+".txt";

        // Crear un archivo File
        File file = new File(filePath);

        // Check if the file exists
        if (!file.exists()) {
        	// If it doesn't exist, create a new one
            System.out.println("El archivo "+fileNameTXT+" no existe, se crea uno nuevo.");
            file.createNewFile();
        } else {
        	 // If it exists, overwrite it
            System.out.println("El archivo "+fileNameTXT+" ya existe, se reescribe.");
        }
        // Write to the file
        FileWriter fileWriter = new FileWriter(file,true); // Append mode
        fileWriter.write(dataFile); // Write data to the file
        fileWriter.close(); // Close the file writer

        System.out.println("El archivo se ha actualizado correctamente con estos datos: " + dataFile);

	}	
		
	private static void createSalesManInfoFile( int salesmanCount ) throws IOException{

		// Generate and write information for each salesman following this structure TipoDocumento;NúmeroDocumento;NombresVendedor;ApellidosVendedor
		for (int i=0; i<salesmanCount;i++) {
			// Generate salesman information
        	String dataFile=generateIdentificationType().toString()+";"+generateRandomNumber(8).toString()+";"+queryRandomData("Names")+";"+queryRandomData("LastNames")+"\n";
        	 // Write the information to the file
        	writerDataFiles("salesmanCount", dataFile); 
        }
	}
	
	public static void createProductsFile( int productsCount ) throws IOException {
		 // Generate and write information for each product following this structure IDProducto;NombreProducto;PrecioPorUnidadProducto
        for (int i=0; i<productsCount;i++) {
        // Generate product information
        String dataFile=generateRandomNumber(4).toString()+";"+queryRandomData("Products").toString()+";$"+generateRandomNumber(5)+"\n"; 
        // Write the information to the file
        writerDataFiles("productsCount", dataFile); 
        }
        System.out.println("El archivo se ha actualizado correctamente.");
	
	}
	
	public static void createSalesMenFile( int randomSalesCount, String name, int id) throws IOException {
		// Generate File with name SalesManFile-TypeID-IdNumber
		String firstLineFile=extractIdTypeAndIdNumber(name, id);
		writerDataFiles("SalesManFile"+"-"+firstLineFile.replace(";","-"),firstLineFile+"\n"); 
		// Generate product information
		String producLine=extractRandomIdProductAndPriceProduct(randomSalesCount);
		// Write the information to the file
		writerDataFiles("SalesManFile"+"-"+firstLineFile.replace(";","-"),producLine+"\n"); 

		}
	
	public static void main(String[] args) throws IOException {

	        Scanner scanner = new Scanner(System.in);

	        // Create number of salesman
	        int salesmanCount;
	        do {
	            System.out.println("Introduzca cantidad de vendedores para crear en archivo salesmanCount.txt: ");
	            while (!scanner.hasNextInt()) {
	                System.out.println("Por favor, ingrese un número válido.");
	                scanner.next();
	            }
	            salesmanCount = scanner.nextInt();
	        } while (salesmanCount <= 0);
	        createSalesManInfoFile(salesmanCount);

	        // Create number of products
	        int productsCount;
	        do {
	            System.out.println("Introduzca cantidad de productos para crear en productsCount.txt: ");
	            while (!scanner.hasNextInt()) {
	                System.out.println("Por favor, ingrese un número válido.");
	                scanner.next();
	            }
	            productsCount = scanner.nextInt();
	        } while (productsCount <= 0);
	        createProductsFile(productsCount);

	        // Create number of sales for each salesman
	        int randomSalesCount;
	        String name;
	        int id;
	        do {
	            System.out.println("Introduzca cantidad de ventas para crear, nombre e Id del vendedor en randomSalesCount.txt: ");
	            System.out.println("Cantidad de ventas:");
	            while (!scanner.hasNextInt()) {
	                System.out.println("Por favor, ingrese un número válido para la cantidad de ventas.");
	                scanner.next();
	            }
	            randomSalesCount = scanner.nextInt();
	            scanner.nextLine(); // Clear the keyboard buffer
	            System.out.println("Nombre del vendedor (incluir tildes):");
	            name = scanner.nextLine();
	            System.out.println("Numero ID del vendedor:");
	            while (!scanner.hasNextInt()) {
	                System.out.println("Por favor, ingrese un número válido para el ID del vendedor.");
	                scanner.next();
	            }
	            id = scanner.nextInt();
	        } while (randomSalesCount <= 0 || name.isEmpty() || id <= 0);
	        createSalesMenFile(randomSalesCount, name, id);
	        
	        scanner.close();
	    }
	}