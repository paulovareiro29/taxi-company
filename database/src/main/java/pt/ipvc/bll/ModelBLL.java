package pt.ipvc.bll;

import pt.ipvc.dal.Brand;
import pt.ipvc.dal.Model;
import pt.ipvc.database.Database;
import pt.ipvc.exceptions.NameAlreadyExistsException;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

public class ModelBLL {

    public static List<Model> index() {
        return Database.query("model.index").getResultList();
    }

    public static Model get(UUID id) {
        return Database.find(Model.class, id);
    }

    public static void create(Brand brand, String name) throws NameAlreadyExistsException {
        if(getByName(name) != null)
            throw new NameAlreadyExistsException();

        Model model = new Model();
        model.setBrand(brand);
        model.setName(name);


        Database.beginTransaction();
        Database.insert(model);
        Database.commitTransaction();
    }

    public static void update(Model entity) {
        Database.beginTransaction();
        Database.update(entity);
        Database.commitTransaction();
    }

    public static void remove(UUID id) {
        Model entity = get(id);
        entity.setDeletedAt(Timestamp.from(Instant.now()));

        Database.beginTransaction();
        Database.update(entity);
        Database.commitTransaction();
    }

    public static int count() {
        return ((Long) Database.query("model.count").getSingleResult()).intValue();
    }

    public static Model getByName(String name) {
        return (Model) Database.query("model.get_by_name")
                .setParameter("name", name)
                .getResultStream().findFirst().orElse(null);
    }

    public static List<Model> getByBrand(Brand brand) {
        return Database.query("model.get_by_brand")
                .setParameter("brand_id", brand.getId().toString())
                .getResultList();
    }

}
