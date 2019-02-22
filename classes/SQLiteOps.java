package classes;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.swing.JTextField;
import javax.swing.JTextPane;

public class SQLiteOps{
	
	public Connection SQLconn(String dbName) throws SQLException{
		//Class.forName("org.sqlite.JDBC");
		Connection connection = null;
		//connection = DriverManager.getConnection("jdbc:sqlite::resource:" + dbName);
		connection = DriverManager.getConnection("jdbc:sqlite:" + dbName);
		connection.setAutoCommit(false);
		Statement statement = connection.createStatement();
		//statement.setQueryTimeout(30);
		statement.execute("CREATE TABLE IF NOT EXISTS COPPA (\n" +
						  "COPPA_ID INTEGER     PRIMARY KEY AUTOINCREMENT NOT NULL,\n" +
						  "PLAYER   VARCHAR (30),\n" +
						  "OPPONENT VARCHAR (30),\n" +
						  "GF_A     INTEGER,\n" +
						  "GS_A     INTEGER,\n" +
						  "TYPE     VARCHAR (1) NOT NULL,\n" +
						  "GF_R     INTEGER,\n" +
						  "GS_R     INTEGER,\n" +
						  "N_MATCH  INTEGER     NOT NULL\n" +
						  ");");
		return connection;
	}
	
	public void CloseCnt(Connection cnt) throws SQLException{
        if(cnt != null){
       		cnt.commit();
       		cnt.close();
        }
	}
	
	public String[] GetPlayers(Connection cnt, String type, int n_match) throws SQLException{
		String[] players = {"",""};
		Statement stmt = cnt.createStatement();
		String query;
		ResultSet rs;
		query = "SELECT PLAYER, OPPONENT FROM COPPA WHERE TYPE = '"+type+"' AND N_MATCH = '"+n_match+"'";
		rs = stmt.executeQuery(query);
		while(rs.next()){
			if(rs.getString("PLAYER") != null && !rs.getString("PLAYER").isEmpty())
				players[0] = rs.getString("PLAYER");
			if(rs.getString("OPPONENT") != null && !rs.getString("OPPONENT").isEmpty())
				players[1] = rs.getString("OPPONENT");
		}
		return players;
	}
	
	public String[] GetResults(Connection cnt, String type, int n_match) throws SQLException{
		String[] res = {"","","",""};
		Statement stmt = cnt.createStatement();
		String query;
		ResultSet rs;
		query = "SELECT GF_A, GS_A, GF_R, GS_R FROM COPPA WHERE TYPE = '"+type+"' AND N_MATCH = '"+n_match+"'";
		rs = stmt.executeQuery(query);
		while(rs.next()){
			if(rs.getString("GF_A") != null && !rs.getString("GF_A").isEmpty())
				res[0] = rs.getString("GF_A");
			if(rs.getString("GS_A") != null && !rs.getString("GS_A").isEmpty())
				res[1] = rs.getString("GS_A");
			if(rs.getString("GF_R") != null && !rs.getString("GF_R").isEmpty())
				res[2] = rs.getString("GF_R");
			if(rs.getString("GS_R") != null && !rs.getString("GS_R").isEmpty())
				res[3] = rs.getString("GS_R");
		}
		return res;
	}
	
	public void UpdateDbRow(Connection cnt, JTextPane[] Jtxt, JTextField[] Jres, String type, int n_match) throws SQLException{
		Statement stmt = cnt.createStatement();
		String pl1 = new ResultsOps().NoHtmlText(Jtxt[0].getText(), "td");
		String pl2 = new ResultsOps().NoHtmlText(Jtxt[1].getText(), "td");
		String res1 = Jres[0].getText();
		String res2 = Jres[1].getText();
		String res3 = Jres[2].getText();
		String res4 = Jres[3].getText();
		stmt.executeUpdate("UPDATE COPPA SET PLAYER='"+pl1+"',OPPONENT='"+pl2+"',GF_A='"+res1+"',GS_A='"+res2+"',GF_R='"+res3+"',"
				+"GS_R='"+res4+"' WHERE TYPE='"+type+"' AND N_MATCH='"+n_match+"'");
	}
	
	public int CheckVictory(ResultSet resSet, String gfField, String gsField) throws SQLException{
		int ret = 0;
		if(resSet.getString(gfField) != "" && resSet.getString(gsField) != ""){
			if(!resSet.getString(gfField).isEmpty() && !resSet.getString(gsField).isEmpty()){
				if(resSet.getInt(gfField) > resSet.getInt(gsField)){
					ret = 1;
				}
			}
		}
		return ret;
	}
	
