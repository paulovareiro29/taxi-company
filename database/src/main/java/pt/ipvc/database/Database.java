package pt.ipvc.database;

import jakarta.persistence.*;

public class Database {

    private static final String PERSISTENCE_UNIT_NAME = "PROJECT_II_24473_26211" ;
    private static EntityManagerFactory factory;
    private static EntityManager entityManager;

    private static EntityManager getEntityManager() {
        if (entityManager == null || !entityManager.isOpen()) {
            factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
            entityManager = factory.createEntityManager();
        }
        return entityManager;
    }

    public static Query query(String query) {
        return getEntityManager().createNamedQuery(query);
    }

    public static Query customQuery(String query) {
        return getEntityManager().createQuery(query);
    }

    public static <T> T find(Class<T> type, Object key) {
        return getEntityManager().find(type, key);
    }

    public static void beginTransaction() {
        getEntityManager().getTransaction().begin();
    }

    public static void commitTransaction() {
        getEntityManager().getTransaction().commit();
    }

    public static void rollbackTransaction() {
        getEntityManager().getTransaction().rollback();
    }

    public static void insert(Object entity) {
        getEntityManager().persist(entity);
    }

    public static void update(Object entity) {
        getEntityManager().merge(entity);
    }

    public static void delete(Object entity) {
        getEntityManager().remove(entity);
    }

    public static void connect() {
        if(entityManager == null) {
            entityManager = factory.createEntityManager();
        }
    }

    public static void disconnect() {
        entityManager.close();
        entityManager = null;
    }
}
