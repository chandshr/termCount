package termCount;

import java.io.*;
import java.util.Scanner;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;


public class Term {
	
	public String termValue;
	public static int termId = 0;
	public static ArrayList<String> terms = new ArrayList<String>();
	public int termCount = 1;
	
	public Term( String k ){
		this.setTerm(k);
	}
	
	public Term(  ){
		
	}
	
	public void setTermValue( String k ){
		this.termValue = k;
	}
	
	public String getTermValue(){
		return this.termValue;
	}
	
	public void setTermId(){
		termId++;
	}
	
	public int getTermId(){
		return this.termId;
	}
	
	public void setTermCount( int k ){
		this.termCount = k;
	}
	
	public int getTermCount(){
		return this.termCount;
	}
	public void setTerm( String k ){
		ArrayList<String> terms = new ArrayList();
		HashMap< String, Integer > termCount = new HashMap< String, Integer >();
		this.termValue = k;
		if(!termCount.containsKey(k)){
			this.terms.add(k);
			termCount.put(k, 1);
//			this.setTermId();
		}else{
			int prevCount = termCount.get(k).intValue();
			termCount.put(k, prevCount+1);
		}
		try(PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("outFile.txt", true)))) {
		    out.println("Term ID "+this.termId);
		    out.println("Term Value "+this.termValue);
		    out.println("count ** "+termCount.get(k).intValue());
		    out.println("**************");
		    out.flush();
		}catch (IOException e) {
		    System.err.println(e);
		}
	}
	public void setTerms( String path, String k ){
		File file = new File(path);
		if( !file.exists() ){
			throw new RuntimeException( "File Not Found" );
		}
		Scanner reader = null;
		ArrayList<String> terms = new ArrayList();
		try{
			reader = new Scanner(new BufferedReader(new FileReader(file)));
			try(PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("outFile.txt", true)))) {
			    out.println("the text");
			    out.flush();
			}catch (IOException e) {
			    System.err.println(e);
			}
			Term docTerm = new Term();
			while (reader.hasNext()) {
				if(!docTerm.terms.contains(k)){
					docTerm.terms.add(k);
					this.termValue = k;
					this.setTermId();
				}else{
					++this.termCount;
					System.out.println("count"+this.termCount);
				}
//				out.println("Term ID "+this.termId);
//				out.println("Term Value "+this.termValue);
//				out.println("count"+this.termCount);
//				out.println("**************");
				terms.add(reader.next());
            }
//			Term.terms.clear();
		}catch (Exception e) {
            e.printStackTrace();
        }finally{
        	if(reader != null){
        		reader.close();
        	}
//        	if(out != null){
//        		out.close();
//        	}
        }
	}
	
	public ArrayList<String> getTerms(){
		return this.terms;
	}
}
