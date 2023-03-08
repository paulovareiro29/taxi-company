package pt.ipvc.database;

import jakarta.persistence.*;

public class Database {

    private static final String PERSISTENCE_UNIT_NAME = "PROJECT_II_24473_26211" ;
    private static EntityManagerFactory factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
    private static EntityManager entityManager = factory.createEntityManager();

    public static EntityManager getEntityManager() {
        return entityManager;
    }

    public static Query query(String query) {
        return entityManager.createNamedQuery(query);
    }

    public static Query customQuery(String query) {
        return entityManager.createQuery(query);
    }

    public static <T> T find(Class<T> type, Object key) {
        return entityManager.find(type, key);
    }

    public static void beginTransaction() {
        entityManager.getTransaction().begin();
    }

    public static void commitTransaction() {
        entityManager.getTransaction().commit();
    }

    public static void rollbackTransaction() {
        entityManager.getTransaction().rollback();
    }

    public static void insert(Object entity) {
        entityManager.persist(entity);
    }

    public static void update(Object entity) {
        entityManager.merge(entity);
    }

    public static void delete(Object entity) {
        entityManager.remove(entity);
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
