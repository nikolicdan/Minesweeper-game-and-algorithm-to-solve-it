package Glavni;

import java.awt.EventQueue;

import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JOptionPane;
import java.awt.Button;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Panel;
import java.awt.Font;
import javax.swing.JRadioButton;

public class PocetniProzor {

	private JFrame frame;
	private JTextField unosBrPoljaM;
	private JTextField unosBrPoljaN;
	private JTextField unosBrMina;
	
	private JLabel lblNewLabel;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PocetniProzor window = new PocetniProzor();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public PocetniProzor() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 389, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.getContentPane().setLayout(null);
		
		unosBrPoljaM = new JTextField();
		unosBrPoljaM.setBounds(163, 180, 46, 20);
		frame.getContentPane().add(unosBrPoljaM);
		unosBrPoljaM.setColumns(10);
		
		unosBrPoljaN = new JTextField();
		unosBrPoljaN.setBounds(231, 180, 46, 20);
		frame.getContentPane().add(unosBrPoljaN);
		unosBrPoljaN.setColumns(10);
		
		unosBrMina = new JTextField();
		unosBrMina.setBounds(299, 180, 46, 20);
		frame.getContentPane().add(unosBrMina);
		unosBrMina.setColumns(10);
		
		Panel panel = new Panel();
		panel.setBackground(new Color(0, 102, 255));
		panel.setBounds(0, 0, 383, 39);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		lblNewLabel = new JLabel("Миноловац");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblNewLabel.setForeground(new Color(255, 255, 255));
		lblNewLabel.setBounds(10, 0, 150, 39);
		panel.add(lblNewLabel);
		
		Panel panel_1 = new Panel();
		panel_1.setBackground(new Color(221, 221, 221));
		panel_1.setBounds(0, 35, 383, 46);
		frame.getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblNewLabel_2 = new JLabel("Висина");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_2.setBounds(157, 23, 69, 23);
		panel_1.add(lblNewLabel_2);
		
		JLabel lblNewLabel_2_1 = new JLabel("Ширина");
		lblNewLabel_2_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_2_1.setBounds(224, 23, 69, 23);
		panel_1.add(lblNewLabel_2_1);
		
		JLabel lblNewLabel_2_1_1 = new JLabel("Мине");
		lblNewLabel_2_1_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_2_1_1.setBounds(291, 23, 82, 23);
		panel_1.add(lblNewLabel_2_1_1);
		
		JRadioButton radio1 = new JRadioButton("   Лако ");
		radio1.setFont(new Font("Tahoma", Font.BOLD, 17));
		radio1.setBounds(0, 87, 157, 23);
		frame.getContentPane().add(radio1);
		
		JRadioButton radio2 = new JRadioButton("   Средње ");
		radio2.setFont(new Font("Tahoma", Font.BOLD, 17));
		radio2.setBounds(0, 117, 157, 23);
		frame.getContentPane().add(radio2);
		
		JRadioButton radio3 = new JRadioButton("   Тешко");
		radio3.setFont(new Font("Tahoma", Font.BOLD, 17));
		radio3.setBounds(0, 147, 157, 23);
		frame.getContentPane().add(radio3);
		
		JRadioButton radio4 = new JRadioButton("   Унеси сам");
		radio4.setFont(new Font("Tahoma", Font.BOLD, 17));
		radio4.setBounds(0, 177, 157, 23);
		frame.getContentPane().add(radio4);
		
		ButtonGroup group = new ButtonGroup();
	    group.add(radio1);
	    group.add(radio2);
	    group.add(radio3);
	    group.add(radio4);
	    radio1.setSelected(true);
		
