package Glavni;

import Resavac.*;
import java.util.Vector;
import java.awt.event.*;
import javax.swing.Timer;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.Point;
import java.awt.Robot;
import java.awt.SystemColor;

public class GlavniProzor {

	JFrame frame;
	int pozX;
	int pozY;
	Polje[][] polja;
	Integer m; 
	Integer n; 
	Integer brMina;
	Integer brNeobelezenihMina;
	boolean prviKlik;
	
	private JLabel cifra1, cifra2, cifra3;
	JLabel dugmeSmajli;
	
	Timer timer;
	int prosloVreme;
	JLabel timerCifra1, timerCifra2, timerCifra3;
	
	boolean gameWon;
	boolean gameLost;
	
	boolean autoSolve = false;
	int delay;
	
	/**
	 * Create the application.
	 */
	public GlavniProzor(int m, int n, int brMina, int pozX, int pozY) {
		this.m = m;
		this.n = n;
		this.brMina = brMina;
		this.brNeobelezenihMina = brMina;
		this.pozX = pozX;
		this.pozY = pozY;
		this.prviKlik = true;
		this.gameWon = false;
		this.gameLost = false;
		this.delay = 60;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(SystemColor.controlHighlight);
		frame.setBounds(pozX, pozY, Polje.sirina * n + 15, Polje.visina * m + 99);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setResizable(false);
		frame.setVisible(true);
		
		dugmeSmajli = new JLabel("");
		dugmeSmajli.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				dugmeSmajli.setIcon(new ImageIcon(PocetniProzor.class.getResource("/Slike/dugmeResetPressed.png")));
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				dugmeSmajli.setIcon(new ImageIcon(PocetniProzor.class.getResource("/Slike/dugmeReset.png")));
			}
			@Override 
			public void mouseClicked(MouseEvent e) {
				if (SwingUtilities.isRightMouseButton(e)) {
					ukljuciResavac();
					autoSolve = true;
				}
				else if (SwingUtilities.isMiddleMouseButton(e)) {
					ukljuciResavac();
					autoSolve = false;
				}
				else {
					frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
					GlavniProzor gp = new GlavniProzor(m, n, brMina, frame.getX(), frame.getY());
				}
			}
		});
		dugmeSmajli.setBounds(frame.getWidth() / 2 - 28, 10, 40, 40);
		dugmeSmajli.setIcon(new ImageIcon(PocetniProzor.class.getResource("/Slike/dugmeReset.png")));
		frame.getContentPane().add(dugmeSmajli);
		
		cifra1 = new JLabel("");
		cifra1.setBounds(0, 10, 27, 40);
		frame.getContentPane().add(cifra1);
		
		cifra2 = new JLabel("");
		cifra2.setBounds(27, 10, 27, 40);
		frame.getContentPane().add(cifra2);
		
		cifra3 = new JLabel("");
		cifra3.setBounds(27 * 2, 10, 27, 40);
		frame.getContentPane().add(cifra3);
		
		ispisiBrojNeobelezenihMina();
		
		timerCifra1 = new JLabel("");
		timerCifra1.setBounds(frame.getWidth() - 3 * 27 - 15, 10, 27, 40);
		frame.getContentPane().add(timerCifra1);
		
		timerCifra2 = new JLabel("");
		timerCifra2.setBounds(frame.getWidth() - 2 * 27 - 15, 10, 27, 40);
		frame.getContentPane().add(timerCifra2);
		
		timerCifra3 = new JLabel("");
		timerCifra3.setBounds(frame.getWidth() - 1 * 27 - 15, 10, 27, 40);
		frame.getContentPane().add(timerCifra3);
		
		ispisiProtekloVreme();
		
		//inicijalizacija tajmera
		timer = new Timer(1000, taskPerformer);
		timer.setRepeats(true);
		timer.setInitialDelay(0);
		
		//inicijalizacija polja
		polja = new Polje[m][n];
		
		for (int i = 0; i < m; i++)
			for (int j = 0; j < n; j++) 
				polja[i][j] = new Polje(i, j, this);
	}
	
	void ukljuciResavac() {
		PoljeRes[][] poljaRes = new PoljeRes[m][n];
		for (int i = 0; i < m; i++)
			for (int j = 0; j < n; j++)
				poljaRes[i][j] = polja[i][j].toPoljeRes();
		
		PoljeRes resenje = Resavac.nadjiResenja(m, n, poljaRes, brNeobelezenihMina, gameWon, gameLost, prviKlik);
		System.out.println(resenje);
		System.out.println();
		
		try {
			autoClicker(resenje);
		}
		catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Дошло је до грешке.", "Грешка извршавања", JOptionPane.INFORMATION_MESSAGE);
		}
	}
	
	void ispisiProtekloVreme() {
		ispisiCifru(timerCifra1, this.prosloVreme / 100);
		ispisiCifru(timerCifra2, (this.prosloVreme % 100) / 10);
		ispisiCifru(timerCifra3, this.prosloVreme % 10);
	}
	
	void ispisiBrojNeobelezenihMina() {
		ispisiCifru(cifra1, this.brNeobelezenihMina / 100);
		ispisiCifru(cifra2, (this.brNeobelezenihMina % 100) / 10);
		ispisiCifru(cifra3, this.brNeobelezenihMina % 10);
	}
	
	private void ispisiCifru(JLabel labela, int br) {
		switch (br) {
		case 0:
			labela.setIcon(new ImageIcon(PocetniProzor.class.getResource("/Slike/broj0.png")));
			break;
		case 1:
			labela.setIcon(new ImageIcon(PocetniProzor.class.getResource("/Slike/broj1.png")));
			break;
		case 2:
			labela.setIcon(new ImageIcon(PocetniProzor.class.getResource("/Slike/broj2.png")));
			break;
		case 3:
			labela.setIcon(new ImageIcon(PocetniProzor.class.getResource("/Slike/broj3.png")));
			break;
		case 4:
			labela.setIcon(new ImageIcon(PocetniProzor.class.getResource("/Slike/broj4.png")));
			break;
		case 5:
			labela.setIcon(new ImageIcon(PocetniProzor.class.getResource("/Slike/broj5.png")));
			break;
		case 6:
			labela.setIcon(new ImageIcon(PocetniProzor.class.getResource("/Slike/broj6.png")));
			break;
		case 7:
			labela.setIcon(new ImageIcon(PocetniProzor.class.getResource("/Slike/broj7.png")));
			break;
		case 8:
			labela.setIcon(new ImageIcon(PocetniProzor.class.getResource("/Slike/broj8.png")));
			break;
		case 9:
			labela.setIcon(new ImageIcon(PocetniProzor.class.getResource("/Slike/broj9.png")));
			break;
		}
	}
	
	private ActionListener taskPerformer = new ActionListener() {

        public void actionPerformed(ActionEvent evt) {
            ispisiProtekloVreme();
            prosloVreme++;
            
            if (prosloVreme >= 1000) 
            	timer.stop();
        }
    };
    
    private void autoClicker(PoljeRes resenje) throws Exception {
    	//ako nema resenja prekini izvrsavanje
    	if (resenje == null) {
    		autoSolve = false;
    		return;
    	}
    	
    	//polje na koje treba da se klikne
    	Polje klikniNa = polja[resenje.i][resenje.j];
    	
    	//levi, srednji ili desni klik
    	int button = InputEvent.BUTTON1_DOWN_MASK;
    	if (resenje.klik == 2)
    		button = InputEvent.BUTTON3_DOWN_MASK;
    	else if (resenje.klik == 3)
    		button = InputEvent.BUTTON2_DOWN_MASK;
    		
    	//klikni na dugme
    	Robot r = new Robot();
    	Point p = SwingUtilities.convertPoint(klikniNa.label, 0, 0, frame);
    	r.mouseMove(p.x + frame.getX() + 10, p.y + frame.getY() + 10);
    	r.mousePress(button);
    	r.mouseRelease(button);
    	
    	//pauza
		Thread.sleep(delay);
    }
}