	public int CheckLoss(ResultSet resSet, String gfField, String gsField) throws SQLException{
		int ret = 0;
		if(resSet.getString(gfField) != "" && resSet.getString(gsField) != ""){
			if(!resSet.getString(gfField).isEmpty() && !resSet.getString(gsField).isEmpty()){
				if(resSet.getInt(gfField) < resSet.getInt(gsField)){
					ret = 1;
				}
			}
		}
		return ret;
	}
	
	public int CheckDraw(ResultSet resSet, String gfField, String gsField) throws SQLException{
		int ret = 0;
		if(resSet.getString(gfField) != "" && resSet.getString(gsField) != ""){
			if(!resSet.getString(gfField).isEmpty() && !resSet.getString(gsField).isEmpty()){
				if(resSet.getInt(gfField) == resSet.getInt(gsField)){
					ret = 1;
				}
			}
		}
		return ret;
	}
	
	public int CheckGames(ResultSet resSet, String gfField, String gsField) throws SQLException{
		int ret = 0;
		if(resSet.getString(gfField) != "" && resSet.getString(gsField) != ""){
			if(!resSet.getString(gfField).isEmpty() && !resSet.getString(gsField).isEmpty()){
				ret = 1;
			}
		}
		return ret;
	}
	
	public void UpdateMap(ResultSet resSet, String plKey, String gfPl, String gsPl, Map<String, int[]> map) throws SQLException{
		int[] vals = new int[7];
		int score, g, v, n, p, gf, gs;
		String pl;
		pl = resSet.getString(plKey);
		g = CheckGames(resSet, gfPl+"_A", gsPl+"_A") + CheckGames(resSet, gfPl+"_R", gsPl+"_R");
		v = CheckVictory(resSet, gfPl+"_A", gsPl+"_A") + CheckVictory(resSet, gfPl+"_R", gsPl+"_R");
		n = CheckDraw(resSet, gfPl+"_A", gsPl+"_A") + CheckDraw(resSet, gfPl+"_R", gsPl+"_R");
		p = CheckLoss(resSet, gfPl+"_A", gsPl+"_A") + CheckLoss(resSet, gfPl+"_R", gsPl+"_R");
		gf = resSet.getInt(gfPl+"_A") + resSet.getInt(gfPl+"_R");
		gs = resSet.getInt(gsPl+"_A") + resSet.getInt(gsPl+"_R");
		if(map.containsKey(pl)){
			vals = map.get(pl);
			g += vals[1];
			v += vals[2];
			n += vals[3];
			p += vals[4];
			gf += vals[5];
			gs += vals[6];
		}
		score = 3 * v + n;
		map.put(pl, new int[] {score, g, v, n, p, gf, gs});
	}
	
	public String[][] ExtractPlayersVal(Connection cnt, int n_matches) throws SQLException{
		Map<String, int[]> LT = new HashMap<>();
		Statement stmt  = cnt.createStatement();
		String query;
		ResultSet rs;
		for(int i = 1; i <= n_matches; i++){
			query = "SELECT PLAYER, OPPONENT, GF_A, GS_A, GF_R, GS_R FROM COPPA WHERE TYPE = 'G' AND N_MATCH = "+String.valueOf(i)+
					" AND PLAYER != '' AND OPPONENT != ''";
			rs = stmt.executeQuery(query);
			while(rs.next()){
				UpdateMap(rs, "PLAYER", "GF", "GS", LT);
				UpdateMap(rs, "OPPONENT", "GS", "GF", LT);
			}
		}
		String[][] LeagueTbl = new String[LT.size()][8];
		Set entries = LT.entrySet();
		Iterator entriesIterator = entries.iterator();
		int i = 0;
		while(entriesIterator.hasNext()){
			@SuppressWarnings("unchecked")
			Map.Entry<String, int[]> mapping = (Map.Entry<String, int[]>) entriesIterator.next();
			LeagueTbl[i][0] = mapping.getKey();
			LeagueTbl[i][1] = String.valueOf(mapping.getValue()[0]);
			LeagueTbl[i][2] = String.valueOf(mapping.getValue()[1]);
			LeagueTbl[i][3] = String.valueOf(mapping.getValue()[2]);
			LeagueTbl[i][4] = String.valueOf(mapping.getValue()[3]);
			LeagueTbl[i][5] = String.valueOf(mapping.getValue()[4]);
			LeagueTbl[i][6] = String.valueOf(mapping.getValue()[5]);
			LeagueTbl[i][7] = String.valueOf(mapping.getValue()[6]);
			i++;
		}
		return LeagueTbl;
	}

}
