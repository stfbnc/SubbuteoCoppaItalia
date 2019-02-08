package classes;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JTextField;
import javax.swing.JTextPane;

public class SQLiteOps{
	
	public Connection SQLconn(String dbName) throws Exception{
		//Class.forName("org.sqlite.JDBC");
		Connection connection = null;
		try{
			//connection = DriverManager.getConnection("jdbc:sqlite::resource:" + dbName);
			connection = DriverManager.getConnection("jdbc:sqlite:" + dbName);
			connection.setAutoCommit(false);
			Statement statement = connection.createStatement();
			//statement.setQueryTimeout(30);
			statement.execute("CREATE TABLE IF NOT EXISTS COPPA (\n" +
							  "COPPA_ID INTEGER      PRIMARY KEY AUTOINCREMENT NOT NULL,\n" +
							  "PLAYER   VARCHAR (30),\n" +
							  "OPPONENT VARCHAR (30),\n" +
							  "GF       INTEGER,\n" +
							  "GS       INTEGER,\n" +
							  "TYPE     VARCHAR (1) NOT NULL\n" +
							  ");");
	    }catch(SQLException e){
	    	System.err.println(e.getMessage());
	    }
		return connection;
	}
	
	public void CloseCnt(Connection cnt){
		try{
        	if(cnt != null)
        		cnt.commit();
        		cnt.close();
        }catch(SQLException e){          
           System.err.println(e); 
        }
	}
	
	public void LoadData(Connection cnt, JTextField[] jtfGroupA, String[] playersA, JTextField[] jtfGroupB, String[] playersB,
            			 JTextPane sm11Txt, JTextPane sm12Txt, JTextPane sm21Txt, JTextPane sm22Txt,
            			 JTextField sm11Fld, JTextField sm12Fld, JTextField sm21Fld, JTextField sm22Fld,
            			 JTextPane fin1Txt, JTextPane fin2Txt, JTextField fin1Fld, JTextField fin2Fld){
		try{
			Statement stmt  = cnt.createStatement();
			String query;
			ResultSet rs;
			for(int i = 0; i < (jtfGroupA.length / 2); i++){
				query = "SELECT GF, GS FROM GAMES WHERE PLAYER = '" + playersA[i] + "' AND TYPE = 'A'";
				rs = stmt.executeQuery(query);
				while(rs.next()){
					jtfGroupA[i].setText(rs.getString("GF"));
					jtfGroupA[i + (jtfGroupA.length / 2)].setText(rs.getString("GS"));
				}
			}
			for(int i = 0; i < (jtfGroupB.length / 2); i++){
				query = "SELECT GF, GS FROM GAMES WHERE PLAYER = '" + playersB[i] + "' AND TYPE = 'B'";
				rs = stmt.executeQuery(query);
				while(rs.next()){
					jtfGroupB[i].setText(rs.getString("GF"));
					jtfGroupB[i + (jtfGroupB.length / 2)].setText(rs.getString("GS"));
				}
			}
			query = "SELECT * FROM GAMES WHERE TYPE = 'SEMI1'";
			rs = stmt.executeQuery(query);
			while(rs.next()){
				if(rs.getString("PLAYER") != null)
					new ResultsOps().SetPlayer(sm11Txt, rs.getString("PLAYER"));
				if(rs.getString("GF") != null)
					sm11Fld.setText(rs.getString("GF"));
				if(rs.getString("OPPONENT") != null)
					new ResultsOps().SetPlayer(sm12Txt, rs.getString("OPPONENT"));
				if(rs.getString("GS") != null)
					sm12Fld.setText(rs.getString("GS"));
			}
			query = "SELECT * FROM GAMES WHERE TYPE = 'SEMI2'";
			rs = stmt.executeQuery(query);
			while(rs.next()){
				if(rs.getString("PLAYER") != null)
					new ResultsOps().SetPlayer(sm21Txt, rs.getString("PLAYER"));
				if(rs.getString("GF") != null)
					sm21Fld.setText(rs.getString("GF"));
				if(rs.getString("OPPONENT") != null)
					new ResultsOps().SetPlayer(sm22Txt, rs.getString("OPPONENT"));
				if(rs.getString("GS") != null)
					sm22Fld.setText(rs.getString("GS"));
			}
			query = "SELECT * FROM GAMES WHERE TYPE = 'FIN'";
			rs = stmt.executeQuery(query);
			while(rs.next()){
				if(rs.getString("PLAYER") != null)
					new ResultsOps().SetPlayer(fin1Txt, rs.getString("PLAYER"));
				if(rs.getString("GF") != null)
					fin1Fld.setText(rs.getString("GF"));
				if(rs.getString("OPPONENT") != null)
					new ResultsOps().SetPlayer(fin2Txt, rs.getString("OPPONENT"));
				if(rs.getString("GS") != null)
					fin2Fld.setText(rs.getString("GS"));
			}
		}catch(SQLException e){          
	           System.err.println(e); 
	    }
	}
	
