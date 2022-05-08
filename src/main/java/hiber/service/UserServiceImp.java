package hiber.service;

import hiber.dao.UserDao;
import hiber.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImp implements UserService {

   @Autowired
   private UserDao userDao;
   @Autowired
   private SessionFactory sessionFactory;

   @Transactional
   @Override
   public void add(User user) {
      userDao.add(user);
   }

   @Transactional(readOnly = true)
   @Override
   public List<User> listUsers() {
      return userDao.listUsers();
   }

   @Override
   public User getUsers(String model, int series) {
      Session session = sessionFactory.openSession();
      String query = "FROM User user LEFT OUTER JOIN FETCH user.car WHERE user.car.series = :series and user.car.model = :model";
      User user = (User) session.createQuery(query)
              .setParameter("series", series)
              .setParameter("model", model)
              .uniqueResult();

      return user;
   }
}
