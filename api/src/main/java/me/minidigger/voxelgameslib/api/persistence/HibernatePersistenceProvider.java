package me.minidigger.voxelgameslib.api.persistence;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.reflections.Reflections;

import java.util.Optional;
import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import me.minidigger.voxelgameslib.api.user.User;

import lombok.extern.java.Log;

/**
 * Created by Martin on 29.01.2017.
 */
@Log
public class HibernatePersistenceProvider implements PersistenceProvider {

    private SessionFactory sessionFactory;

    @Override
    public void start() {
        // A SessionFactory is set up once for an application!
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure() // configures settings from hibernate.cfg.xml, we don't really want that
                .build(); // TODO load settings from our config

        MetadataSources sources = new MetadataSources(registry);

        new Reflections().getTypesAnnotatedWith(Entity.class).forEach(sources::addAnnotatedClass);

        try {
            Metadata metadata = sources.buildMetadata();
            sessionFactory = metadata.buildSessionFactory();
        } catch (Exception e) {
            // The registry would be destroyed by the SessionFactory, but we had trouble building the SessionFactory
            // so destroy it manually.
            StandardServiceRegistryBuilder.destroy(registry);
            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(User user) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        session.save(user);

        session.getTransaction().commit();
        session.close();
    }

    @Override
    public Optional<User> loadUser(UUID id) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        CriteriaBuilder builder = sessionFactory.getCriteriaBuilder();
        CriteriaQuery<User> query = builder.createQuery(User.class);
        Root<User> root = query.from(User.class); // USES A USER DATA OBJECT TO FIX STUFF!
        query.select(root).where(builder.equal(root.get("uuid"), id));

        Optional<User> result;
        try {
            User user = session.createQuery(query).getSingleResult();
            result = Optional.of(user);
        } catch (NoResultException ex) {
            result = Optional.empty();
        }
        session.getTransaction().commit();
        session.close();

        return result;
    }

    @Override
    public void stop() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }
}
