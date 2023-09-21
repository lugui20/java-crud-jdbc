
package lugui20.repository;

import lugui20.ConnectionFactory;
import lugui20.dominio.Anime;
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


public class AnimeRepositoryCrud {
       public static void create(Anime anime){
        try (Connection conn = ConnectionFactory.getConnection();
                PreparedStatement ps = createPreparedStatement(conn, anime)) {
            ps.execute();
            Logger.getLogger(AnimeRepositoryCrud.class.getName())
                    .log(Level.INFO, "Database rows affected: {0}", anime.getName());
        } catch (SQLException ex) {
            Logger.getLogger(AnimeRepositoryCrud.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
    }
    
    private static PreparedStatement createPreparedStatement(Connection conn, Anime anime) throws SQLException{
        String sql = "INSERT INTO anime_store.anime (name, episodes, producer_id) VALUES (?, ?, ?);";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, anime.getName());
        ps.setInt(2, anime.getEpisodes());
        ps.setInt(3, anime.getProducer().getId());
        return ps;
    }
    
    public static List<Anime> read(String name){
        List<Anime> animes = new ArrayList<>();
        
        try(Connection conn = ConnectionFactory.getConnection();
            PreparedStatement ps = readPreparedStatement(conn, name);
            ResultSet rs = ps.executeQuery()) {
 
            Logger.getLogger(AnimeRepositoryCrud.class.getName())
                    .log(Level.INFO, "Finding animes by name"); 
            
            while(rs.next()){
                Producer producer = Producer.builder()
                        .name(rs.getString("producer_name"))
                        .id(rs.getInt("producer_id"))
                        .build();
                Anime anime = Anime.builder()
                        .id(rs.getInt("id"))
                        .name(rs.getString("name"))
                        .episodes(rs.getInt("episodes"))
                        .producer(producer)
                        .build();
                animes.add(anime);
            }

        } catch (SQLException ex) {
            Logger.getLogger(AnimeRepositoryCrud.class.getName())
                    .log(Level.SEVERE, null, ex);
        } 
        return animes;
    }
    
    private static PreparedStatement readPreparedStatement(Connection conn, String name) throws SQLException{
        String sql = "SELECT a.id, a.name, a.episodes, a.producer_id, p.name as 'producer_name' "
                + "FROM anime_store.anime a "
                + "INNER JOIN anime_store.producer p "
                + "ON a.producer_id = p.id "
                + "WHERE a.name LIKE ?;";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, String.format("%%%s%%", name));
        return ps;
    }
    
    public static Optional<Anime> findById(Integer id){
        try(Connection conn = ConnectionFactory.getConnection();
            PreparedStatement ps = findByIdPreparedStatement(conn, id);
            ResultSet rs = ps.executeQuery()) {
 
            Logger.getLogger(AnimeRepositoryCrud.class.getName())
                    .log(Level.INFO, "Finding animes by name"); 
            
            if(!rs.next()) return Optional.empty();
            Producer producer = Producer.builder()
                    .name(rs.getString("producer_name"))
                    .id(rs.getInt("producer_id"))
                    .build();
            Anime anime = Anime.builder()
                    .id(rs.getInt("id"))
                    .name(rs.getString("name"))
                    .episodes(rs.getInt("episodes"))
                    .producer(producer)
                    .build();
            return Optional.of(anime);
        } catch (SQLException ex) {
            Logger.getLogger(AnimeRepositoryCrud.class.getName())
                    .log(Level.SEVERE, null, ex);
        } 
        return null;
    }
    
    private static PreparedStatement findByIdPreparedStatement(Connection conn, Integer id) throws SQLException{
               String sql = "SELECT a.id, a.name, a.episodes, a.producer_id, p.name as 'producer_name' "
                + "FROM anime_store.anime a "
                + "INNER JOIN anime_store.producer p "
                + "ON a.producer_id = p.id"
                + "WHERE a.id = ?;";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, id);
        return ps;
    }
    
    public static void update(Anime anime){
        try (Connection conn = ConnectionFactory.getConnection();
                PreparedStatement ps = updatePreparedStatement(conn, anime)) {
            ps.executeUpdate();
            Logger.getLogger(AnimeRepositoryCrud.class.getName())
                    .log(Level.INFO, "Database rows affected: {0}", anime.getName());
        } catch (SQLException ex) {
            Logger.getLogger(AnimeRepositoryCrud.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
    }
    
    private static PreparedStatement updatePreparedStatement(Connection conn, Anime anime) throws SQLException{
        String sql = "UPDATE anime_store.anime SET name = ?, episodes = ? WHERE id = ?;";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, anime.getName());
        ps.setInt(2, anime.getEpisodes());
        ps.setInt(3, anime.getId());
        return ps;
    }

    public static void delete(int id) {
        try (Connection conn = ConnectionFactory.getConnection();
                PreparedStatement ps = preparedStatementDelete(conn, id)) {
            ps.execute();
            Logger.getLogger(AnimeRepositoryCrud.class.getName())
                    .log(Level.INFO, "Database rows affected: {0}", id);
        } catch (SQLException ex) {
            Logger.getLogger(AnimeRepositoryCrud.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
    }

    private static PreparedStatement preparedStatementDelete(Connection conn, int id) throws SQLException {
        String sql = "DELETE FROM anime_store.anime WHERE (id = ?);";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, id);
        return ps;
    }
}
