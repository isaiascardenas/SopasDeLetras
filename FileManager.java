import java.util.*;
import java.io.*;

public class FileManager
{
	private String fileName;
	private List<String> fileContent;

	public void FileManager()
	{
		this.fileName = "";
		this.fileContent = null;
	}

	public void setFileName(String fileName)
	{
		this.fileName = fileName;
	}

	public List<String> getFileContent()
	{
		return this.fileContent;
	}

	public void readFile()
	{
		try {
			this.fileContent = new ArrayList<String>();

			File file = new File (this.fileName);
			FileReader fr = new FileReader (file);
			BufferedReader br = new BufferedReader(fr);

			String line = "";
    		while ((line = br.readLine()) != null) {
    			line = line.replaceAll(" ", ""); 
        		this.fileContent.add(line);
    		}
    		fr.close();
		} catch(Exception e){
			e.printStackTrace();
		}
	}

	public void writeFile(List<String> findingWords)
	{
		try {
	        File file = new File(this.fileName);
	        BufferedWriter bw = new BufferedWriter(new FileWriter(file));

            bw.write("Palabras en ambas sopas:\n");
            for (String word: findingWords) {
            	bw.write(word + "\n");
            }

	        bw.close();
		} catch(Exception e){
			e.printStackTrace();
		}
	}
}