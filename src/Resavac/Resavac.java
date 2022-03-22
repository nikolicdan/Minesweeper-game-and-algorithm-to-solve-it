package Resavac;

import java.util.Vector;

import Glavni.Polje;

public class Resavac {
	private static Vector<PoljeRes> otvorenaPolja;
	private static Vector<PoljeRes> neotvorenaPolja;
	private static Vector<PoljeRes> marginalnaPolja;
	private static int brResenja;
	
	public static PoljeRes nadjiResenja(int m, int n, PoljeRes[][] polja, int brNeobelezenihMina, boolean gameWon, boolean gameLost, boolean prviKlik) {
		//povratna vrednost
		PoljeRes resenje;
		
		//ako je zavrsena igra prekida se izvrsavanje
		if (gameWon || gameLost)
			return null;
		
		//ako je prvi klik
		if (prviKlik) 
			return polja[m/2][n/2];
		
		//odredi susedna polja svakog polja
		Resavac.susednaPoljaInit(m, n, polja);
		
		//napravi listu svih otvorenih polja koja imaju za suseda bar jedno neotvoreno polje koje nije obelezeno kao mina
		Resavac.nadjiOtvorenaPolja(polja);
		
		//napravi listu svih neotvorenih polja koja nisu obelezena kao mine i koja za suseda imaju bar jedno otvoreno polje
		Resavac.nadjiNeotvorenaPolja(polja);
		
		//napravi listu svih neotvorenih polja nija nisu obelezena kao mine i koja nemaju susedna otvorena polja
		Resavac.nadjiMarginalnaPolja(polja);
		
		
		//ako nema otvorenih vrati random polje
		if (otvorenaPolja.isEmpty())
			return marginalnaPolja.get(marginalnaPolja.size() / 2);
				
		//ako postoji otvoreno polje oko kojih sigurno nema mina 
		//ili neotvoreno koje je sigurno mina
		for (PoljeRes polje: otvorenaPolja) {
			polje.razlika = polje.brSusednihMina;
			for (PoljeRes susednoPolje: polje.susednaPolja) 
				if (susednoPolje.minaObelezeno) 
					polje.razlika--;
				
			if (polje.razlika == 0) {
				polje.klik = 3;
				return polje;
			}
			else if (polje.razlika == polje.susednaPoljaNeotvorena.size()) 
				for (PoljeRes susednoPolje: polje.susednaPoljaNeotvorena) {
					susednoPolje.klik = 2;
					return susednoPolje;
				}
		}
		
		//ako ima previse kombinacija da bi mogao da izracuna verovatnocu vrati random polje kao resenje
		//if (otvorenaPolja.size() > 30) 
			//return neotvorenaPolja.get(neotvorenaPolja.size() / 2);
		
		//poziv rekurzivne funkcije koja proverava sve kombinacije
		brResenja = 0;
		Resavac.nadjiResenjaRekurzija(0, neotvorenaPolja.size());
		
		//izracunaj verovatnocu da na svakom polju bude mina
		System.out.println();
		for (PoljeRes polje: neotvorenaPolja) {
			polje.odrediVerovatnocu(brResenja);
			System.out.println(polje);
		}
		System.out.println();
		
		//odredi koje polje ima najvecu verovatnocu da se na njemu nalazi ili ne nalazi mina
		resenje = neotvorenaPolja.get(0);
		for (PoljeRes polje: neotvorenaPolja) {
			if (min(polje.verovatnoca, 1.0 - polje.verovatnoca) < min(resenje.verovatnoca, 1.0 - resenje.verovatnoca)) 
				resenje = polje;
		}
		
		//ako je verovatnoca veca od 0.5 onda desni klik
		if (resenje.verovatnoca > 0.5)
			resenje.klik = 2;
	
		return resenje;
	}
	
