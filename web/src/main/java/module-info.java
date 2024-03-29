module taxi.company.web.main {
    requires spring.web;
    requires spring.boot.autoconfigure;
    requires spring.boot;
    requires spring.context;
    requires spring.core;
    requires spring.beans;

    requires taxi.company.database.main;
    requires java.validation;
    requires org.apache.tomcat.embed.core;

    opens pt.ipvc to spring.core;
    opens pt.ipvc.controllers to spring.beans;

    exports pt.ipvc;
    exports pt.ipvc.controllers;
    exports pt.ipvc.models;
}