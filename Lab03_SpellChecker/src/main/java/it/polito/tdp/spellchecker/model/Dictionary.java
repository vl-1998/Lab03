package it.polito.tdp.spellchecker.model;

import java.io.*;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Dictionary {
	private List  <String> dizionario;
	private int nErrate;
	
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
	
	public List <RichWord>  spellCheckTextLinear (List <String> inputTextList) {
		List <RichWord> parole = new ArrayList<>();
		nErrate=0;
		
		for (String s:inputTextList) {
			if (this.check(s)==false) {
				throw new IllegalStateException("I numeri non sono validi. Inserire parola valida!\n");
			}	
			
			if (dizionario.contains(s)) {
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
	
	public List <RichWord>  spellCheckTextDichotomic (List <String> inputTextList) {
		int posizioneMeta = (dizionario.size())/2;
		String parolaCentrale = dizionario.get(posizioneMeta);
		
		
		
		List <RichWord> parole = new ArrayList<>();
		nErrate=0;
		
		for (String s : inputTextList) {
			if (this.check(s)==false) {
				throw new IllegalStateException("I numeri non sono validi. Inserire parola valida!\n");
			}	
			
			if (s.compareTo(parolaCentrale)==0) {
				RichWord pTemp  = new RichWord (s, true);
				parole.add(pTemp);
			}
			
			if (s.compareTo(parolaCentrale)>0) {
				List <String> secondaMeta = new ArrayList <>();
				secondaMeta = dizionario.subList(posizioneMeta+1, dizionario.size());
				
				if (secondaMeta.contains(s)) {
					RichWord pTemp  = new RichWord (s, true);
					parole.add(pTemp);
				}
				else {
					RichWord pTemp  = new RichWord (s, false);
					parole.add(pTemp);
					this.nErrate++;
				}	
			}
			
			if (s.compareTo(parolaCentrale)<0) {
				List <String> primaMeta = new ArrayList <>();
				primaMeta = dizionario.subList(0, posizioneMeta-1);
				
				if (primaMeta.contains(s)) {
					RichWord pTemp  = new RichWord (s, true);
					parole.add(pTemp);
				}
				else {
					RichWord pTemp  = new RichWord (s, false);
					parole.add(pTemp);
					this.nErrate++;
				}	
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

	public int getnErrate() {
		return nErrate;
	}
	 
}
