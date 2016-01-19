package sk.hackcraft.learning.pers;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileIO {

	private final String FILE_NAME;
	private final String DEF_SEPARATOR = "-";
	private final String TAB_SEPARATOR = "\t";
	private final String LINE_SEPARATOR = System.getProperty("line.separator");
	
	public FileIO(String fileName) {
		this.FILE_NAME = fileName;
	}
	
	public void saveToFile(double[][] matrix) {
		FileWriter out = null;

		try {
		   out = new FileWriter(FILE_NAME);
		   
		   int rowCount = matrix.length;
		   int columnCount = matrix[0].length;
		   
		   StringBuilder stringBuilder = new StringBuilder();
		   stringBuilder.append(rowCount + "-" + columnCount);
		   stringBuilder.append(LINE_SEPARATOR);
		   
		   for (int row = 0; row < rowCount; row++) {
			   for (int column = 0; column < columnCount; column++) {
				   stringBuilder.append(matrix[row][column] + TAB_SEPARATOR);
			   }
			   stringBuilder.append(LINE_SEPARATOR);
		   }
		   
		   out.write(stringBuilder.toString());
		   
		   if (out != null) {
		      out.close();
		   }
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public double[][] loadFromFile() {
		try {
			BufferedReader bufferedReader = new BufferedReader(new FileReader(FILE_NAME));
			String line = null;
			
			final String defLine = bufferedReader.readLine();
			if (defLine != null) {
				String[] sizeOf = defLine.split(DEF_SEPARATOR);
				double[][] result = new double[Integer.parseInt(sizeOf[0])]
											  [Integer.parseInt(sizeOf[1])];
				
				int rowCounter = 0;
				while((line = bufferedReader.readLine()) != null){
					String[] lineArray = line.split(TAB_SEPARATOR);
					for (int i = 0; i < lineArray.length; i++) {
						result[rowCounter][i] = Double.parseDouble(lineArray[i]);
					}
					
					rowCounter++;
				}
				
				return result;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
}