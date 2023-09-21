

package lugui20.repository;

import lugui20.ConnectionFactory;
import lugui20.dominio.Producer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author lusta
 */
public class ProducerRepositoryCrud {
    
    public static void create(Producer producer){
        try (Connection conn = ConnectionFactory.getConnection();
                PreparedStatement ps = createPreparedStatement(conn, producer)) {
            ps.execute();
            Logger.getLogger(ProducerRepositoryCrud.class.getName())
                    .log(Level.INFO, "Database rows affected: {0}", producer.getName());
        } catch (SQLException ex) {
            Logger.getLogger(ProducerRepositoryCrud.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
    }
    
    private static PreparedStatement createPreparedStatement(Connection conn, Producer producer) throws SQLException{
        String sql = "INSERT INTO anime_store.producer (name) VALUES (?);";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, producer.getName());
        return ps;
    }
    
    public static List<Producer> read(String name){
        List<Producer> producers = new ArrayList<>();
        
        try(Connection conn = ConnectionFactory.getConnection();
            PreparedStatement ps = readPreparedStatement(conn, name);
            ResultSet rs = ps.executeQuery()) {
 
            Logger.getLogger(ProducerRepositoryCrud.class.getName())
                    .log(Level.INFO, "Finding producers by name"); 
            
            while(rs.next()){
                Producer producer = Producer.builder()
                        .id(rs.getInt("id"))
                        .name(rs.getString("name"))
                        .build();
                producers.add(producer);
            }

        } catch (SQLException ex) {
            Logger.getLogger(ProducerRepositoryCrud.class.getName())
                    .log(Level.SEVERE, null, ex);
        } 
        return producers;
    }
    
    private static PreparedStatement readPreparedStatement(Connection conn, String name) throws SQLException{
        String sql = "SELECT * FROM anime_store.producer WHERE name LIKE ?;";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, String.format("%%%s%%", name));
        return ps;
    }
    
    public static Optional<Producer> findById(Integer id){
        try(Connection conn = ConnectionFactory.getConnection();
            PreparedStatement ps = findByIdPreparedStatement(conn, id);
            ResultSet rs = ps.executeQuery()) {
 
            Logger.getLogger(ProducerRepositoryCrud.class.getName())
                    .log(Level.INFO, "Finding producers by name"); 
            
            if(!rs.next()) return Optional.empty();
            return Optional.of(Producer.builder()
                        .id(rs.getInt("id"))
                        .name(rs.getString("name"))
                        .build());
        } catch (SQLException ex) {
            Logger.getLogger(ProducerRepositoryCrud.class.getName())
                    .log(Level.SEVERE, null, ex);
        } 
        return null;
    }
    
    private static PreparedStatement findByIdPreparedStatement(Connection conn, Integer id) throws SQLException{
        String sql = "SELECT * FROM anime_store.producer WHERE id = ?;";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, id);
        return ps;
    }
    
    public static void update(Producer producer){
        try (Connection conn = ConnectionFactory.getConnection();
                PreparedStatement ps = updatePreparedStatement(conn, producer)) {
            ps.executeUpdate();
            Logger.getLogger(ProducerRepositoryCrud.class.getName())
                    .log(Level.INFO, "Database rows affected: {0}", producer.getName());
        } catch (SQLException ex) {
            Logger.getLogger(ProducerRepositoryCrud.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
    }
    
    private static PreparedStatement updatePreparedStatement(Connection conn, Producer producer) throws SQLException{
        String sql = "UPDATE anime_store.producer SET name = ? WHERE id = ?;";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, producer.getName());
        ps.setInt(2, producer.getId());
        return ps;
    }

    public static void delete(int id) {
        try (Connection conn = ConnectionFactory.getConnection();
                PreparedStatement ps = preparedStatementDelete(conn, id)) {
            ps.execute();
            Logger.getLogger(ProducerRepositoryCrud.class.getName())
                    .log(Level.INFO, "Database rows affected: {0}", id);
        } catch (SQLException ex) {
            Logger.getLogger(ProducerRepositoryCrud.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
    }

    private static PreparedStatement preparedStatementDelete(Connection conn, int id) throws SQLException {
        String sql = "DELETE FROM anime_store.producer WHERE (id = ?);";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, id);
        return ps;
    }
}
