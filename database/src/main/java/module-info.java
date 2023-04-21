module taxi.company.database.main {
    requires jakarta.persistence;
    requires jakarta.xml.bind;
    requires org.hibernate.orm.core;
    requires org.hibernate.commons.annotations;
    requires com.fasterxml.classmate;
    requires net.bytebuddy;
    opens pt.ipvc.dal to org.hibernate.orm.core;
    exports pt.ipvc.bll;
    exports pt.ipvc.exceptions;
    exports pt.ipvc.dal;
}