	public void SQLgroup(Connection cnt, JTextField[] group, String[] players, String type){
		try{
			Statement stmt = cnt.createStatement();
			for(int i = 0; i < (group.length / 2); i++){
				String query = "SELECT * FROM GAMES WHERE PLAYER = '" + players[i] + "' AND TYPE = '" + type + "'";
				ResultSet rs = stmt.executeQuery(query);
				if(!rs.isBeforeFirst()){
					if(!group[i].getText().isEmpty())
						stmt.executeUpdate("INSERT INTO GAMES (PLAYER, OPPONENT, GF, GS, TYPE)" + 
				            		   	   "VALUES ('" + players[i] + "', '" + players[(i + 1) % (group.length / 2)] + "', '" +
				            		   	   group[i].getText() + "', '" + group[i + (group.length / 2)].getText() + "', '" + type + "')");
				}else{
					while(rs.next()){
						if(group[i].getText().isEmpty())
							stmt.executeUpdate("DELETE FROM GAMES WHERE PLAYER = '" + players[i] + "' AND TYPE = '" + type + "'");
						else{
							int res1 = Integer.parseInt(group[i].getText());
							int res2 = Integer.parseInt(group[i + (group.length / 2)].getText());
							if((rs.getInt("GF") != res1) || (rs.getInt("GS") != res2))
								stmt.executeUpdate("UPDATE GAMES SET GF = '" + res1 + "', GS = '" + res2 + "' WHERE PLAYER = '" + players[i] + "'");
						}
					}
				}
			}
		}catch(SQLException e){
	    	System.err.println(e.getMessage());
	    }
	}
	
	public String[][] ExtractPlayersVal(Connection cnt, String[] players){
		String[][] LeagueTbl = new String[players.length][8];
		try{
			Statement stmt  = cnt.createStatement();
			String query;
			ResultSet rs;
			int gf, gs, g, v, n, p, score;
			for(int i = 0; i < players.length; i++){
				gf = 0; gs = 0; g = 0; v = 0; n = 0; p = 0;
				query = "SELECT GF, GS FROM GAMES WHERE PLAYER = '" + players[i] + "' AND TYPE IN ('A', 'B')";
				rs = stmt.executeQuery(query);
				while(rs.next()){
					g++;
					if(rs.getInt("GF") > rs.getInt("GS"))
						v++;
					else if(rs.getInt("GF") < rs.getInt("GS"))
						p++;
					else
						n++;
					gf += rs.getInt("GF");
					gs += rs.getInt("GS");
				}
				query = "SELECT GF, GS FROM GAMES WHERE OPPONENT = '" + players[i] + "' AND TYPE IN ('A', 'B')";
				rs = stmt.executeQuery(query);
				while(rs.next()){
					g++;
					if(rs.getInt("GS") > rs.getInt("GF"))
						v++;
					else if(rs.getInt("GS") < rs.getInt("GF"))
						p++;
					else
						n++;
					gf += rs.getInt("GS");
					gs += rs.getInt("GF");
				}
				score = 3 * v + n;
				LeagueTbl[i][0] = players[i];
				LeagueTbl[i][1] = String.valueOf(score);
				LeagueTbl[i][2] = String.valueOf(g);
				LeagueTbl[i][3] = String.valueOf(v);
				LeagueTbl[i][4] = String.valueOf(n);
				LeagueTbl[i][5] = String.valueOf(p);
				LeagueTbl[i][6] = String.valueOf(gf);
				LeagueTbl[i][7] = String.valueOf(gs);
			}
		}catch(SQLException e){
	    	System.err.println(e.getMessage());
	    }
		return LeagueTbl;
	}
	
