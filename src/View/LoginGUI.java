package View;

import java.awt.EventQueue;
import Helper.*;
import Model.*;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;

public class LoginGUI extends JFrame {
	private JPanel w_pane;
	private JTextField fld_hastaTc;
	private JPasswordField fld_hastaPass;
	private DBConnection conn = new DBConnection();
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginGUI frame = new LoginGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public LoginGUI() {
		setResizable(false);
		setTitle("Hastane Sistemi");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 509, 420);
		w_pane = new JPanel();
		w_pane.setBackground(new Color(204, 204, 255));
		w_pane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(w_pane);
		w_pane.setLayout(null);


		JLabel lblHastaneYnetimSistemine = new JLabel("Randevu Sistemine Hoşgeldiniz");
		lblHastaneYnetimSistemine.setFont(new Font("Verdana", Font.BOLD, 17));
		lblHastaneYnetimSistemine.setBounds(49, 132, 380, 22);
		w_pane.add(lblHastaneYnetimSistemine);

		JTabbedPane w_tabpane = new JTabbedPane(JTabbedPane.TOP);
		w_tabpane.setBounds(10, 165, 474, 195);
		w_pane.add(w_tabpane);

		JPanel w_hastaLogin = new JPanel();
		w_hastaLogin.setBackground(new Color(153, 102, 255));
		w_tabpane.addTab("Hasta E-Devlet Girişi", null, w_hastaLogin, null);
		w_hastaLogin.setLayout(null);

		JLabel lblTcGirisi = new JLabel("TC Numaranız:");
		lblTcGirisi.setFont(new Font("Verdana", Font.BOLD, 17));
		lblTcGirisi.setBounds(61, 21, 168, 22);
		w_hastaLogin.add(lblTcGirisi);

		JLabel lblSifre = new JLabel("Şifre:");
		lblSifre.setFont(new Font("Verdana", Font.BOLD, 17));
		lblSifre.setBounds(61, 54, 168, 20);
		w_hastaLogin.add(lblSifre);

		fld_hastaTc = new JTextField();
		fld_hastaTc.setFont(new Font("Tahoma", Font.PLAIN, 17));
		fld_hastaTc.setBounds(239, 21, 195, 20);
		w_hastaLogin.add(fld_hastaTc);
		fld_hastaTc.setColumns(10);
		JButton btn_hastaLogin = new JButton("Giriş Yap");
		btn_hastaLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (fld_hastaTc.getText().length() == 0 || fld_hastaPass.getText().length() == 0) {
					Helper.showMsg("fill");
				} else {
					boolean key = true;

					try {
						Connection con = conn.connDb();
						Statement st = con.createStatement();
						ResultSet rs = st.executeQuery("SELECT *FROM user");
						while (rs.next()) {
							if (fld_hastaTc.getText().equals(rs.getString("tcno"))
									&& fld_hastaPass.getText().equalsIgnoreCase(rs.getString("pasword"))) {
								if (rs.getString("type").equals("hasta")) {
									Hasta hasta = new Hasta();
									hasta.setId(rs.getInt("id"));
									hasta.setPasword("pasword");
									hasta.setTcno(rs.getString("tcno"));
									hasta.setName(rs.getString("name"));
									hasta.setType(rs.getString("type"));
									HastaGUI hGUI = new HastaGUI(hasta);
									hGUI.setVisible(true);
									dispose();
									key = false;
								}
							}
						}
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					if (key)
						Helper.showMsg("Böyle bir hasta bulunamadı lütfen kayıt olunuz.");

				}
			}
		});
		btn_hastaLogin.setFont(new Font("Tahoma", Font.BOLD, 14));
		btn_hastaLogin.setBounds(239, 102, 220, 54);
		w_hastaLogin.add(btn_hastaLogin);

		fld_hastaPass = new JPasswordField();
		fld_hastaPass.setBounds(239, 57, 195, 20);
		w_hastaLogin.add(fld_hastaPass);}
	}