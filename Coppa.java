/**
 Main class for CoppaItalia
 */
import classes.GUIcompon;
import classes.ResultsOps;
import classes.SQLiteOps;

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
        main_window.setContentPane(new JLabel(new GUIcompon().ResizeImg(imPath, "campo.png", 1150, 800)));
        Connection conn = new SQLiteOps().SQLconn(db);
	    
        //########################################################################SPAREGGIO PRELIMINARE
        players = new SQLiteOps().GetPlayers(conn, dbTypes.get("SP_PREL"), 1);
        results = new SQLiteOps().GetResults(conn, dbTypes.get("SP_PREL"), 1);
        JTextField[] resSpareggioPrel = new GUIcompon().MatchResults(main_window, 95, 65, results, 1);
        JTextPane[] txtSpareggioPrel = new GUIcompon().MatchPlayers(main_window, 95, 65, players, "SPAREGGIO PRELIMINARE");
        //########################################################################SPAREGGI
        players = new SQLiteOps().GetPlayers(conn, dbTypes.get("SPAR"), 1);
        results = new SQLiteOps().GetResults(conn, dbTypes.get("SPAR"), 1);
        JTextField[] resSpareggio1 = new GUIcompon().MatchResults(main_window, 95, 125, results, 1);
        JTextPane[] txtSpareggio1 = new GUIcompon().MatchPlayers(main_window, 95, 125, players, "SPAREGGI");
        players = new SQLiteOps().GetPlayers(conn, dbTypes.get("SPAR"), 2);
        results = new SQLiteOps().GetResults(conn, dbTypes.get("SPAR"), 2);
        JTextField[] resSpareggio2 = new GUIcompon().MatchResults(main_window, 95, 125+yHeight, results, 0);
        JTextPane[] txtSpareggio2 = new GUIcompon().MatchPlayers(main_window, 95, 125+yHeight, players, "");
        //########################################################################GIRONE
        players = new SQLiteOps().GetPlayers(conn, dbTypes.get("GIR"), 1);
        results = new SQLiteOps().GetResults(conn, dbTypes.get("GIR"), 1);
        JTextField[] resGirone1 = new GUIcompon().MatchResults(main_window, startX, startY, results, 1);
        JTextPane[] txtGirone1 = new GUIcompon().MatchPlayers(main_window, startX, startY, players, "GIRONE PRINCIPALE");
        players = new SQLiteOps().GetPlayers(conn, dbTypes.get("GIR"), 2);
        results = new SQLiteOps().GetResults(conn, dbTypes.get("GIR"), 2);
        JTextField[] resGirone2 = new GUIcompon().MatchResults(main_window, startX, startY+yHeight, results, 0);
        JTextPane[] txtGirone2 = new GUIcompon().MatchPlayers(main_window, startX, startY+yHeight, players, "");
        players = new SQLiteOps().GetPlayers(conn, dbTypes.get("GIR"), 3);
        results = new SQLiteOps().GetResults(conn, dbTypes.get("GIR"), 3);
        JTextField[] resGirone3 = new GUIcompon().MatchResults(main_window, startX, startY+2*yHeight, results, 0);
        JTextPane[] txtGirone3 = new GUIcompon().MatchPlayers(main_window, startX, startY+2*yHeight, players, "");
        players = new SQLiteOps().GetPlayers(conn, dbTypes.get("GIR"), 4);
        results = new SQLiteOps().GetResults(conn, dbTypes.get("GIR"), 4);
        JTextField[] resGirone4 = new GUIcompon().MatchResults(main_window, startX, startY+3*yHeight, results, 0);
        JTextPane[] txtGirone4 = new GUIcompon().MatchPlayers(main_window, startX, startY+3*yHeight, players, "");
        players = new SQLiteOps().GetPlayers(conn, dbTypes.get("GIR"), 5);
        results = new SQLiteOps().GetResults(conn, dbTypes.get("GIR"), 5);
        JTextField[] resGirone5 = new GUIcompon().MatchResults(main_window, startX, startY+4*yHeight, results, 0);
        JTextPane[] txtGirone5 = new GUIcompon().MatchPlayers(main_window, startX, startY+4*yHeight, players, "");
        players = new SQLiteOps().GetPlayers(conn, dbTypes.get("GIR"), 6);
        results = new SQLiteOps().GetResults(conn, dbTypes.get("GIR"), 6);
        JTextField[] resGirone6 = new GUIcompon().MatchResults(main_window, startX, startY+5*yHeight, results, 0);
        JTextPane[] txtGirone6 = new GUIcompon().MatchPlayers(main_window, startX, startY+5*yHeight, players, "");
        JTextPane Table = new GUIcompon().TableContainer(false);
        Table.setBounds(startX, startY+6*yHeight, 4*30+2*140, 4*yHeight);
        main_window.add(Table);
        //########################################################################FINALE
        players = new SQLiteOps().GetPlayers(conn, dbTypes.get("FIN"), 1);
        results = new SQLiteOps().GetResults(conn, dbTypes.get("FIN"), 1);
        JTextField[] resFinale = new GUIcompon().MatchResults(main_window, 655, 640, results, 1);
        JTextPane[] txtFinale = new GUIcompon().MatchPlayers(main_window, 655, 640, players, "FINALE");
        //close connection
        new SQLiteOps().CloseCnt(conn);
        //########################################################################BOTTONE AGGIORNA
        JButton btnRefresh = new JButton("AGGIORNA");
        btnRefresh.setBounds(525, 740, 100, 30);
        main_window.getRootPane().setDefaultButton(btnRefresh);
        main_window.add(btnRefresh);
        btnRefresh.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
            	new ResultsOps().ValidateFields(resSpareggioPrel, txtSpareggioPrel);
            	if(!(new ResultsOps().ValidateFields(resSpareggioPrel, txtSpareggioPrel) &&
            		 new ResultsOps().ValidateFields(resSpareggio1, txtSpareggio1) &&
            		 new ResultsOps().ValidateFields(resSpareggio2, txtSpareggio2) &&
            		 new ResultsOps().ValidateFields(resGirone1, txtGirone1) &&
            		 new ResultsOps().ValidateFields(resGirone2, txtGirone2) &&
            		 new ResultsOps().ValidateFields(resGirone3, txtGirone3) &&
            		 new ResultsOps().ValidateFields(resGirone4, txtGirone4) &&
            		 new ResultsOps().ValidateFields(resGirone5, txtGirone5) &&
            		 new ResultsOps().ValidateFields(resGirone6, txtGirone6) &&
            		 new ResultsOps().ValidateFields(resFinale, txtFinale))){
            		JOptionPane.showMessageDialog(null, "Alcuni risultati non sono stati inseriti correttamente!", "", JOptionPane.ERROR_MESSAGE);
            	}else{
            		try{
            			Connection conn = new SQLiteOps().SQLconn(db);
            			new SQLiteOps().UpdateDbRow(conn, txtSpareggioPrel, resSpareggioPrel, dbTypes.get("SP_PREL"), 1);
						new SQLiteOps().UpdateDbRow(conn, txtSpareggio1, resSpareggio1, dbTypes.get("SPAR"), 1);
						new SQLiteOps().UpdateDbRow(conn, txtSpareggio2, resSpareggio2, dbTypes.get("SPAR"), 2);
						new SQLiteOps().UpdateDbRow(conn, txtGirone1, resGirone1, dbTypes.get("GIR"), 1);
						new SQLiteOps().UpdateDbRow(conn, txtGirone2, resGirone2, dbTypes.get("GIR"), 2);
						new SQLiteOps().UpdateDbRow(conn, txtGirone3, resGirone3, dbTypes.get("GIR"), 3);
						new SQLiteOps().UpdateDbRow(conn, txtGirone4, resGirone4, dbTypes.get("GIR"), 4);
						new SQLiteOps().UpdateDbRow(conn, txtGirone5, resGirone5, dbTypes.get("GIR"), 5);
						new SQLiteOps().UpdateDbRow(conn, txtGirone6, resGirone6, dbTypes.get("GIR"), 6);
						new SQLiteOps().UpdateDbRow(conn, txtFinale, resFinale, dbTypes.get("FIN"), 1);
						//la classifica legge dal database
	            		new SQLiteOps().CloseCnt(conn);
            		}catch (Exception e1){
            			e1.printStackTrace();
            		}
            	}
            }
        });
        //########################################################################MAIN_WINDOW SETTINGS
        main_window.setResizable(false);
        main_window.setLayout(null);
        main_window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        main_window.setSize(1150, 800);
        main_window.setVisible(true);
    }
    
    public static void main(String[] args) throws SQLException{
    	new Coppa().CoppaGUI();
    }
    
}
