package Glavni;

import Resavac.PoljeRes;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.JFrame;
import java.util.Vector;
import java.util.Random;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Polje {
	JLabel label;
	private int i, j;
	private boolean mina;
	private Polje[][] polja;
	private Vector<Polje> susednaPolja;
	private boolean otvoreno;
	private int brSusednihMina;
	private boolean minaObelezeno;
	private boolean nijeMina;
	private GlavniProzor gp;
	
	private static int brPoljaInit;
	private static int brMinaInit;
	
	static final int visina = 20;
	static final int sirina = 20;
	
	Polje(int i, int j, GlavniProzor gp) {
		this.gp = gp;
		this.i = i;
		this.j = j;
		this.polja = gp.polja;
		dugmeInit(gp.frame);
		otvoreno = false;
		minaObelezeno = false;
		mina = false;
		brSusednihMina = 0;
		nijeMina = false;
	}
	
	private void prviKlik() {
		//inicijalizuj susedna polja
		for (Polje[] redPolja: polja)
			for (Polje polje: redPolja) 
				polje.susednaPoljaInit();
		
		//polje na koje je prvo kliknuto i sva susedna nisu mine
		nijeMina = true;
		for (Polje polje: susednaPolja)
			polje.nijeMina = true;
		
		//postavi mine na ostalim poljima
		Polje.brPoljaInit = gp.n * gp.m - 1 - susednaPolja.size();
		Polje.brMinaInit = gp.brMina;
		
		for (Polje[] redPolja: polja)
			for (Polje polje: redPolja)
				polje.mina = polje.minaInit();
		
		//postavi brSusednihMinai u zavisnosti od mina
		for (Polje[] redPolja: polja)
			for (Polje polje: redPolja)
				polje.brSusednihMinaInit();
		
		//pokreni timer
		gp.timer.start();
	}
	
	private void poljeKliknuto() {
		if (otvoreno || minaObelezeno) return;
		
		if (gp.prviKlik) {
			gp.prviKlik = false;
			prviKlik();
		}
		
		if (mina) {
			gameOver();
			obeleziMinu();
		}
		else {
			obeleziPolje();
			gameWon();
		}
	}
	
	private void poljeKliknutoDesniKlik() {
		if (otvoreno || (!minaObelezeno && gp.brNeobelezenihMina == 0)) return;
		
		if (minaObelezeno) { 
			gp.brNeobelezenihMina++;
			label.setIcon(new ImageIcon(PocetniProzor.class.getResource("/Slike/polje.png")));
		}
		else { 
			gp.brNeobelezenihMina--;
			label.setIcon(new ImageIcon(PocetniProzor.class.getResource("/Slike/poljeObelezeno.png")));
		}
		
		minaObelezeno = !minaObelezeno;
		
		gp.ispisiBrojNeobelezenihMina();
	}
	
	private void poljeKliknutoSrednjiKlik() {
		if (!otvoreno || minaObelezeno) return;
		
		int brSusednihMinaTemp = 0;
		for (Polje polje: susednaPolja)
			if (polje.minaObelezeno) brSusednihMinaTemp++;
		
		if (brSusednihMina != brSusednihMinaTemp) return;
		
		for (Polje polje: susednaPolja) 
			polje.poljeKliknuto();
	}
	
	private void dugmeInit(JFrame frame) {
		label = new JLabel();
		label.setIcon(new ImageIcon(PocetniProzor.class.getResource("/Slike/polje.png")));
		label.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (SwingUtilities.isRightMouseButton(e)) 
					poljeKliknutoDesniKlik();
				else if (SwingUtilities.isMiddleMouseButton(e))
					poljeKliknutoSrednjiKlik();
				else 
					poljeKliknuto();
				
				if (gp.autoSolve)
					gp.ukljuciResavac();
			}
			@Override
			public void mousePressed(MouseEvent e) {
				if (otvoreno) return;
				label.setIcon(new ImageIcon(PocetniProzor.class.getResource("/Slike/poljePrazno.png")));
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				if (otvoreno) return;
				if (minaObelezeno)
					label.setIcon(new ImageIcon(PocetniProzor.class.getResource("/Slike/poljeObelezeno.png")));
				else
					label.setIcon(new ImageIcon(PocetniProzor.class.getResource("/Slike/polje.png")));
			}
		});
		label.setBounds(j * sirina, i * visina + 60, sirina, visina);
		frame.getContentPane().add(label);
	}
	
	private void brSusednihMinaInit() {
		if (mina) return;
		
		for (Polje polje: susednaPolja) 
			if (polje.mina)
				brSusednihMina++;
	}
	
	private boolean minaInit() {
		if (nijeMina) 
			return false;
		
		Random rand = new Random();
		if (rand.nextDouble() <= (1.0 * Polje.brMinaInit) / Polje.brPoljaInit--) {
			Polje.brMinaInit--;
			return true;
		}
		return false;
	}
	
	private void susednaPoljaInit() { 
		susednaPolja = new Vector<Polje>();
		
		int[] i_vals = {i-1, i, i+1};
		int[] j_vals = {j-1, j, j+1};
	
		for (int i_val: i_vals)
			for (int j_val: j_vals) 
				if (i_val >= 0 && i_val < gp.m && j_val >=0 && j_val < gp.n && (i_val != i || j_val != j))
					susednaPolja.add(polja[i_val][j_val]);
	}
	
	private void obeleziPolje() {
		otvoreno = true;
		switch (brSusednihMina) {
		case 0:
			label.setIcon(new ImageIcon(PocetniProzor.class.getResource("/Slike/poljePrazno.png")));
			for (Polje polje: susednaPolja) 
				polje.poljeKliknuto();
			break;
		case 1:
			label.setIcon(new ImageIcon(PocetniProzor.class.getResource("/Slike/polje1.png")));
			break;
		case 2:
			label.setIcon(new ImageIcon(PocetniProzor.class.getResource("/Slike/polje2.png")));
			break;
		case 3:
			label.setIcon(new ImageIcon(PocetniProzor.class.getResource("/Slike/polje3.png")));
			break;
		case 4:
			label.setIcon(new ImageIcon(PocetniProzor.class.getResource("/Slike/polje4.png")));
			break;
		case 5:
			label.setIcon(new ImageIcon(PocetniProzor.class.getResource("/Slike/polje5.png")));
			break;
		case 6:
			label.setIcon(new ImageIcon(PocetniProzor.class.getResource("/Slike/polje6.png")));
			break;
		case 7:
			label.setIcon(new ImageIcon(PocetniProzor.class.getResource("/Slike/polje7.png")));
			break;
		case 8:
			label.setIcon(new ImageIcon(PocetniProzor.class.getResource("/Slike/polje8.png")));
			break;
		}
	}
	
	private void obeleziMinu() {
		label.setIcon(new ImageIcon(PocetniProzor.class.getResource("/Slike/poljeGreska.png")));
		minaObelezeno = false;
	}
	
	private void gameWon() {
		int brOtvorenihPolja = 0;
		for (Polje[] redPolja: polja)
			for (Polje polje: redPolja)
				if (polje.otvoreno)
					brOtvorenihPolja++;
		
		if (brOtvorenihPolja != gp.m * gp.n - gp.brMina) return;
		
		for (Polje[] redPolja: polja)
			for (Polje polje: redPolja) {
				if (!polje.minaObelezeno && polje.mina) 
					polje.label.setIcon(new ImageIcon(PocetniProzor.class.getResource("/Slike/poljeObelezeno.png")));
				polje.otvoreno = true;
			}
		gp.dugmeSmajli.setIcon(new ImageIcon(PocetniProzor.class.getResource("/Slike/dugmeResetWon.png")));
		
		gp.brNeobelezenihMina = 0;
		gp.ispisiBrojNeobelezenihMina();
		gp.timer.stop();
		gp.gameWon = true;
	}
	
	private void gameOver() {
		for (Polje[] redPolja: polja)
			for (Polje polje: redPolja) {
				if (!polje.minaObelezeno && polje.mina) 
					polje.label.setIcon(new ImageIcon(PocetniProzor.class.getResource("/Slike/poljeMina.png")));
				else if (polje.minaObelezeno && !polje.mina)
					polje.label.setIcon(new ImageIcon(PocetniProzor.class.getResource("/Slike/poljePogresnoObelezeno.png")));
				polje.otvoreno = true;
			}
		gp.dugmeSmajli.setIcon(new ImageIcon(PocetniProzor.class.getResource("/Slike/dugmeResetLost.png")));
		
		gp.timer.stop();
		gp.gameLost = true;
	}
	
	public PoljeRes toPoljeRes() {
		return new PoljeRes(i, j, otvoreno, minaObelezeno, brSusednihMina);
	}

}
