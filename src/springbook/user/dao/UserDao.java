package springbook.user.dao;

import org.springframework.dao.EmptyResultDataAccessException;
import springbook.user.domain.User;

import javax.sql.DataSource;
import javax.xml.crypto.Data;
import javax.xml.transform.Result;
import java.sql.Connection;
import java.sql.*;

public class UserDao {
    private DataSource dataSource;

    public void setDataSource(DataSource dataSource){
        this.dataSource = dataSource;
    }


    public void add(User user) throws SQLException{
        Connection c = dataSource.getConnection();
        PreparedStatement ps = c.prepareStatement("INSERT INTO users(id, name, password) values(?, ?, ?)");
        ps.setString(1, user.getId());
        ps.setString(2, user.getName());
        ps.setString(3, user.getPassword());

        ps.execute();

        ps.close();
        c.close();
    }

    public void delete(User user) throws SQLException{
        Connection c = dataSource.getConnection();
        PreparedStatement ps = c.prepareStatement("DELETE FROM users WHERE id = ? and name = ? and password = ?");
        ps.setString(1, user.getId());
        ps.setString(2, user.getName());
        ps.setString(3, user.getPassword());

        ps.execute();

        ps.close();
        c.close();
    }

    public User get(String id) throws SQLException{
        Connection c = dataSource.getConnection();
        PreparedStatement ps = c.prepareStatement("SELECT * From users WHERE id = ?");
        ps.setString(1, id);

        ResultSet rs = ps.executeQuery();
        User user = null;

        if(rs.next()) {
            user = new User();
            user.setId(rs.getString("id"));
            user.setName(rs.getString("name"));
            user.setPassword(rs.getString("password"));
        }

        rs.close();
        ps.close();
        c.close();

        if(user==null) throw new EmptyResultDataAccessException(1);

        return user;
    }

    public int getCount() throws SQLException{
        Connection c = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            c = dataSource.getConnection();
            ps = c.prepareStatement("SELECT COUNT(*) FROM users");
            rs = ps.executeQuery();

            rs.next();
            int count = rs.getInt(1);
            return count;
        } catch(SQLException e){
            throw e;
        }finally{
            if(rs != null){
                try {
                    rs.close();
                }catch(SQLException e){

                }
            }
            if(ps != null){
                try{
                    ps.close();
                }catch(SQLException e){

                }
            }
            if(c != null){
                try{
                    c.close();
                }catch(SQLException e){

                }
            }
        }
    }

    public void deleteAll() throws SQLException{
        Connection c = null;
        PreparedStatement ps = null;

        try{
            c = dataSource.getConnection();
            ps = c.prepareStatement("DELETE FROM users");
            ps.execute();
        } catch (SQLException e){
            throw e;
        } finally {
            if(ps != null){
                try{
                    ps.close();
                } catch (SQLException e){

                }
            }
            if(c!= null){
                try{
                    c.close();
                }catch(SQLException e){

                }
            }
        }



        ps.close();
        c.close();
    }
}
