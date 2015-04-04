package termCount;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class ReadTerm {

	/**
	 * Read the content of cran.txt starting from I. and till it finds next I.
	 * write the document ID in the outFile.txt
	 */

	public void readCran(final File srcPath, String s) throws IOException {
		Scanner input = null;
		PrintWriter out = null;
		String word;
		String stemOut;
		Stemmer stemObj = new Stemmer();
		try {
			input = new Scanner(new FileReader(srcPath + "//input//cran.txt"));
			out = new PrintWriter(new FileWriter(srcPath
					+ "//output//outFile.txt"));
			File stopWordFile = new File(srcPath + "//input//stopWords.txt");
			word = input.next();
			int i = 1;
			HashMap<String, Integer> termCount = new HashMap<String, Integer>();
			/********* CREATE ARRAYLIST OF STOPWORDS FROM STOP.TXT START ********/
			if (!stopWordFile.exists()) {
				throw new RuntimeException("File Not Found");
			}
			BufferedReader reader = null;
			StringBuilder stopWords = new StringBuilder();
			try {
				reader = new BufferedReader(new FileReader(stopWordFile));
				String line;
				while ((line = reader.readLine()) != null) {
					stopWords.append(line + " ");
				}

			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (reader != null) {
					try {
						reader.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			String[] arrStopWords = stopWords.toString().split("\\s+");
			/********* CREATE ARRAYLIST OF STOPWORDS FROM STOP.TXT STOP ********/
			while (input.hasNext()) {
				if (!word.equals(s)) {
					/********* check stop word *******/
					if (!Arrays.asList(arrStopWords).contains(word)) {
						stemOut = stemObj.steamWord(word); // stem this word
						/***** hash stores the term count START *******/
						if (!termCount.containsKey(stemOut)) {
							Term.terms.add(stemOut);
							termCount.put(stemOut, 1);
						} else {
							int prevCount = termCount.get(stemOut).intValue();
							termCount.put(stemOut, prevCount + 1);
						}
						/***** hash stores the term count STOP *******/
					}
					System.out.println(word);
					/******** check stop word *******/
				} else {
					System.out.println(word + " " + input.next());
					printTerms(out, i, termCount);
					i++;
				}
				word = input.next();
			}
			if (!termCount.isEmpty()) {
				printTerms(out, i, termCount);

			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			input.close();
			out.close();
		}
	}

	/**
	 * This method prints the terms in the file
	 * @param out - Output printwriter stream
	 * @param i - Count for the line
	 * @param termCount - Termcount map to keep track of the terms
	 */
	private void printTerms(PrintWriter out, int i,
			HashMap<String, Integer> termCount) {
		List<String> sortedKeys = new ArrayList<String>(termCount.keySet());
		if (i - 1 > 0) {
			out.println("Doc ID: " + (i - 1) + "\n"
					+ "Total Unique Term Count: " + Term.terms.size()); // Initially
																		// doc-id
																		// = 1
																		// term
																		// count
																		// 0;
		}
		Collections.sort(sortedKeys);
		for (String str : sortedKeys) {
			out.println("Term: " + str + " Count: "
					+ termCount.get(str).intValue());
		}
		Term.terms.clear();
		termCount.clear();
		sortedKeys.clear();
		out.flush();
	}

	/**
	 * it takes the word given by readCran() and checks if it is stopword if
	 * the passed "checkWord" is not stopword it returns the string which was
	 * passed
	 * 
	 * @param srcPath
	 * @param checkWord
	 * @return
	 */
	public String checkStopWord(final File srcPath, String checkWord) {
		File stopWordFile = new File(srcPath + "//input//stopWords.txt");
		if (!stopWordFile.exists()) {
			throw new RuntimeException("File Not Found");
		}
		BufferedReader reader = null;
		StringBuilder stopWords = new StringBuilder();
		try {
			reader = new BufferedReader(new FileReader(stopWordFile));
			String line;
			while ((line = reader.readLine()) != null) {
				stopWords.append(line + " ");
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		String notStopWord = "";
		String[] arrStopWords = stopWords.toString().split("\\s+");
		if (!Arrays.asList(arrStopWords).contains(checkWord)) {
			notStopWord = checkWord;
		}
		return notStopWord;
	}

	public static void main(String[] args) throws IOException {
		Path currentRelativePath = Paths.get("");
		String getProjPath = currentRelativePath.toAbsolutePath().toString();
		final File srcPath = new File(getProjPath + "//src");
		ReadTerm readTermObj = new ReadTerm();
		readTermObj.readCran(srcPath, ".I");
	}
}
