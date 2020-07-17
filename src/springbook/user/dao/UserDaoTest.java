package springbook.user.dao;

import javafx.application.Application;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

import java.sql.SQLException;

public class UserDaoTest {

    public static void main(String[] args) throws SQLException {

        // Spring의 getBean을 사용하는 경우
        ApplicationContext context = new GenericXmlApplicationContext("springbook/user/applicationContext.xml");
//        ApplicationContext context = new AnnotationConfigApplicationContext(DaoFactory.class);
        UserDao dao = context.getBean("userDao", UserDao.class);

        // 직접 Object 생성의 경우
        DaoFactory factory = new DaoFactory();
        UserDao dao1 = factory.userDao();
        UserDao dao2 = factory.userDao();

        System.out.println(dao1.get("rjs9611").getName());
        System.out.println(dao2.get("rhs9908").getName());
        System.out.println(dao.get("hi").getName());
    }
}
