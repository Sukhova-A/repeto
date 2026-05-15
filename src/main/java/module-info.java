module com.anastasiia.repeto {
    requires javafx.controls;
    requires javafx.fxml;
    requires static lombok;
    requires java.sql;
    requires org.xerial.sqlitejdbc;


    opens com.anastasiia.repeto to javafx.fxml;
    exports com.anastasiia.repeto;
    exports com.anastasiia.repeto.service;
    exports com.anastasiia.repeto.controller;
    exports com.anastasiia.repeto.model;
    opens com.anastasiia.repeto.service to javafx.fxml;
    opens com.anastasiia.repeto.controller to javafx.fxml;
}