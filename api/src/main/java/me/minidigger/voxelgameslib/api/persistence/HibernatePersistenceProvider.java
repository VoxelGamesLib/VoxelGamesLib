package me.minidigger.voxelgameslib.api.persistence;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.reflections.Reflections;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;

import me.minidigger.voxelgameslib.api.lang.Locale;
import me.minidigger.voxelgameslib.api.user.User;
import me.minidigger.voxelgameslib.api.user.UserData;

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
            StandardServiceRegistryBuilder.destroy(registry);
            e.printStackTrace();
        }
    }

    @Override
    public void saveUserData(User user) {
        session((SessionExecutor<Void>) session -> {
            session.saveOrUpdate(user.getData());
            return null;
        });
    }

    @Override
    public Optional<UserData> loadUserData(UUID id) {
        return session(session -> Optional.ofNullable(session.get(UserData.class, id.toString())));
    }

    @Override
    public void saveLocale(Locale locale) {
        session((SessionExecutor<Void>) session -> {
            session.saveOrUpdate(locale);
            return null;
        });
    }

    @Override
    public List<Locale> loadLocales() {
        return session(session -> {
            CriteriaQuery<Locale> criteriaQuery = session.getCriteriaBuilder().createQuery(Locale.class);
            CriteriaQuery<Locale> select = criteriaQuery.select(criteriaQuery.from(Locale.class));
            TypedQuery<Locale> typedQuery = session.createQuery(select);
            return typedQuery.getResultList();
        });
    }

    private <T> T session(SessionExecutor<T> executor) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        T t = executor.execute(session);

        session.getTransaction().commit();
        session.close();

        return t;
    }

    interface SessionExecutor<T> {
        T execute(Session session);
    }

    @Override
    public void stop() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }
}