	public void SQLsemiPlayers(Connection cnt, String[] semiPlayers, JTextPane semi1, JTextPane semi2,
							   JTextField s1, JTextField s2, String type1, String type2){
		new ResultsOps().SetPlayer(semi1, semiPlayers[0]);
		new ResultsOps().SetPlayer(semi2, semiPlayers[1]);
		try{
			Statement stmt = cnt.createStatement();
			String query;
			ResultSet rs;
			query = "SELECT * FROM GAMES WHERE TYPE = 'SEMI1'";
			rs = stmt.executeQuery(query);
			if(!rs.isBeforeFirst()){
				stmt.executeUpdate("INSERT INTO GAMES (PLAYER, TYPE) VALUES ('" + semiPlayers[0] + "', '" + type1 + "')");
				stmt.executeUpdate("INSERT INTO GAMES (OPPONENT, TYPE) VALUES ('" + semiPlayers[1] + "', '" + type2 + "')");
			}else{
				stmt.executeUpdate("UPDATE GAMES SET PLAYER = '" + semiPlayers[0] + "' WHERE TYPE = '" + type1 + "'");
				stmt.executeUpdate("UPDATE GAMES SET OPPONENT = '" + semiPlayers[1] + "' WHERE TYPE = '" + type2 + "'");
			}
			if(!s1.getText().isEmpty() && s1.getText() != "")
				stmt.executeUpdate("UPDATE GAMES SET GF = '" + s1.getText() + "' WHERE TYPE = '" + type1 + "'");
			else
				stmt.executeUpdate("UPDATE GAMES SET GF = NULL WHERE TYPE = '" + type1 + "'");
			if(!s2.getText().isEmpty() && s2.getText() != "")
				stmt.executeUpdate("UPDATE GAMES SET GS = '" + s2.getText() + "' WHERE TYPE = '" + type2 + "'");
			else
				stmt.executeUpdate("UPDATE GAMES SET GS = NULL WHERE TYPE = '" + type2 + "'");	
		}catch(SQLException e){
	    	System.err.println(e.getMessage());
	    }
	}
	
	public void UpdateSemi(Connection cnt, String type1, String type2, JTextPane p1, JTextPane p2,
			               JTextField f1, JTextField f2, JTextField f3, JTextField f4){
		try{
			Statement stmt = cnt.createStatement();
			String query;
			ResultSet rs;
			query = "SELECT * FROM GAMES WHERE TYPE = 'SEMI1'";
			rs = stmt.executeQuery(query);
			while(rs.next()){
				stmt.executeUpdate("UPDATE GAMES SET PLAYER = NULL WHERE TYPE = '" + type1 + "'");
				stmt.executeUpdate("UPDATE GAMES SET GF = NULL WHERE TYPE = '" + type1 + "'");
				stmt.executeUpdate("UPDATE GAMES SET OPPONENT = NULL WHERE TYPE = '" + type2 + "'");
				stmt.executeUpdate("UPDATE GAMES SET GS = NULL WHERE TYPE = '" + type2 + "'");
				new ResultsOps().EmptyPlayer(p1);
				new ResultsOps().EmptyPlayer(p2);
				f1.setText("");
				f2.setText("");
				f3.setText("");
				f4.setText("");
			}
		}catch(SQLException e){
	    	System.err.println(e.getMessage());
	    }
	}
	
	public void UpdateFin(Connection cnt, JTextPane p3, JTextPane p4, JTextField f5, JTextField f6){
		try {
			Statement stmt = cnt.createStatement();
			String query;
			ResultSet rs;
			query = "SELECT * FROM GAMES WHERE TYPE = 'FIN'";
			rs = stmt.executeQuery(query);
			while(rs.next()){
				stmt.executeUpdate("UPDATE GAMES SET PLAYER = NULL WHERE TYPE = 'FIN'");
				stmt.executeUpdate("UPDATE GAMES SET GF = NULL WHERE TYPE = 'FIN'");
				stmt.executeUpdate("UPDATE GAMES SET OPPONENT = NULL WHERE TYPE = 'FIN'");
				stmt.executeUpdate("UPDATE GAMES SET GS = NULL WHERE TYPE = 'FIN'");
				new ResultsOps().EmptyPlayer(p3);
				new ResultsOps().EmptyPlayer(p4);
				f5.setText("");
				f6.setText("");
			}
		}catch(SQLException e){
	    	System.err.println(e.getMessage());
	    }
	}
	
	public String[] getFinPlayers(Connection cnt){
		String[] plF = {"", ""};
		try{
			Statement stmt = cnt.createStatement();
			String query;
			ResultSet rs;
			query = "SELECT * FROM GAMES WHERE TYPE = 'SEMI1' AND PLAYER IS NOT NULL AND OPPONENT IS NOT NULL "
					+ "AND GF IS NOT NULL AND GS IS NOT NULL";
			rs = stmt.executeQuery(query);
			while(rs.next()){
				if(rs.getInt("GF") > rs.getInt("GS")){
					plF[0] = rs.getString("PLAYER");
				}else if(rs.getInt("GF") < rs.getInt("GS")){
					plF[0] = rs.getString("OPPONENT");
				}
			}
			query = "SELECT * FROM GAMES WHERE TYPE = 'SEMI2' AND PLAYER IS NOT NULL AND OPPONENT IS NOT NULL "
					+ "AND GF IS NOT NULL AND GS IS NOT NULL";
			rs = stmt.executeQuery(query);
			while(rs.next()){
				if(rs.getInt("GF") > rs.getInt("GS")){
					plF[1] = rs.getString("PLAYER");
				}else if(rs.getInt("GF") < rs.getInt("GS")){
					plF[1] = rs.getString("OPPONENT");
				}
			}
		}catch(SQLException e){
	    	System.err.println(e.getMessage());
	    }
		return plF;
	}
	
