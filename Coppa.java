/**
 Main class for CoppaItalia
 */
import classes.GUIcompon;
import classes.ResultsOps;
import classes.SQLiteOps;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;

public class Coppa{
	
	public static final String imPath = "./images/";
	public static final String db = "coppa.db";
	public static final Map<String,String> dbTypes = new HashMap<>();
	{dbTypes.put("SP_PREL", "X");}
	{dbTypes.put("SPAR", "S");}
	{dbTypes.put("GIR", "G");}
	{dbTypes.put("FIN", "F");}
    
    public void CoppaGUI() throws SQLException{
    	
    	final int startX = 375;
    	final int startY = 250;
    	final int yHeight = 25;
    	String[] players = new String[2];
    	String[] results = new String[4];
    	
        JFrame main_window = new JFrame("COPPA ITALIA SUBBUTEO");
        main_window.setContentPane(new JLabel(new GUIcompon().ResizeImg(imPath, "campo2.png", 1150, 800)));
        Connection conn = new SQLiteOps().SQLconn(db);
        //########################################################################SPAREGGIO PRELIMINARE
        players = new SQLiteOps().GetPlayers(conn, dbTypes.get("SP_PREL"), 1);
        results = new SQLiteOps().GetResults(conn, dbTypes.get("SP_PREL"), 1);
        JTextField[] resSpareggioPrel = new GUIcompon().MatchResults(main_window, 95, 65, results[0], results[1], results[2], results[3], 1);
        JTextPane[] txtSpareggioPrel = new GUIcompon().MatchPlayers(main_window, 95, 65, players[0], players[1], "SPAREGGIO PRELIMINARE");
        //########################################################################SPAREGGI
        players = new SQLiteOps().GetPlayers(conn, dbTypes.get("SPAR"), 1);
        JTextField[] resSpareggio1 = new GUIcompon().MatchResults(main_window, 95, 125, results[0], results[1], results[2], results[3], 1);
        JTextPane[] txtSpareggio1 = new GUIcompon().MatchPlayers(main_window, 95, 125, players[0], players[1], "SPAREGGI");
        players = new SQLiteOps().GetPlayers(conn, dbTypes.get("SPAR"), 2);
        JTextField[] resSpareggio2 = new GUIcompon().MatchResults(main_window, 95, 125+yHeight, results[0], results[1], results[2], results[3], 0);
        JTextPane[] txtSpareggio2 = new GUIcompon().MatchPlayers(main_window, 95, 125+yHeight, players[0], players[1], "");
        //########################################################################GIRONE
        players = new SQLiteOps().GetPlayers(conn, dbTypes.get("GIR"), 1);
        JTextField[] resGirone1 = new GUIcompon().MatchResults(main_window, startX, startY, results[0], results[1], results[2], results[3], 1);
        JTextPane[] txtGirone1 = new GUIcompon().MatchPlayers(main_window, startX, startY, players[0], players[1], "GIRONE PRINCIPALE");
        players = new SQLiteOps().GetPlayers(conn, dbTypes.get("GIR"), 2);
        JTextField[] resGirone2 = new GUIcompon().MatchResults(main_window, startX, startY+yHeight, results[0], results[1], results[2], results[3], 0);
        JTextPane[] txtGirone2 = new GUIcompon().MatchPlayers(main_window, startX, startY+yHeight, players[0], players[1], "");
        players = new SQLiteOps().GetPlayers(conn, dbTypes.get("GIR"), 3);
        JTextField[] resGirone3 = new GUIcompon().MatchResults(main_window, startX, startY+2*yHeight, results[0], results[1], results[2], results[3], 0);
        JTextPane[] txtGirone3 = new GUIcompon().MatchPlayers(main_window, startX, startY+2*yHeight, players[0], players[1], "");
        players = new SQLiteOps().GetPlayers(conn, dbTypes.get("GIR"), 4);
        JTextField[] resGirone4 = new GUIcompon().MatchResults(main_window, startX, startY+3*yHeight, results[0], results[1], results[2], results[3], 0);
        JTextPane[] txtGirone4 = new GUIcompon().MatchPlayers(main_window, startX, startY+3*yHeight, players[0], players[1], "");
        players = new SQLiteOps().GetPlayers(conn, dbTypes.get("GIR"), 5);
        JTextField[] resGirone5 = new GUIcompon().MatchResults(main_window, startX, startY+4*yHeight, results[0], results[1], results[2], results[3], 0);
        JTextPane[] txtGirone5 = new GUIcompon().MatchPlayers(main_window, startX, startY+4*yHeight, players[0], players[1], "");
        players = new SQLiteOps().GetPlayers(conn, dbTypes.get("GIR"), 6);
        JTextField[] resGirone6 = new GUIcompon().MatchResults(main_window, startX, startY+5*yHeight, results[0], results[1], results[2], results[3], 0);
        JTextPane[] txtGirone6 = new GUIcompon().MatchPlayers(main_window, startX, startY+5*yHeight, players[0], players[1], "");
        JTextPane Table = new GUIcompon().TableContainer();
        Table.setBounds(startX, startY+6*yHeight, 4*30+2*140, 4*yHeight);
        main_window.add(Table);
        //########################################################################FINALE
        players = new SQLiteOps().GetPlayers(conn, dbTypes.get("FIN"), 1);
        JTextField[] resFinale = new GUIcompon().MatchResults(main_window, 655, 640, results[0], results[1], results[2], results[3], 1);
        JTextPane[] txtFinale = new GUIcompon().MatchPlayers(main_window, 655, 640, players[0], players[1], "FINALE");
        //close connection
        new SQLiteOps().CloseCnt(conn);
        //########################################################################BOTTONE AGGIORNA
        JButton btnRefresh = new JButton("AGGIORNA");
        btnRefresh.setBounds(525,740,100,30);
        main_window.getRootPane().setDefaultButton(btnRefresh);
        main_window.add(btnRefresh);
        btnRefresh.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
            	//boolean check = new ResultsOps().ResChecks(resA, resB, res11, res12, res21, res22, txtSemi11, txtSemi12,
            	//										   txtSemi21, txtSemi22, res1, res2, txtFin1, txtFin2);
            	//if(!check){
            	//	JOptionPane.showMessageDialog(null,"Alcuni risultati non sono stati inseriti correttamente!","",JOptionPane.ERROR_MESSAGE);
            	//}else{
					try{
            			Connection conn = new SQLiteOps().SQLconn(db);
            			new SQLiteOps().UpdateDbRow(conn, new ResultsOps().NoHtmlText(txtSpareggioPrel[0].getText(), "td"),
            										new ResultsOps().NoHtmlText(txtSpareggioPrel[1].getText(), "td"),
            										resSpareggioPrel[0].getText(), resSpareggioPrel[1].getText(), resSpareggioPrel[2].getText(),
            										resSpareggioPrel[3].getText(), dbTypes.get("SP_PREL"), 1);
	            		/*new SQLiteOps().SQLgroup(conn, resA, A, "A");
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
	            		new SQLiteOps().SQLfinPlayers(conn, txtFin1, txtFin2, res1, res2);*/
	            		new SQLiteOps().CloseCnt(conn);
					}catch (Exception e1){
						e1.printStackTrace();
					}
            	//}
            }
        });
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
    
    public static void main(String[] args) throws SQLException{
    	new Coppa().CoppaGUI();
    }
    
}
