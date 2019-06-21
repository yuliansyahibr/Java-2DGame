package game.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import game.model.object.Player;
import game.model.user.Scores;

public class ScoresWindow extends JFrame{
	
	// komponen window
	private JTable table;
	private DefaultTableModel dtm;
	private JPanel panel;	
	private JLabel lblUsername;
	private JTextField txtUsername;	
	private JButton btnMain;
	private JButton btnKeluar;	
	private JScrollPane scroll;
	
	// table header 
	String tHead[] = new String[] {"No", "Username", "Score"};
	
	// model untuk skor
	private Scores scores;
	
	//username
	private static String username;
	
	public ScoresWindow() {
		
		super("Tabel Skor");
		
		init();
		// load table skor
		loadScores();
		// tampilkan window
		this.setVisible(true);
		
	}
		
	private void init() {
		
		// instansiasi
		scores = new Scores();		
		table = new JTable();
		scroll = new JScrollPane();
		dtm = new DefaultTableModel(tHead, 0);		
		lblUsername = new JLabel("Username");
		txtUsername = new JTextField(18);		
		btnMain = new JButton("Main");
		btnKeluar = new JButton("Keluar");		
		panel = new JPanel(new BorderLayout());
		
		// set layout window ke borderlayout
		this.getContentPane().setLayout(new BorderLayout());
		// set window size
		this.setSize(new Dimension(500, 500));
				
		// isi panel
		fillPanel();		
		panel.setBorder(new EmptyBorder(0, 0, 0, 0));
		// add panel ke window
		this.add(panel, BorderLayout.CENTER);
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		
		//align colom skor ke kanan
		DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
		rightRenderer.setHorizontalAlignment( JLabel.RIGHT );
		table.setModel(dtm);
		table.setGridColor(Color.LIGHT_GRAY);
		table.setRowHeight(25);
		// set column width
		table.getColumnModel().getColumn(0).setPreferredWidth(20);
		table.getColumnModel().getColumn(1).setPreferredWidth(200);
		table.getColumnModel().getColumn(2).setPreferredWidth(80);
		table.getColumnModel().getColumn(2).setCellRenderer( rightRenderer );
		table.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
		dtm.setRowCount(0);
	}
	
	private void fillPanel() {		
		
		JPanel cPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
		cPanel.setBorder(BorderFactory.createEmptyBorder(60, 10, 0, 10));
		cPanel.setPreferredSize(new Dimension(450, 300));
		
		// panel yg menampung label dan input username
		JPanel nPanel = new JPanel(new GridBagLayout());
		nPanel.setPreferredSize(new Dimension(400, 100));
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(0, 0, 0, 20);
		c.gridx = 0;
		c.gridy = 1;
		nPanel.add(lblUsername, c);
		c.gridx ++;
		c.ipady = 10;
		nPanel.add(txtUsername, c);
		nPanel.setBorder(new EmptyBorder(0, 0, 0, 0));
		
		// panel untuk button
		JPanel sPanel = new JPanel(new GridBagLayout());
		sPanel.setPreferredSize(new Dimension(400, 80));
		GridBagConstraints c2 = new GridBagConstraints();
		c2.insets = new Insets(0, 20, 0, 20);
		c2.gridx = 0;
		c2.gridy = 1;
		c2.ipadx = 50;
		c2.ipady = 8;
		sPanel.add(btnMain, c2);
		c2.insets = new Insets(0, 20, 0, 20);
		c2.gridx ++;
		c2.ipadx = 50;
		c2.ipady = 8;
		sPanel.add(btnKeluar, c2);
		
		// action listener
		btnKeluar.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				keluar();
			}
		});		
		btnMain.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {				
				runGame();
			}
		});
		
		cPanel.add(nPanel);
		cPanel.add(getTablePanel());
		cPanel.add(sPanel);		
		panel.add(cPanel, BorderLayout.CENTER);
		
	}
	
	
	private JPanel getTablePanel() {
		// panel untuk table
		JPanel panel = new JPanel(new BorderLayout());
		panel.setBackground(Color.LIGHT_GRAY);
		panel.setPreferredSize(new Dimension(400, 200));
		table.setModel(
				new DefaultTableModel(
						new Object[][] { 
							{ null, null, null }, 
							{ null, null, null },
							{ null, null, null },
							{ null, null, null },
							{ null, null, null }
						},
						tHead
				)
		);
		scroll.setViewportView(table);
		panel.add(scroll);
		return panel;
	}
	
	private void loadScores() {
		// get users scores
		try {
			java.sql.ResultSet res = scores.getAll();
			int i=1;
			while(res.next()) {
				dtm.addRow(new Object[] {
						i ,
						res.getString(1), res.getInt(2)
				});
				i++;
			}
			table.setModel(dtm);
		} catch (Exception e) {
			
		}
	}
	
	// run game
	private void runGame() {
		
		username = txtUsername.getText();
	
		// jika username kosong
		if(username.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Username tidak boleh kosong", "Error", JOptionPane.WARNING_MESSAGE);
			return;
		}
		
		// jika username belum ada, insert dengan skor 0
		if(!scores.findUsername(username)) scores.insert(username, 0);
		
		// set username
		Player.setUsername(username);
		// jalankan window game
		new GameWindow();
		// hilankang window skor
		this.dispose();
	}
	
	// keluar aplikasi
	private void keluar() {
		System.exit(0);		
	}
	
}
