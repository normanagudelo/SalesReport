import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class main {

	private static List<String> ListSalesManFilesFromFolder(String rutaCarpeta) {

		// Crea un objeto File con la ruta de la carpeta
		File carpeta = new File(rutaCarpeta);
		List<String> ListSalesManFiles = new ArrayList<>();

		// Verifica que la carpeta exista y sea realmente una carpeta
		if (carpeta.exists() && carpeta.isDirectory()) {
			// Obtiene una lista de los archivos en la carpeta
			File[] archivos = carpeta.listFiles();

			// Itera sobre la lista de archivos e imprime sus nombres
			if (archivos != null) {
				System.out.println("Archivos en la carpeta:");
				for (File archivo : archivos) {
					if (archivo.getName().toString().contains("SalesManFile")) {
						System.out.println("archivo.getName().toString().contains(\"SalesManFile\") desde ListSalesManFilesFromFolder--->"+archivo.getName().toString().contains("SalesManFile"));
						ListSalesManFiles.add(archivo.getName().toString());
						System.out.println(archivo.getName().toString());
					}

				}
			} else {
				System.out.println("La carpeta especificada no contiene archivos");
			}

			
		} else {
			System.out.println("La carpeta especificada no existe o no es una carpeta válida.");
		}
		return ListSalesManFiles;
	}
	
	private static HashMap<String, Double> ListSalesManAndTotalSales (List<String> ListSalesManFilesFromFolder){
		HashMap<String, Double> SalesManAndTotalSales = new HashMap<>();
		String data;
		
		String firstLine;
		String SalesManInfo;
		Double value=(double) 0; 
		
				
		for(String ListSalesManFile: ListSalesManFilesFromFolder) {
			List<String> datas = new ArrayList<>();
			try {
				// Open the file for reading
				BufferedReader br = new BufferedReader(new FileReader(ListSalesManFile)); // Assuming the file is in
						
				// Read each line from the file																					// the same.
				while ((data = br.readLine()) != null) {
					
					datas.add(data.trim()); // Trim any leading/trailing whitespace
					System.out.println(data.trim());
				}
				br.close(); // Close the reader to release resources
			} catch (FileNotFoundException e) {
				 // Handle the case where the file is not found
				System.out.println("Error: File"+ListSalesManFile+" not found." + e.getMessage());
			} catch (IOException e) {
				 // Handle IO exception
				System.out.println("Error reading from file: "+ListSalesManFile+" " + e.getMessage());
			}
			
		
		String SalesManIdAndNumber=datas.get(0).toString();
		System.out.println("SalesManIdAndNumber-->"+SalesManIdAndNumber);
		
		for(int i=1; i<datas.size();i++) {
			
			int index=datas.get(i).indexOf("$");
			if (index!=-1) {
			index=datas.get(i).indexOf("$")+1;
			//System.out.println("datas.get(i).indexOf(\"$\")+1-->"+index+" datas.get(i)-->"+datas.get(i));
			//System.out.println("datas.size()"+datas.size()+"int i--> "+i+"Index--> "+index+"datas.get(i)"+datas.get(i));
			value=value+Double.valueOf(datas.get(i).substring(index));
			}
			SalesManAndTotalSales.put(SalesManIdAndNumber,value); 
		}
		}
		return SalesManAndTotalSales;
	}
	
	private static LinkedHashMap<String, Double> SortedListSalesManByTotalSales (HashMap<String, Double> ListSalesManAndTotalSales){
		
		// Ordenar el HashMap por valor
        LinkedHashMap<String, Double> SortedListSalesManByTotalSales = ListSalesManAndTotalSales.entrySet()  // Obtener un conjunto de entradas del HashMap
                .stream()  // Convertir el conjunto de entradas en un flujo de datos
                .sorted(Map.Entry.comparingByValue())  // Ordenar las entradas por valor
                .collect(  // Recopilar las entradas ordenadas en un nuevo LinkedHashMap
                        LinkedHashMap::new,  // Proveedor de la colección intermedia (LinkedHashMap)
                        (map, entry) -> map.put(entry.getKey(), entry.getValue()),  // Acumulador para agregar elementos al mapa
                        LinkedHashMap::putAll  // Combinador para combinar varios mapas parciales (no se usa en este caso)
                );

        // Mostrar el HashMap ordenado por valor
        System.out.println("HashMap ordenado por valor:");
        //SortedListSalesManByTotalSales.forEach((clave, valor) -> System.out.println(clave + ": " + valor));
    
		return SortedListSalesManByTotalSales;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// Especifica la ruta de la carpeta que deseas leer
		String rutaCarpeta = new File(".").getAbsolutePath();
		
		List<String> ListSalesManFilesFromFolder=	ListSalesManFilesFromFolder(rutaCarpeta);
		HashMap<String, Double> ListSalesManAndTotalSales=ListSalesManAndTotalSales(ListSalesManFilesFromFolder); 
		LinkedHashMap<String, Double> SortedListSalesManByTotalSales= SortedListSalesManByTotalSales (ListSalesManAndTotalSales); 
		for (Map.Entry<String, Double> datasFinal : SortedListSalesManByTotalSales.entrySet()) {
		    System.out.println(datasFinal.getKey() + " : " + datasFinal.getValue());
		}
	}
}
