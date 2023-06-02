module taxi.company.web.main {
    requires spring.web;
    requires spring.boot.autoconfigure;
    requires spring.boot;
    requires spring.context;
    requires spring.core;
    requires spring.beans;

    requires taxi.company.database.main;
    opens pt.ipvc to spring.core;
    opens pt.ipvc.controllers to spring.beans;

    exports pt.ipvc;
    exports pt.ipvc.controllers;
}