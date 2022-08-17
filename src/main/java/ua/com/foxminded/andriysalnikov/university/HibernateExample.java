package ua.com.foxminded.andriysalnikov.university;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import ua.com.foxminded.andriysalnikov.university.h_model.Teacher;

import java.util.List;

public class HibernateExample {

    public static void main(String[] args) {

        SessionFactory factory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Teacher.class)
                .buildSessionFactory();

        Session session = factory.getCurrentSession();
//        Teacher teacher = new Teacher("Gulka", "Gdanskaya");
//        session.beginTransaction();
//        session.persist(teacher);
//        session.getTransaction().commit();

//        Teacher teacher1 = new Teacher("Zhopa", "Sruchkoi");
//        session.beginTransaction();
//        session.persist(teacher1);
//        session.getTransaction().commit();
//        System.out.println(teacher1);

        session.beginTransaction();
        List<Teacher> teachers = session.createQuery("from Teacher", Teacher.class).getResultList();
        for (Teacher teacher : teachers) {
            System.out.println(teacher);
        }
        session.getTransaction().commit();

        factory.close();



    }

}
