package it.polito.tdp.quadrato.model;

//c'e' il pdf in cui spiega tutto quello che riguarda il problema

//funziona massimo con il quadrato massimo li lato 4
//gia' con il lato 5 il programma non termina in quanto anche con il taglio
//di rami non e' cosi' sufficiente

import java.util.List;

public class TestQuadrato {

	public static void main(String[] args) {

		System.out.println("LATO TRE: \n\n");
		RisolviQuadrato r = new RisolviQuadrato(3) ;
		
		List<List<Integer>> soluzioni = r.quadrati();
		
		for(List<Integer> sol: soluzioni) {
			System.out.println(sol);
		}
		
		System.out.println("LATO QUATTRO: \n\n");
		RisolviQuadrato r2 = new RisolviQuadrato(4) ;
		
		List<List<Integer>> soluzioni2 = r2.quadrati();
		
		for(List<Integer> sol: soluzioni) {
			System.out.println(sol);
		}
	}

}
