import java.util.*;
import java.io.*;

public class LetterSoup
{
	private List<String> searchingWords;
	private List<String> words =  new ArrayList<String>();
	private List<String> missingWords = new ArrayList<String>();
	private List<String> findingWords = new ArrayList<String>();
	private List<Integer> coordinates = new ArrayList<Integer>(Arrays.asList(0,0));
	private StringBuilder word = new StringBuilder(4);
	private List<String> soup;
	private int currentChar;

	public void LetterSoup()
	{
		this.searchingWords = null;
		this.soup = null;
		this.currentChar = 1;
	}

	public void setSearchingWords(List<String> searchWords)
	{
		this.missingWords = searchWords;
		this.searchingWords = searchWords;
	}

	public void setSoup(List<String> soup)
	{
		this.soup = soup;
	}

	public List<String> getFindingWords()
	{
		return this.findingWords;
	}

	public void searchWords()
	{
		for (String word: this.searchingWords) {
			this.findWord(word);
		}
	}

	public void setWords()
	{
		for (int j =0 ; j<this.soup.size(); j++) {
			for (int i = 0; i < this.soup.get(j).length(); i ++) {
				if (!this.isVocal(this.soup.get(j).charAt(i))) {
					List<String> lastPositions = new ArrayList<String>(Arrays.asList("notFound"));
					List<String> backFrom = new ArrayList<String>();
					List<List<Integer>> set = new ArrayList<List<Integer>>();

					this.coordinates.set(0, i);
					this.coordinates.set(1, j);
					set.add(Arrays.asList(i,j));
					this.word = new StringBuilder(4);
					word.append(this.soup.get(this.coordinates.get(1)).charAt(this.coordinates.get(0)));
					if (this.searchWord(word, lastPositions, backFrom, set)) {
						this.findingWords.add(0, word.toString());
						// break;
					}	
				}
			}
		}
	}

	private void findWord(String word)
	{
		for (int j =0 ; j<this.soup.size(); j++) {
			for (int i = 0; i < this.soup.get(j).length(); i ++) {
				if (Objects.equals(this.soup.get(j).charAt(i), word.charAt(0))) {
					List<String> lastPositions = new ArrayList<String>(Arrays.asList("notFound"));
					List<String> backFrom = new ArrayList<String>();
					List<List<Integer>> set = new ArrayList<List<Integer>>();

					this.coordinates.set(0, i);
					this.coordinates.set(1, j);
					set.add(Arrays.asList(i,j));
					this.currentChar = 1;
					if (this.depthFirstSearch(word, lastPositions, backFrom, set)) {
						this.findingWords.add(0, word);
						break;
					}			
				}
			}
		}
	}

	private boolean depthFirstSearch(String word, List<String> lastPositions, List<String> backFrom, List<List<Integer>> positionsSet)
	{
		List<Integer> position = new ArrayList<Integer>(Arrays.asList(0,0));

		lastPositions.add(0, "down");
		lastPositions.add(0, "up");
		lastPositions.add(0, "right");
		lastPositions.add(0, "left");

		while (this.currentChar < word.length() && lastPositions.size() != 0) {
			String direction = lastPositions.get(0);
			lastPositions.remove(0);

			if (this.canMoveTo(direction)) {
				switch (direction){
					case "left":
						position.set(1, this.coordinates.get(1));
						position.set(0, this.coordinates.get(0)-1);
						if (this.soup.get(this.coordinates.get(1)).charAt(this.coordinates.get(0)-1) == word.charAt(this.currentChar) && !positionsSet.contains(position)) {
							this.coordinates.set(0, this.coordinates.get(0)-1);
							positionsSet.add(0, position);
							this.currentChar = this.currentChar+1;
							backFrom.add(0, direction);
							lastPositions.add(0, "back");
							return this.depthFirstSearch(word, lastPositions, backFrom, positionsSet);
						}
						break;
					case "right":
						position.set(1, this.coordinates.get(1));
						position.set(0, this.coordinates.get(0)+1);
						if (this.soup.get(this.coordinates.get(1)).charAt(this.coordinates.get(0)+1) == word.charAt(this.currentChar) && !positionsSet.contains(position)) {
							this.coordinates.set(0, this.coordinates.get(0)+1);
							positionsSet.add(0, position);
							this.currentChar = this.currentChar+1;
							backFrom.add(0, direction);
							lastPositions.add(0, "back");
							return this.depthFirstSearch(word, lastPositions, backFrom, positionsSet);
						}
						break;
					case "up":
						position.set(0, this.coordinates.get(0));
						position.set(1, this.coordinates.get(1)-1);
						if (this.soup.get(this.coordinates.get(1)-1).charAt(this.coordinates.get(0)) == word.charAt(this.currentChar) && !positionsSet.contains(position)) {
							this.coordinates.set(1, this.coordinates.get(1)-1);
							positionsSet.add(0, position);
							this.currentChar = this.currentChar+1;
							backFrom.add(0, direction);
							lastPositions.add(0, "back");
							return this.depthFirstSearch(word, lastPositions, backFrom, positionsSet);
						}
						break;
					case "down":
						position.set(0, this.coordinates.get(0));
						position.set(1, this.coordinates.get(1)+1);
						if (this.soup.get(this.coordinates.get(1)+1).charAt(this.coordinates.get(0)) == word.charAt(this.currentChar) && !positionsSet.contains(position)) {
							this.coordinates.set(1, this.coordinates.get(1)+1);
							positionsSet.add(0, position);
							this.currentChar = this.currentChar+1;
							backFrom.add(0, direction);
							lastPositions.add(0, "back");
							return this.depthFirstSearch(word, lastPositions, backFrom, positionsSet);
						}
						break;
					case "back":
						position.set(0, this.coordinates.get(0));
						position.set(1, this.coordinates.get(1));
						positionsSet.remove(position);
						this.goBack(backFrom.get(0));
						this.currentChar = this.currentChar-1;
						backFrom.remove(0);
						break;
					default:
						return false;
				}
			}
			
		}
		return true;
	}

