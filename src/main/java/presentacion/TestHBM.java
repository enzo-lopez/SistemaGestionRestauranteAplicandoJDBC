package presentacion;

import org.hibernate.Session;
import persistencia.utils.HibernateUtil;

public class TestHBM {
    public static void main(String[] args){
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.close();
        System.out.println("Ok");
    }
}
