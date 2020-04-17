package it.polito.tdp.quadrato.model;

import java.util.ArrayList;
import java.util.List;

public class RisolviQuadrato {
	private int N ; // lato del quadrato
	private int N2 ; // numero di caselle (N^2)
	private int magica ; // costante magica
	
	private List<List<Integer>> soluzioni;
	
	//nel costruttore inializziamo la dimensione del lato del quadrato
	//e ci salviamo anche per comodita' il numero di caselle siccome
	//e' una quantita' che compare spesso nel codice
	public RisolviQuadrato(int N) {
		this.N = N ;
		this.N2 = N*N ;
		//inizializziamo anche la somma corretta che sappiamo che ci deve
		//essere una volta nota la dimensione della riga e della colonna
		this.magica = N*(N2+1)/2 ;
	}
	
	// Calcola tutti i quadrati magici
	public List<List<Integer>> quadrati() {
		//inizializziamo la ricorsione
		List<Integer> parziale = new ArrayList<>() ;
		int livello = 0 ;
		
		//ogni soluzione e' una lista di interi
		this.soluzioni = new ArrayList<List<Integer>>() ;
		
		cerca(parziale, livello) ;
		
		return this.soluzioni ;
	}
	
	// procedura ricorsiva (privata)
	private void cerca(List<Integer> parziale, int livello) {
		
		//se sono all'ultima casella allora ho un quadrato completo
		if(livello==N2) {
			// caso terminale
			//controllo il mio quadrato se e' magico oppure no
			if(controlla(parziale)) {
				// è magico!!
				//System.out.println(parziale) ;
				//aggiungo alla soluzione solo se e' un quadrato magico
				//occhio che devo crearmi un nuovo oggetto inizializzandolo
				//con il parziale e non passare il parziale direttamente altrimenti
				//passiamo il riferimento all'oggetto e poiche' parziale viene 
				//mutato dalla ricorsione, se erroneamente passiamo il riferimento 
				//all'oggetto nella soluzione, allora tutti gli elementi nel vettore
				//soluzione punteranno allo stesso oggetto parziale e conterranno tutti
				//la stessa cosa.
				//quindi importante passare un nuovo oggetto caricato con gli
				//oggetti che popolano parziale che sono interi e quindi immutabili
				this.soluzioni.add(new ArrayList<>(parziale)) ;
			}
			//arrivato al livello massimo comunque devo tornare indietro
			//con la ricorsione, sia che io sia con un quadrato magico 
			//oppure no
			return ;
		}
		
		// controlli intermedi, quando livello è multiplo di N (righe complete)
		//controllo il resto della divisione di livello per N.
		//Senza questo controllo, gia' con il lato di dimensione 4 il programma non
		//termina in quanto il fattoriale di 16 e' un numero enorme.
		//Con questo controllo tagliamo gia' notevolmente molti rami che non
		//portavano da nessuna parte
		if(livello%N==0 && livello!=0) {
			//controlliamo che l'ultima riga inserita sommi a quello giusto a cui
			//noi sappiamo che deve sommare.
			//Se la riga non somma correttamente ritorno indietro con la ricorsione di
			//un livello e quindi completo la riga con un numero diverso in quanto
			//tolgo l'ultimo numero che avevo messo e se ce ne sono ancora da provare
			//lo rimpiazza con un'altra
			if(!controllaRiga(parziale, livello/N-1))
				return ; // potatura (pruning) dell'albero di ricerca
		}
		
		// caso intermedio in cui scorro tutti i numeri che 
		//possono popolare il quadrato e se non sono ancora nel quadrato
		//parziale allora la vado a mettere
		for(int valore=1; valore<=N2; valore++) {
			if(!parziale.contains(valore)) {
				// prova 'valore'
				parziale.add(valore) ;
				cerca(parziale, livello+1);
				//tolgo l'ultimo numero che avevo inserito a questo livello
				//per poterne valutare altri in questo livello di inserimento
				//nel quadrato
				parziale.remove(parziale.size()-1) ;
			}
		}
	}
		
	
	//questa funzione prende una soluzione, che deve riempire il quadrato in totale
	//e controlla se e' un quadrato magico oppure no
	/**
	 * Verifica se una soluzione rispetta tutte le somme
	 * @param parziale
	 * @return
	 */
	private boolean controlla(List<Integer> parziale) {
		
		//e' solo una funzione molto noiosa in cui dobbiamo controllare linee, diagonali
		//e colonne che sommino uguali
		if(parziale.size()!=this.N*this.N)
			throw new IllegalArgumentException("Numero di elementi insufficiente") ;
		
		// Fai la somma delle righe
		for(int riga=0; riga<this.N; riga++) {
			int somma = 0 ;
			for(int col=0; col<this.N; col++) {
				somma += parziale.get(riga*this.N+col) ;
			}
			if(somma!=this.magica)
				return false ;
		}
		
		// Fai la somma delle colonne
		for(int col=0; col<this.N; col++) {
			int somma = 0 ;
			for(int riga=0; riga<this.N; riga++) {
				somma += parziale.get(riga*this.N+col) ;
			}
			if(somma!=this.magica)
				return false ;
		}
		
		// diagonale principale
		int somma = 0;
		for(int riga=0; riga<this.N; riga++) {
			somma += parziale.get(riga*this.N+riga) ;
		}
		if(somma!=this.magica)
			return false ;
		
		// diagonale inversa
		somma = 0;
		for(int riga=0; riga<this.N; riga++) {
			somma += parziale.get(riga*this.N+(this.N-1-riga)) ;
		}
		if(somma!=this.magica)
			return false ;

		return true ;
	}
	
	
	private boolean controllaRiga(List<Integer> parziale, int riga) {
		int somma=0;
		for(int col=0; col<N; col++)
			somma+=parziale.get(riga*N+col);
		return somma==magica ;
			
	}
}
