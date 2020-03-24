package it.polito.tdp.spellchecker.model;

import java.io.*;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

public class Dictionary {
	private List  <String> dizionario;
	private int nErrate;
	private long tempo;

	public Dictionary() {
		super();
		dizionario = new ArrayList <> ();
	}

	public void lodDictionary (String language) {
		language="src/main/resources/"+language+".txt";
		
		try {
			FileReader fr = new FileReader (language);
				BufferedReader br = new BufferedReader(fr);
			
			String word;
			
			while (( word= br.readLine()) != null){
				word.toLowerCase();
				dizionario.add(word);
				
			}
			br.close();
			
		}
		catch (IOException e) {
			System.out.println("Errore nella lettura del file");
		}
	}
	
	public List <RichWord> spellCheckText (List <String> inputTextList){
		List <RichWord> parole = new ArrayList <>();
		nErrate=0;
		
		for (String s: inputTextList) {
			if (this.check(s)==false) {
				throw new IllegalStateException("I numeri non sono validi. Inserire parola valida!\n");
			}
			
			
			
			int posizione = dizionario.indexOf(s);
			if (posizione != -1) {
				RichWord pTemp  = new RichWord (s, true);
				parole.add(pTemp);
			}
			else {
				RichWord pTemp  = new RichWord (s, false);
				parole.add(pTemp);
				this.nErrate++;
				
			}
		}
		return parole;
		
	}
	
	public String stampaLista (List <RichWord> errateDaStampare) {
		String sTemp= "";
		for (RichWord r : errateDaStampare) {
			if (r.isCorretta()==false) {
				if (sTemp=="") {
					sTemp= r.toString();
				}
				else {
					sTemp+= "\n"+r.toString();
				}
			}
		}
		return sTemp;
	}
	
	public boolean check (String p) {
			boolean check = true;
			for (int i=0; i<p.length();i++) {
				if (!Character.isLetter(p.charAt(i))){
					check=false;
				}
			}
			return check;
		}
	 
	public long doTime () {
		tempo=System.nanoTime();
    	return tempo;
	}

	public int getnErrate() {
		return nErrate;
	}

	public long getTempo() {
		return tempo;
	}
	

	 
}
