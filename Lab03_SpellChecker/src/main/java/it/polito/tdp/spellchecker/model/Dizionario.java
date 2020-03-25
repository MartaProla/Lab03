package it.polito.tdp.spellchecker.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class Dizionario {
	private List<String>paroleDizionario;
	private String language;
	
	public Dizionario() {
		super();
	}

	public boolean loadDictionary(String language) {
		paroleDizionario=new LinkedList<String>();
		this.language=language;
	
		try {
			FileReader fr = new FileReader("C:\\Users\\prola\\git\\Labb03\\Lab03_SpellChecker\\src\\main\\resources\\"+this.language+".txt");
			BufferedReader br=new BufferedReader(fr);
			String word;
			while((word=br.readLine())!=null) {
				paroleDizionario.add(word.toLowerCase());
			}
			br.close();
			System.out.println("Dizionario "+this.language+" caricato con successo\n");
			return true;
		}catch(IOException e) {
			System.out.println("Errore lettura da file\n");
			return false;
		}
		
	}
	
	public List<RichWord>spellCheckText(List<String>inputTextList){
		List <RichWord>elencoParole=new LinkedList<RichWord>();
		RichWord rw=null;
		for(String s: inputTextList) {
			
			if(paroleDizionario.contains(s)) {
				 rw=new RichWord(s);
				rw.setCorretta(true);
			}else {
				rw=new RichWord(s);
				rw.setCorretta(false);
			}
			elencoParole.add(rw);
				
		}
		return elencoParole;
		
	}
}