	private boolean canMoveTo(String direction)
	{
		switch (direction){
			case "left":
				if (this.coordinates.get(0)-1 < 0) {
					return false;
				}
				break;
			case "right":
				if (this.coordinates.get(0)+1 >=this.soup.get(0).length()) {
					return false;
				}
				break;
			case "up":
				if (this.coordinates.get(1)-1 < 0) {
					return false;
				}
				break;
			case "down":
				if (this.coordinates.get(1)+1 >= this.soup.size()) {
					return false;
				}
				break;
			default:
				return true;
		}
		return true;
	}

	private void goBack(String direction)
	{
		switch (direction){
			case "left":
				this.coordinates.set(0, this.coordinates.get(0)+1);
				break;
			case "right":
				this.coordinates.set(0, this.coordinates.get(0)-1);
				break;
			case "up":
				this.coordinates.set(1, this.coordinates.get(1)+1);
				break;
			case "down":
				this.coordinates.set(1, this.coordinates.get(1)-1);
				break;
			default:
				return;
		}
	}

	private boolean isVocal(char character)
	{
		List<Character> vocals = new ArrayList<Character>(Arrays.asList('a', 'e', 'i', 'o', 'u'));
		return vocals.contains(character);
	}

	private boolean isNextLetter(int len, char character)
	{
		if (len%2 == 0) {
			return !this.isVocal(character);
		}
		return this.isVocal(character);
	}

	private boolean searchWord(StringBuilder word, List<String> lastPositions, List<String> backFrom, List<List<Integer>> positionsSet) 
	{
		List<Integer> position = new ArrayList<Integer>(Arrays.asList(0,0));

		lastPositions.add(0, "down");
		lastPositions.add(0, "up");
		lastPositions.add(0, "right");
		lastPositions.add(0, "left");

		while (word.toString().length() != 4 && lastPositions.size() != 0) {
			String direction = lastPositions.get(0);
			lastPositions.remove(0);

			if (this.canMoveTo(direction)) {
				switch (direction){
					case "left":
						position.set(1, this.coordinates.get(1));
						position.set(0, this.coordinates.get(0)-1);
						if (this.isNextLetter(this.word.toString().length(), this.soup.get(this.coordinates.get(1)).charAt(this.coordinates.get(0)-1)) && !positionsSet.contains(position)) {
							this.coordinates.set(0, this.coordinates.get(0)-1);
							positionsSet.add(0, position);
							word.append(this.soup.get(this.coordinates.get(1)).charAt(this.coordinates.get(0)));
							backFrom.add(0, direction);
							lastPositions.add(0, "back");
							return this.searchWord(word, lastPositions, backFrom, positionsSet);
						}
						break;
					case "right":
						position.set(1, this.coordinates.get(1));
						position.set(0, this.coordinates.get(0)+1);
						if (this.isNextLetter(this.word.toString().length(), this.soup.get(this.coordinates.get(1)).charAt(this.coordinates.get(0)+1)) && !positionsSet.contains(position)) {
							this.coordinates.set(0, this.coordinates.get(0)+1);
							positionsSet.add(0, position);
							word.append(this.soup.get(this.coordinates.get(1)).charAt(this.coordinates.get(0)));
							backFrom.add(0, direction);
							lastPositions.add(0, "back");
							return this.searchWord(word, lastPositions, backFrom, positionsSet);
						}
						break;
					case "up":
						position.set(0, this.coordinates.get(0));
						position.set(1, this.coordinates.get(1)-1);
						if (this.isNextLetter(this.word.toString().length(), this.soup.get(this.coordinates.get(1)-1).charAt(this.coordinates.get(0))) && !positionsSet.contains(position)) {
							this.coordinates.set(1, this.coordinates.get(1)-1);
							positionsSet.add(0, position);
							word.append(this.soup.get(this.coordinates.get(1)).charAt(this.coordinates.get(0)));
							backFrom.add(0, direction);
							lastPositions.add(0, "back");
							return this.searchWord(word, lastPositions, backFrom, positionsSet);
						}
						break;
					case "down":
						position.set(0, this.coordinates.get(0));
						position.set(1, this.coordinates.get(1)+1);
						if (this.isNextLetter(this.word.toString().length(), this.soup.get(this.coordinates.get(1)+1).charAt(this.coordinates.get(0))) && !positionsSet.contains(position)) {
							this.coordinates.set(1, this.coordinates.get(1)+1);
							positionsSet.add(0, position);
							word.append(this.soup.get(this.coordinates.get(1)).charAt(this.coordinates.get(0)));
							backFrom.add(0, direction);
							lastPositions.add(0, "back");
							return this.searchWord(word, lastPositions, backFrom, positionsSet);
						}
						break;
					case "back":
						position.set(0, this.coordinates.get(0));
						position.set(1, this.coordinates.get(1));
						positionsSet.remove(position);
						this.goBack(backFrom.get(0));
						word.deleteCharAt(word.toString().length()-1);
						backFrom.remove(0);
						break;
					default:
						return false;
				}
			}
		}
		return true;
	}
}