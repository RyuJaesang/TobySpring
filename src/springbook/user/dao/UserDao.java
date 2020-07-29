package springbook.user.dao;

import com.sun.corba.se.spi.orbutil.fsm.State;
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

    public void jdbcContextWithStatementStrategy(StatementStrategy stmt) throws SQLException {
        Connection c = null;
        PreparedStatement ps = null;

        try{
            c = dataSource.getConnection();
            ps = stmt.makePreparedStatement(c);
            ps.executeUpdate();
        } catch (SQLException e){
            throw e;
        } finally {
            if(ps != null){ try{ps.close();}catch(SQLException e){} }
            if(c != null){ try{c.close();}catch(SQLException e){} }
        }
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

    public void add(User user) throws SQLException{
//        class AddStatement implements StatementStrategy {
//            User user;
//
//            public AddStatement(User user){
//                this.user = user;
//            }
//
//            @Override
//            public PreparedStatement makePreparedStatement(Connection c) throws SQLException {
//                PreparedStatement ps = c.prepareStatement("INSERT INTO users(id, name, password) VALUES (?, ?, ?)");
//                ps.setString(1, user.getId());
//                ps.setString(2, user.getName());
//                ps.setString(3, user.getPassword());
//                return ps;
//            }
//        }
        AddStatement stmt = new AddStatement(user);
        jdbcContextWithStatementStrategy(stmt);
    }


    public void deleteAll() throws SQLException{
        StatementStrategy stmt = new DeleteAllStatement();
        jdbcContextWithStatementStrategy(stmt);
    }
}
