public class Application
{
	public void run()
	{
		FileManager file = new FileManager();
		LetterSoup soup1 = new LetterSoup();
		LetterSoup soup2 = new LetterSoup();

		file.setFileName("Sopa1.in");
		file.readFile();
		soup1.setSoup(file.getFileContent());

		soup1.setWords();

		file.setFileName("Sopa2.in");
		file.readFile();
		soup2.setSoup(file.getFileContent());

		soup2.setSearchingWords(soup1.getFindingWords());
		soup2.searchWords();

		file.setFileName("Solucion.out");
		file.writeFile(soup2.getFindingWords());

		System.out.println("Done!");
	}
}