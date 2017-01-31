package me.minidigger.voxelgameslib.api.persistence;

import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.spi.PersistenceProviderResolverHolder;

import lombok.extern.java.Log;

/**
 * Created by Martin on 29.01.2017.
 */
@Log
public class HibernatePersistenceProvider implements PersistenceProvider {

    private EntityManagerFactory entityManagerFactory;

    @Override
    public void start() {
        log.info("Create factory");

        // use bukkit class loader to load the persistence.xml
//        ClassLoader backup = Thread.currentThread().getContextClassLoader();
//        Thread.currentThread().setContextClassLoader(getClass().getClassLoader());
        PersistenceProviderResolverHolder.getPersistenceProviderResolver().getPersistenceProviders();
//        try {
//            Persistence.createEntityManagerFactory("GimmeDaError");
//        } catch (Exception ignored) {
//        }
//        Thread.currentThread().setContextClassLoader(backup);
//
//        try {
//            for (Method method : Class.forName("javax.persistence.Table", true, backup).getDeclaredMethods()) {
//                System.out.println(method.getName());
//            }
//            System.out.println("....");
//            for (Method method : Class.forName("javax.persistence.Table", true, getClass().getClassLoader()).getDeclaredMethods()) {
//                System.out.println(method.getName());
//            }
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
        entityManagerFactory = Persistence.createEntityManagerFactory("test123");
    }

    public void test() {
        log.info("start test");
        // create a couple of events...
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(new Event("Our very first event!", new Date()));
        entityManager.persist(new Event("A follow up event", new Date()));
        entityManager.getTransaction().commit();
        entityManager.close();

        log.info("pull stuff");
        // now lets pull events from the database and list them
        entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        List<Event> result = entityManager.createQuery("from Event", Event.class).getResultList();
        for (Event event : result) {
            System.out.println("Event (" + event.getDate() + ") : " + event.getTitle());
        }
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    @Override
    public void stop() {
        entityManagerFactory.close();
    }
}