	public void SQLfinPlayers(Connection cnt, JTextPane fin1, JTextPane fin2, JTextField f1, JTextField f2){
		String[] finPl = new SQLiteOps().getFinPlayers(cnt);
		if(finPl[0] != "" && finPl[1] == ""){
			new ResultsOps().SetPlayer(fin1, finPl[0]);
			new ResultsOps().EmptyPlayer(fin2);
			f1.setText("");
			f2.setText("");
		}
		if(finPl[1] != "" && finPl[0] == ""){
			new ResultsOps().SetPlayer(fin2, finPl[1]);
			new ResultsOps().EmptyPlayer(fin1);
			f1.setText("");
			f2.setText("");
		}
		if(finPl[0] != "" && finPl[1] != ""){
			new ResultsOps().SetPlayer(fin1, finPl[0]);
			new ResultsOps().SetPlayer(fin2, finPl[1]);
		}
		if(finPl[0] == "" && finPl[1] == ""){
			new ResultsOps().EmptyPlayer(fin1);
			new ResultsOps().EmptyPlayer(fin2);
			f1.setText("");
			f2.setText("");
		}	
		try{
			Statement stmt = cnt.createStatement();
			String query;
			ResultSet rs;
			query = "SELECT * FROM GAMES WHERE TYPE = 'FIN'";
			rs = stmt.executeQuery(query);
			if(!rs.isBeforeFirst()){
				if(finPl[0] != "" && finPl[1] == "")
					stmt.executeUpdate("INSERT INTO GAMES (PLAYER, TYPE) VALUES ('" + finPl[0] + "', 'FIN')");
				if(finPl[1] != "" && finPl[0] == "")
					stmt.executeUpdate("INSERT INTO GAMES (OPPONENT, TYPE) VALUES ('" + finPl[1] + "', 'FIN')");
				if(finPl[0] != "" && finPl[1] != "")
					stmt.executeUpdate("INSERT INTO GAMES (PLAYER, OPPONENT, TYPE) VALUES ('" + finPl[0] + "', '" + finPl[1] + "', 'FIN')");
			}else{
				if(finPl[0] != "" && finPl[1] == ""){
					stmt.executeUpdate("UPDATE GAMES SET PLAYER = '" + finPl[0] + "' WHERE TYPE = 'FIN'");
					stmt.executeUpdate("UPDATE GAMES SET OPPONENT = NULL WHERE TYPE = 'FIN'");
				}
				if(finPl[1] != "" && finPl[0] == ""){
					stmt.executeUpdate("UPDATE GAMES SET OPPONENT = '" + finPl[1] + "' WHERE TYPE = 'FIN'");
					stmt.executeUpdate("UPDATE GAMES SET PLAYER = NULL WHERE TYPE = 'FIN'");
				}
				if(finPl[0] != "" && finPl[1] != ""){
					stmt.executeUpdate("UPDATE GAMES SET OPPONENT = '" + finPl[1] + "' WHERE TYPE = 'FIN'");
					stmt.executeUpdate("UPDATE GAMES SET PLAYER = '" + finPl[0] + "' WHERE TYPE = 'FIN'");
				}
				if(finPl[0] == "" && finPl[1] == ""){
					stmt.executeUpdate("UPDATE GAMES SET PLAYER = NULL WHERE TYPE = 'FIN'");
					stmt.executeUpdate("UPDATE GAMES SET OPPONENT = NULL WHERE TYPE = 'FIN'");
				}
			}
			if(!f1.getText().isEmpty() && f1.getText() != ""){
				stmt.executeUpdate("UPDATE GAMES SET GF = '" + f1.getText() + "' WHERE TYPE = 'FIN'");
				stmt.executeUpdate("UPDATE GAMES SET GS = '" + f2.getText() + "' WHERE TYPE = 'FIN'");
			}else{
				stmt.executeUpdate("UPDATE GAMES SET GF = NULL WHERE TYPE = 'FIN'");
				stmt.executeUpdate("UPDATE GAMES SET GS = NULL WHERE TYPE = 'FIN'");
			}
		}catch(SQLException e){
	    	System.err.println(e.getMessage());
	    }
	}

}
