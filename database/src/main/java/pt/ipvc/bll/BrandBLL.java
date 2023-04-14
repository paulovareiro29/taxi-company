package pt.ipvc.bll;

import pt.ipvc.dal.Brand;
import pt.ipvc.database.Database;

import java.util.List;
import java.util.UUID;


public class BrandBLL {

    public static List<Brand> index() {
        return Database.query("brand.index").getResultList();
    }

    public static Brand get(UUID id) {
        return Database.find(Brand.class, id);
    }

    public static void create(Brand entity) {
        Database.beginTransaction();
        Database.insert(entity);
        Database.commitTransaction();
    }

    public static void update(Brand entity) {
        Database.beginTransaction();
        Database.update(entity);
        Database.commitTransaction();
    }

    public static void remove(UUID id) {
        Brand entity = get(id);

        Database.beginTransaction();
        Database.delete(entity);
        Database.commitTransaction();
    }

    public static int count() {
        return ((Long) Database.query("brand.count").getSingleResult()).intValue();
    }

    public static Brand getByName(String name) {
        return (Brand) Database.query("brand.get_by_name")
                .setParameter("name", name)
                .getSingleResult();
    }

}