	private static void nadjiResenjaRekurzija(int i, int n) {
		if (i == n) {
			for (PoljeRes polje: neotvorenaPolja) 
				if (polje.minaObelezeno)
					polje.brPutaObelezeno++;
			
			brResenja++;
			return;
		}
		
		PoljeRes trPolje = neotvorenaPolja.get(i);
	
		if (mozePrazno(trPolje)) {
			for (PoljeRes susednoOtvoreno: trPolje.susednaPoljaOtvorena) 
				susednoOtvoreno.brSusednihNeotvorenih--;
			
			nadjiResenjaRekurzija(i + 1, n);
			
			for (PoljeRes susednoOtvoreno: trPolje.susednaPoljaOtvorena) 
				susednoOtvoreno.brSusednihNeotvorenih++;
		}
		
		if (mozeMina(trPolje)) {
			for (PoljeRes susednoOtvoreno: trPolje.susednaPoljaOtvorena) { 
				susednoOtvoreno.brSusednihNeotvorenih--;
				susednoOtvoreno.razlika--;
			}
			
			trPolje.minaObelezeno = true;
			nadjiResenjaRekurzija(i + 1, n);
			trPolje.minaObelezeno = false;
			
			for (PoljeRes susednoOtvoreno: trPolje.susednaPoljaOtvorena) { 
				susednoOtvoreno.brSusednihNeotvorenih++;
				susednoOtvoreno.razlika++;
			}
		}
	}
	
	private static boolean mozePrazno(PoljeRes trPolje) {
		for (PoljeRes susednoOtvoreno: trPolje.susednaPoljaOtvorena) 
			if (susednoOtvoreno.brSusednihNeotvorenih == susednoOtvoreno.razlika)
				return false;
		return true;
	}
	
	private static boolean mozeMina(PoljeRes trPolje) {
		for (PoljeRes susednoOtvoreno: trPolje.susednaPoljaOtvorena) 
			if (susednoOtvoreno.razlika - 1 < 0)
				return false;
		return true;
	}
	
	private static void nadjiNeotvorenaPolja(PoljeRes[][] polja) {
		neotvorenaPolja = new Vector<PoljeRes>();
		
		for (PoljeRes[] redPolja: polja) 
			for (PoljeRes polje: redPolja) 
				if (!polje.otvoreno && !polje.minaObelezeno)
					for (PoljeRes susednoPolje: polje.susednaPolja) 
						if (susednoPolje.otvoreno) {
							neotvorenaPolja.add(polje);
							break;
						}
	}
	
	private static void nadjiOtvorenaPolja(PoljeRes[][] polja) {
		otvorenaPolja = new Vector<PoljeRes>();
		
		for (PoljeRes[] redPolja: polja) 
			for (PoljeRes polje: redPolja) 
				if (polje.otvoreno)
					for (PoljeRes susednoPolje: polje.susednaPolja) 
						if (!susednoPolje.otvoreno && !susednoPolje.minaObelezeno) {
							otvorenaPolja.add(polje);
							break;
						}
	}
	
	private static Vector<PoljeRes> nadjiMarginalnaPolja(PoljeRes[][] polja) {
		marginalnaPolja = new Vector<PoljeRes>();
		
		for (PoljeRes[] redPolja: polja) 
			for (PoljeRes polje: redPolja) 
				if (!polje.otvoreno && !polje.minaObelezeno) {
					boolean flagTmp = true;
					for (PoljeRes susednoPolje: polje.susednaPolja) 
						if (susednoPolje.otvoreno) {
							flagTmp = false;
							break;
						}
					if (flagTmp)
						marginalnaPolja.add(polje);
				}
		return marginalnaPolja;
	}
	
	private static void susednaPoljaInit(int m, int n, PoljeRes[][] polja) {
		for (PoljeRes[] redPolja: polja) 
			for (PoljeRes polje: redPolja) 
				polje.susednaPoljaInit(m, n, polja);
	}
	
	private static double min(double a, double b) {
		if (a < b) 
			return a;
		return b;
	}
}
