package ru.job4j.cars.store;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.job4j.cars.model.Brand;
import ru.job4j.cars.model.Post;
import ru.job4j.cars.model.User;

import java.util.List;
import java.util.function.Function;

public class HbmStore implements Store, AutoCloseable {

    private final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure().build();
    private final SessionFactory sf = new MetadataSources(registry)
            .buildMetadata().buildSessionFactory();

    private static final class Lazy {
        private static final Store INST = new HbmStore();
    }

    public static Store instOf() {
        return Lazy.INST;
    }

    private <T> T tx(final Function<Session, T> command) {
        final Session session = sf.openSession();
        final Transaction tx = session.beginTransaction();
        try {
            T rsl = command.apply(session);
            tx.commit();
            return rsl;
        } catch (final Exception e) {
            session.getTransaction().rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    @Override
    public List<Post> findAllPost() {
        return tx(session -> session.createQuery(
                "from Post"
        ).list());
    }

    @Override
    public List<Post> findTodayPost() {
        return tx(session -> session.createQuery(
                "from Post p where p.created > 'today'"
        ).list());
    }

    @Override
    public List<Post> findPostWithPhoto() {
        return tx(session -> session.createQuery(
                "from Post p where p.photo is not null"
        ).list());
    }

    @Override
    public List<Post> findPostWithBrand(Brand brand) {
        return tx(session -> session.createQuery(
                "from Post p where p.brand =: brand"
        ).setParameter("brand", brand).list());
    }

    @Override
    public Post findPostById(int id) {
        return tx(session -> session.get(Post.class, id));
    }

    @Override
    public boolean save(Post post) {
        return tx(session -> {
            session.save(post);
            return true;
        });
    }

    @Override
    public boolean delete(int id) {
        return tx(session -> {
            Post post = session.get(Post.class, id);
            session.delete(post);
            return true;
        });
    }

    @Override
    public List<User> findAllUser() {
        return tx(session -> session.createQuery("FROM User").list());
    }

    public User findUserByEmail(String email) {
        return tx(session ->
                (User) session.createQuery("from User where email = :email")
                        .setParameter("email", email)
                        .uniqueResult()
        );
    }

    @Override
    public boolean save(User user) {
        return tx(session -> {
            session.saveOrUpdate(user);
            return true;
        });
    }

    @Override
    public void close() {
        StandardServiceRegistryBuilder.destroy(registry);
    }
}
