module taxi.company.database.main {
    requires jakarta.persistence;
    requires org.hibernate.orm.core;
    requires jakarta.xml.bind;
    exports pt.ipvc.bll;
}