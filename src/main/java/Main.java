
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;



public class Main {
	
	private static SessionFactory sessionFactory;

	// La SessionFactory és cara de crear: només n'hi ha UNA per a tota l'aplicació
	public static synchronized SessionFactory getSessionFactory() {
		if (sessionFactory == null) {
			try {
				sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
			} catch (HibernateException e) {
				System.err.println("Error al crear la SessionFactory: " + e.getMessage());
				throw new ExceptionInInitializerError(e);
			}
		}
		return sessionFactory;
	}
	
	public static void main(String args[]) {
		
		sessionFactory = getSessionFactory();
		
	}
}