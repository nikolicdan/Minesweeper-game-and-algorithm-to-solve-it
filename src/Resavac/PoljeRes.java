package Resavac;

import java.util.Vector;

public class PoljeRes {
	public int i, j;
	boolean otvoreno;
	boolean minaObelezeno;
	int brSusednihMina;
	
	PoljeRes[][] polja;
	Vector <PoljeRes> susednaPolja;
	Vector <PoljeRes> susednaPoljaOtvorena;
	Vector <PoljeRes> susednaPoljaNeotvorena;
	
	int brPutaObelezeno;
	public int klik;
	double verovatnoca;
	
	int brSusednihNeotvorenih;
	int razlika;
	
	public PoljeRes(int i, int j, boolean otvoreno, boolean minaObelezeno, int brSusednihMina) {
		this.i = i;
		this.j = j;
		this.otvoreno = otvoreno;
		this.minaObelezeno = minaObelezeno;
		this.brSusednihMina = brSusednihMina;
		this.brPutaObelezeno = 0;
		this.klik = 1;
		this.verovatnoca = 300;
	}
	
	void susednaPoljaInit(int m, int n, PoljeRes[][] polja) {
		this.polja = polja;
		susednaPolja = new Vector<PoljeRes>();
		susednaPoljaOtvorena = new Vector<PoljeRes>();
		susednaPoljaNeotvorena = new Vector<PoljeRes>();
		brSusednihNeotvorenih = 0;
		
		int[] i_vals = {i-1, i, i+1};
		int[] j_vals = {j-1, j, j+1};
		
		for (int i_val: i_vals)
			for (int j_val: j_vals) 
				if (i_val >= 0 && i_val < m && j_val >=0 && j_val < n && (i_val != i || j_val != j)) {
					susednaPolja.add(polja[i_val][j_val]);
					
					if (polja[i_val][j_val].otvoreno)
						susednaPoljaOtvorena.add(polja[i_val][j_val]);
					
					if (!polja[i_val][j_val].otvoreno && !polja[i_val][j_val].minaObelezeno) {
						susednaPoljaNeotvorena.add(polja[i_val][j_val]);
						brSusednihNeotvorenih++;
					}
				}
	}
	
	void odrediVerovatnocu(int brResenja) {
		verovatnoca = (1.0 * brPutaObelezeno) / (1.0 * brResenja);
	}
	
	@Override
	public String toString() {
		return "(" + i + ", " + j + ") " + klik + " " +  verovatnoca;
	}
}
