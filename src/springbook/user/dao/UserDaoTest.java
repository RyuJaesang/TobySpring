package springbook.user.dao;

import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader;
import javafx.application.Application;
import org.junit.*;
import org.junit.runner.JUnitCore;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import springbook.user.domain.User;

import javax.sql.DataSource;
import java.sql.SQLException;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "../test-applicationContext.xml")
public class UserDaoTest {
    @Autowired
    private ApplicationContext context;

    UserDao dao;

    User user1;
    User user2;
    User user3;

    @Before
    public void setUp(){
        this.dao = context.getBean("userDao", UserDao.class);


        this.user1 = new User("rjs9611", "류재상", "moongle");
        this.user2 = new User("rhs9908", "류효상", "moongle");
        this.user3 = new User("rys0207", "류용상", "moongle");

    }


    @Test
    public void addAndGet() throws SQLException{
        dao.deleteAll();
        assertThat(dao.getCount(), is(0));

        assertThat(dao.getCount(), is(0));

        dao.add(user1);
        dao.add(user2);
        assertThat(dao.getCount(), is(2));

        User test1 = dao.get(user1.getId());
        assertThat(user1.getName(), is(test1.getName()));
        assertThat(user2.getPassword(), is(test1.getPassword()));

        User test2 = dao.get(user2.getId());
        assertThat(user2.getName(), is(test2.getName()));
        assertThat(user2.getPassword(), is(test2.getPassword()));
    }

    @Test
    public void count() throws SQLException{

        dao.deleteAll();
        assertThat(dao.getCount(), is(0));

        dao.add(user1);
        assertThat(dao.getCount(), is(1));

        dao.add(user2);
        assertThat(dao.getCount(), is(2));

        dao.add(user3);
        assertThat(dao.getCount(), is(3));
    }

    @Test(expected=EmptyResultDataAccessException.class)
    public void getUserFailure() throws SQLException{

        dao.deleteAll();
        assertThat(dao.getCount(), is(0));

        dao.get("Unknown_user");
    }
}
