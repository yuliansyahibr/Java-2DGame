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

import game.model.user.Scores;

public class ScoresWindow extends JFrame{
	
	private JTable table;
	private DefaultTableModel dtm;
	private JPanel panel;
	
	private JLabel lblUsername;
	private JTextField txtUsername;
	
	private JButton btnMain;
	private JButton btnKeluar;
	
	private JScrollPane scroll;
	
	String tHead[] = new String[] {"No", "Username", "Score"};
	
	// model untuk skor
	private Scores scores;
	
	private static String username; 
	private int newScore;
	
	public static boolean insert=false;
	public static boolean update=false;
	
	public static boolean STATE[] = new boolean[2]; 
	
//	private boolean inGame;
	
	public ScoresWindow() {
		
		super("Tabel Skor");
		
		init();
		
//		inGame = false;
		insert=false;
		update=false;
		
		loadScores();
		
		this.setVisible(true);
		
	}
	
	public ScoresWindow(int skor) {
		
		super("Tabel Skor");
		
		init();
		
		newScore = skor;
		
		if(insert) {
			scores.insert(username, newScore);
		}else if(update) {
			scores.update(username, newScore);
		}
		
//		table.setModel(dtm);
//		table.getColumnModel().getColumn(0).setPreferredWidth(4);
//		table.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
//		dtm.setRowCount(0);
		
		loadScores();
		
		this.setVisible(true);
	}
	
	
	private void init() {
		
		scores = new Scores();
		
		table = new JTable();
		scroll = new JScrollPane();
		dtm = new DefaultTableModel(tHead, 0);
		
		lblUsername = new JLabel("Username");
		txtUsername = new JTextField(18);
		
		btnMain = new JButton("Main");
		btnKeluar = new JButton("Keluar");
		
		panel = new JPanel(new BorderLayout());
		
		this.getContentPane().setLayout(new BorderLayout());
		
		this.setSize(new Dimension(600, 500));
				
		fillCenterPanel();
		
		JPanel panel4 = new JPanel();
		panel4.setBackground(Color.green);		
//		this.add(panel4, BorderLayout.WEST);
		
		panel.setBorder(new EmptyBorder(0, 0, 0, 0));
		this.add(panel, BorderLayout.CENTER);
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
//		this.pack();
		this.setLocationRelativeTo(null);
		
		//align table ke tengah
		DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
		rightRenderer.setHorizontalAlignment( JLabel.RIGHT );
//		table.setDefaultRenderer(String.class, centerRenderer);
		//
		table.setModel(dtm);
		table.setGridColor(Color.LIGHT_GRAY);
		table.setRowHeight(25);
		
		table.getColumnModel().getColumn(0).setPreferredWidth(20);
		table.getColumnModel().getColumn(1).setPreferredWidth(200);
		table.getColumnModel().getColumn(2).setPreferredWidth(80);
		table.getColumnModel().getColumn(2).setCellRenderer( rightRenderer );
		
//		table.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
		dtm.setRowCount(0);
	}
	
	private void fillCenterPanel() {
		
		JPanel cPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));

		cPanel.setBorder(BorderFactory.createEmptyBorder(60, 10, 0, 10));
		cPanel.setPreferredSize(new Dimension(500, 400));
//		cPanel.setBackground(Color.MAGENTA);
		
		JPanel nPanel = new JPanel(new GridBagLayout());
//		nPanel.setBackground(Color.CYAN);
		nPanel.setPreferredSize(new Dimension(500, 100));
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(0, 0, 0, 20);
		c.gridx = 0;
		c.gridy = 1;
		nPanel.add(lblUsername, c);
		c.gridx ++;
		c.ipady = 10;
//		c.weightx = 0.0;
//		c.gridwidth=19;
		nPanel.add(txtUsername, c);
//		nPanel.add(lblUsername);
//		nPanel.add(txtUsername);
		nPanel.setBorder(new EmptyBorder(0, 0, 0, 0));
		
		
		JPanel sPanel = new JPanel(new GridBagLayout());
//		sPanel.setBackground(Color.CYAN);
		sPanel.setPreferredSize(new Dimension(500, 80));
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
		cPanel.add(getPanel());
		cPanel.add(sPanel);
		
		panel.add(cPanel, BorderLayout.CENTER);
		
	}
	
	private JPanel getPanel() {
		
		JPanel panel = new JPanel(new BorderLayout());
		panel.setBackground(Color.YELLOW);
		panel.setPreferredSize(new Dimension(500, 200));
		
//		table = new JTable();
//		table.setSize(200, 100);
		
////		table.setGridColor(Color.WHITE);
//		table.setRowHeight(30);
//		table.setPreferredScrollableViewportSize(new Dimension(200, 100));
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
		
//		panel.add(txtUsername, BorderLayout.NORTH);
		panel.add(scroll);
		
		return panel;
	}
	
	private void loadScores() {
		
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
	
	public void setInsert() {
		insert = true;
		update=false;
	}
	
	public void setUpdate() {
		update=true;
		insert=false;
	}
	
	private void runGame() {
		
		username = txtUsername.getText();
	
		if(username.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Username tidak boleh kosong", "Error", JOptionPane.WARNING_MESSAGE);
			return;
		}
		
		if(scores.findUsername(username)) setUpdate();
		else setInsert();
		
		Game.runGame();
		this.dispose();
	}
	
	private void keluar() {
		this.dispose();
	}
	
}
