package hiber.dao;

import hiber.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.TypedQuery;
import java.util.List;

@Transactional
@Repository
public class UserDaoImp implements UserDao {

   @Autowired
   private SessionFactory sessionFactory;

   @Override
   public void add(User user) {
      sessionFactory.getCurrentSession().save(user);
   }

   @Override
   @SuppressWarnings("unchecked")
   public List<User> listUsers() {
      TypedQuery<User> query=sessionFactory.getCurrentSession().createQuery("from User");
      return query.getResultList();
   }

   @Override
   public User getUsers(String model, int series) {
      Session session = sessionFactory.getCurrentSession();
      String query = "FROM User user LEFT OUTER JOIN FETCH user.car WHERE user.car.series = :series and user.car.model = :model";
      User user = (User) session.createQuery(query)
              .setParameter("series", series)
              .setParameter("model", model)
              .uniqueResult();

      return user;
   }
}
