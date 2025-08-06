package persistencia.dao.implementacion;

import org.hibernate.query.Query;
import persistencia.utils.HibernateUtil;

import modelo.Reserva;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import persistencia.dao.IReservaDao;

import java.util.ArrayList;
import java.util.List;

public class ReservaDaoImpHibernate implements IReservaDao {
    private static Session session;
    private Transaction tx;

    private void iniciaOperacion() throws HibernateException {
        session = HibernateUtil.getSessionFactory().openSession();
        tx = session.beginTransaction();
    }

    private void manejaExcepcion(HibernateException he) throws HibernateException {
        tx.rollback();
        throw new HibernateException("Error en la capa de acceso a datos.");
    }

    @Override
    public Reserva guardarReserva(Reserva reserva) {
        Long id = 0L;
        try {
            iniciaOperacion();
            id = Long.parseLong(session.save(reserva).toString());
            tx.commit();
        } catch (HibernateException he) {
            manejaExcepcion(he);
        } finally {
            session.close();
        }
        reserva.setId(id);
        return reserva;
    }

    @Override
    public List<Reserva> listarReservas() {
        List<Reserva> lista = new ArrayList<Reserva>();
        try {
            iniciaOperacion();
            Query<Reserva> query = session.createQuery(
                    "from Reserva r order by r.id asc", Reserva.class);
            lista = query.list();
        } catch (HibernateException he) {
            manejaExcepcion(he);
        }
        finally {
            session.close();
        }
        return lista;
    }

    @Override
    public List<Reserva> buscarReservaPorNombre(String nombre)  {
        List<Reserva> lista = new ArrayList<Reserva>();
        try {
            iniciaOperacion();
            Query<Reserva> query = session.createQuery(
                    "from Reserva r where r.nombreCliente = :nombre" +
                       " order by r.id asc", Reserva.class)
                    .setParameter("nombre",  nombre);
            lista = query.list();
        } catch (HibernateException he) {
            manejaExcepcion(he);
        }
        finally {
            session.close();
        }
        return lista;
    }

    @Override
    public void eliminarReserva(Long id)  {
        Reserva resAEliminar;
        try {
            iniciaOperacion();
            resAEliminar = session.get(Reserva.class, id);
            session.delete(resAEliminar);
            tx.commit();
        } catch (HibernateException he) {
            manejaExcepcion(he);
        } finally {
            session.close();
        }
    }
}
