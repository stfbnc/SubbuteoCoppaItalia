/**
 Main class for SubChamp
 */
import classes.GUIcompon;
import classes.ResultsOps;
import classes.SQLiteOps;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;

public class Coppa{
	
	public String imPath = "./images/";
	public String[] A = {"Stefano", "Mamma", "Gabriele"};
	public String[] B = {"Angelo", "Papà", "Michele"};
	public static String db = "coppa.db";
    
    public void CoppaGUI(){
    	
        JFrame main_window = new JFrame("COPPA ITALIA SUBBUTEO");
        main_window.setContentPane(new JLabel(new ImageIcon(imPath + "campo.png")));
        //########################################################################champions logo
        /*JLabel picLabel = new JLabel(new GUIcompon().ResizeImg(imPath, "champions.png", 200, 150));
        picLabel.setBounds(475,5,200,150);
        main_window.add(picLabel);
        //########################################################################champions cup
        picLabel = new JLabel(new GUIcompon().ResizeImg(imPath, "coppa.png", 250, 250));
        picLabel.setBounds(475,160,200,250);
        main_window.add(picLabel);
        //########################################################################roma
        picLabel = new JLabel(new GUIcompon().ResizeImg(imPath, "roma.png", 80, 100));
        picLabel.setBounds(350,20,80,100);
        main_window.add(picLabel);
        JLabel text = new GUIcompon().PlayerName("Gabriele", new Color(255, 150, 0));
        text.setBounds(205,45,120,50);
        main_window.add(text);
        //########################################################################bayern
        picLabel = new JLabel(new GUIcompon().ResizeImg(imPath, "bayern.png", 85, 85));
        picLabel.setBounds(280,160,85,85);
        main_window.add(picLabel);
        text = new GUIcompon().PlayerName("Angelo", new Color(210, 0, 40));
        text.setBounds(150,180,120,50);
        main_window.add(text);
        //########################################################################real
        picLabel = new JLabel(new GUIcompon().ResizeImg(imPath, "real.png", 80, 98));
        picLabel.setBounds(200,270,80,98);
        main_window.add(picLabel);
        text = new GUIcompon().PlayerName("Papà", Color.WHITE);
        text.setBounds(100,312,120,50);
        main_window.add(text);
        //########################################################################cska
        picLabel = new JLabel(new GUIcompon().ResizeImg(imPath, "cska.png", 80, 110));
        picLabel.setBounds(710,20,80,110);
        main_window.add(picLabel);
        text = new GUIcompon().PlayerName("Stefano", Color.YELLOW);
        text.setBounds(830,45,120,50);
        main_window.add(text);
        //########################################################################parissaintgermain
        picLabel = new JLabel(new GUIcompon().ResizeImg(imPath, "parissaintgermain.png", 85, 85));
        picLabel.setBounds(785,160,85,85);
        main_window.add(picLabel);
        text = new GUIcompon().PlayerName("Michele", Color.BLUE);
        text.setBounds(920,180,120,50);
        main_window.add(text);
        //########################################################################manchester
        picLabel = new JLabel(new GUIcompon().ResizeImg(imPath, "manchester.png", 85, 85));
        picLabel.setBounds(872,285,85,85);
        main_window.add(picLabel);
        text = new GUIcompon().PlayerName("Mamma", Color.CYAN);
        text.setBounds(995,312,120,50);
        main_window.add(text);*/
        //########################################################################TABELLA GIRONE A
        /*JLabel lblGironeA = new GUIcompon().LabelContainer("GIRONE A");
        lblGironeA.setBounds(155,440,100,30);
        main_window.add(lblGironeA);
        //Classifica girone A
        JTextPane tableA = new GUIcompon().TableContainer();
        tableA.setBounds(76,465,260,100);
        main_window.add(tableA);
        //new ResultsOps().LeagueTable(tableA);
        //Partite girone A
        JTextPane[] mtchA = new GUIcompon().Matches(76, 565, A);
        for(int i = 0; i < mtchA.length; i++){
        	main_window.add(mtchA[i]);
        }
        JTextField[] resA = new GUIcompon().Results(176, 565);
        for(int i = 0; i < resA.length; i++){
        	main_window.add(resA[i]);
        }*/
        //########################################################################TABELLA GIRONE B
        /*JLabel lblGironeB = new GUIcompon().LabelContainer("GIRONE B");
        lblGironeB.setBounds(895,440,100,30);
        main_window.add(lblGironeB);
        //Classifica girone B
        JTextPane tableB = new GUIcompon().TableContainer();
        tableB.setBounds(814,465,260,100);
        main_window.add(tableB);
        //new ResultsOps().LeagueTable(tableB);
        //Partite girone B
        JTextPane[] mtchB = new GUIcompon().Matches(814, 565, B);
        for(int i = 0; i < mtchB.length; i++){
        	main_window.add(mtchB[i]);
        }
        JTextField[] resB = new GUIcompon().Results(914, 565);
        for(int i = 0; i < resB.length; i++){
        	main_window.add(resB[i]);
        }*/
        //########################################################################SPAREGGI
        JTextField[] resSpareggio1 = new GUIcompon().MatchBox(main_window, 95, 110, "", "", "SPAREGGI");
        JTextField[] resSpareggio2 = new GUIcompon().MatchBox(main_window, 95, 135, "", "", "");
        //########################################################################GIRONE
        int startX = 375;
        int startY = 250;
        int yHeight = 25;
        JTextField[] resGirone1 = new GUIcompon().MatchBox(main_window, startX, startY, "", "", "GIRONE PRINCIPALE");
        JTextField[] resGirone2 = new GUIcompon().MatchBox(main_window, startX, startY+yHeight, "", "", "");
        JTextField[] resGirone3 = new GUIcompon().MatchBox(main_window, startX, startY+2*yHeight, "", "", "");
        JTextField[] resGirone4 = new GUIcompon().MatchBox(main_window, startX, startY+3*yHeight, "", "", "");
        JTextField[] resGirone5 = new GUIcompon().MatchBox(main_window, startX, startY+4*yHeight, "", "", "");
        JTextField[] resGirone6 = new GUIcompon().MatchBox(main_window, startX, startY+5*yHeight, "", "", "");
        JTextPane Table = new GUIcompon().TableContainer();
        Table.setBounds(startX, startY+6*yHeight, 4*30+2*140, 4*yHeight);
        main_window.add(Table);
        //########################################################################FINALE
        JTextField[] resFinale = new GUIcompon().MatchBox(main_window, 655, 640, "", "", "FINALE");
        //########################################################################BOTTONE AGGIORNA
        JButton btnRefresh = new JButton("AGGIORNA");
        btnRefresh.setBounds(525,740,100,30);
        main_window.getRootPane().setDefaultButton(btnRefresh);
        main_window.add(btnRefresh);
        /*btnRefresh.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
            	boolean check = new ResultsOps().ResChecks(resA, resB, res11, res12, res21, res22, txtSemi11, txtSemi12,
            											   txtSemi21, txtSemi22, res1, res2, txtFin1, txtFin2);
            	if(!check){
            		JOptionPane.showMessageDialog(null,"Alcuni risultati non sono stati inseriti correttamente!","",JOptionPane.ERROR_MESSAGE);
            	}else{
					try{
						Connection conn = new SQLiteOps().SQLconn(db);
	            		new SQLiteOps().SQLgroup(conn, resA, A, "A");
	            		new SQLiteOps().SQLgroup(conn, resB, B, "B");
	            		String[] semiA = new ResultsOps().LeagueTable(tableA, new SQLiteOps().ExtractPlayersVal(conn, A));
	            		if(semiA[2] == "0"){
	            			new SQLiteOps().UpdateSemi(conn, "SEMI1", "SEMI2", txtSemi11, txtSemi22,
	            									   res11, res12, res21, res22);
	            			new SQLiteOps().UpdateFin(conn, txtFin1, txtFin2, res1, res2);
	            		}else
	            			new SQLiteOps().SQLsemiPlayers(conn, semiA, txtSemi11, txtSemi22, res11, res22, "SEMI1", "SEMI2");
	            		String[] semiB = new ResultsOps().LeagueTable(tableB, new SQLiteOps().ExtractPlayersVal(conn, B));
	            		if(semiB[2] == "0"){
	            			new SQLiteOps().UpdateSemi(conn, "SEMI2", "SEMI1", txtSemi21, txtSemi12,
									                   res11, res12, res21, res22);
	            			new SQLiteOps().UpdateFin(conn, txtFin1, txtFin2, res1, res2);
	            		}else
	            			new SQLiteOps().SQLsemiPlayers(conn, semiB, txtSemi21, txtSemi12, res21, res12, "SEMI2", "SEMI1");
	            		new SQLiteOps().SQLfinPlayers(conn, txtFin1, txtFin2, res1, res2);
	            		new SQLiteOps().CloseCnt(conn);
					}catch (Exception e1){
						e1.printStackTrace();
					}
            	}
            }
        });*/
        //########################################################################MAIN_WINDOW SETTINGS
        main_window.setResizable(false);
        main_window.setLayout(null);
        main_window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        main_window.setSize(1150,800);
        main_window.setVisible(true);
        //########################################################################CARICAMENTO DATI ALL'APERTURA
		/*try{
			Connection firstConn = new SQLiteOps().SQLconn(db);
	        new SQLiteOps().LoadData(firstConn, resA, A, resB, B, txtSemi11, txtSemi12, txtSemi21, txtSemi22,
	        						 res11, res12, res21, res22, txtFin1, txtFin2, res1, res2);
	        String[] uselessA = new ResultsOps().LeagueTable(tableA, new SQLiteOps().ExtractPlayersVal(firstConn, A));
	        String[] uselessB = new ResultsOps().LeagueTable(tableB, new SQLiteOps().ExtractPlayersVal(firstConn, B));
	    	new SQLiteOps().CloseCnt(firstConn);
		}catch (Exception e1){
			e1.printStackTrace();
		}*/
    }
    
    public static void main(String[] args){
    	new Coppa().CoppaGUI();
    }
    
}