		JLabel lblNewLabel_1 = new JLabel("10");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblNewLabel_1.setBounds(163, 87, 23, 20);
		frame.getContentPane().add(lblNewLabel_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("10");
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblNewLabel_1_1.setBounds(231, 87, 23, 20);
		frame.getContentPane().add(lblNewLabel_1_1);
		
		JLabel lblNewLabel_1_2 = new JLabel("10");
		lblNewLabel_1_2.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblNewLabel_1_2.setBounds(299, 87, 23, 20);
		frame.getContentPane().add(lblNewLabel_1_2);
		
		JLabel lblNewLabel_1_3 = new JLabel("16");
		lblNewLabel_1_3.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblNewLabel_1_3.setBounds(163, 117, 23, 20);
		frame.getContentPane().add(lblNewLabel_1_3);
		
		JLabel lblNewLabel_1_1_1 = new JLabel("16");
		lblNewLabel_1_1_1.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblNewLabel_1_1_1.setBounds(231, 117, 23, 20);
		frame.getContentPane().add(lblNewLabel_1_1_1);
		
		JLabel lblNewLabel_1_2_1 = new JLabel("40");
		lblNewLabel_1_2_1.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblNewLabel_1_2_1.setBounds(299, 117, 23, 20);
		frame.getContentPane().add(lblNewLabel_1_2_1);
		
		JLabel lblNewLabel_1_3_1 = new JLabel("16");
		lblNewLabel_1_3_1.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblNewLabel_1_3_1.setBounds(163, 147, 23, 20);
		frame.getContentPane().add(lblNewLabel_1_3_1);
		
		JLabel lblNewLabel_1_1_1_1 = new JLabel("30");
		lblNewLabel_1_1_1_1.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblNewLabel_1_1_1_1.setBounds(231, 147, 23, 20);
		frame.getContentPane().add(lblNewLabel_1_1_1_1);
		
		JLabel lblNewLabel_1_2_1_1 = new JLabel("99");
		lblNewLabel_1_2_1_1.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblNewLabel_1_2_1_1.setBounds(299, 147, 23, 20);
		frame.getContentPane().add(lblNewLabel_1_2_1_1);
		
		Panel panel_1_1 = new Panel();
		panel_1_1.setLayout(null);
		panel_1_1.setBackground(new Color(221, 221, 221));
		panel_1_1.setBounds(0, 215, 383, 56);
		frame.getContentPane().add(panel_1_1);
		
		Button button = new Button("Start");
		button.setFont(new Font("Dialog", Font.BOLD, 18));
		button.setBackground(new Color(0, 102, 255));
		button.setForeground(new Color(255, 255, 255));
		button.setBounds(10, 8, 112, 32);
		panel_1_1.add(button);
		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (radio1.isSelected()) 
					otvoriGlavniProzor(10, 10, 10);
				else if (radio2.isSelected())
					otvoriGlavniProzor(16, 16, 40);
				else if (radio3.isSelected()) 
					otvoriGlavniProzor(16, 30, 99);
				else 
					proveriUnos();
			}
		});
	}
	
	private void otvoriGlavniProzor(int m, int n, int brMina) {
		GlavniProzor gp = new GlavniProzor(m, n, brMina, 100, 100);
	}
	
	private void proveriUnos() {
		try {
			if (unosBrPoljaM.getText().isBlank() || unosBrPoljaN.getText().isBlank() || unosBrMina.getText().isBlank()) 
				throw new Exception("Нисте попунили сва поља!");
			
			int m = Integer.parseInt(unosBrPoljaM.getText());
			int n = Integer.parseInt(unosBrPoljaN.getText());
			int brMina = Integer.parseInt(unosBrMina.getText());
			
			if (!(inRange(m, 10, 30))) 
				throw new Exception("Висина табле мора бити цео број између 10 и 30.");
			if (!(inRange(n, 10, 50))) 
				throw new Exception("Ширина табле мора бити цео број између 10 и 50.");
			if (brMina > 999) 
				throw new Exception("Број мина не сме бити већи од 999.");
			if (!(inRange(brMina, 1, n * m - 9))) 
				throw new Exception("Број мина за унете димензије табле мора бити цео број између 1 и " + (n * m - 9) + ".");
			
			otvoriGlavniProzor(m, n, brMina);
		}
		catch (NumberFormatException err) {
			JOptionPane.showMessageDialog(null, "Унете вредности морају бити цели бројеви.", "Грешка уноса", JOptionPane.INFORMATION_MESSAGE);
		}
		catch (Exception err) {
			JOptionPane.showMessageDialog(null, err.getMessage(), "Грешка уноса", JOptionPane.INFORMATION_MESSAGE);
		}
	}
	
	private boolean inRange(int num, int min, int max) {
		return num >= min && num <= max;
	}
}
