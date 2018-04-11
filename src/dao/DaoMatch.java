package dao;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import models.Player;

public class DaoMatch {

	
	        private Conexion conn = Conexion.getInstance();

	        public void saveMatch (Player player,String hand) throws Exception{
	            String sq = "insert into Logs(player,hand) values (?,?)";
	            try{
	                conn.connect();
	                PreparedStatement st = conn.getConn().prepareStatement(sq);
	                st.setString(1,player.getName());
	                st.setString(2,player.getHandToString());
	                st.executeUpdate();

	            }
	            catch (SQLException es){
	                es.printStackTrace();
	            }
	            finally{
	                try {
	                    conn.desconnect();
	                } catch (Exception x) {
	                    x.printStackTrace();
	                }
	            }
	        }
	        public ArrayList<String> getMatchs() throws Exception{
	            ArrayList<String> list= new ArrayList<String>();
	            String sq= "select * from Logs";

	            try{
	                conn.connect();
	                PreparedStatement st=conn.getConn().prepareStatement(sq);
	                ResultSet rs= st.executeQuery();
	                if(rs == null){
	                    System.out.println(" No hay registros en la base de datos");
	                }
	                while(rs.next()){
	                    String s= new String("Partido n° "+rs.getInt("id_log") +" el ganador fue "+rs.getString("player")+" su mano fue "+ rs.getString("hand"));
	                    list.add(s);
	                }
	            }catch(SQLException a){
	                a.printStackTrace();
	            }
	            finally {
	                try{
	                    conn.desconnect();
	                }catch (Exception b){
	                    b.printStackTrace();
	                }
	            }
	            return list;
	        }


	    }

