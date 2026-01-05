module org.example.datasensefx {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires javafx.swing;

    requires java.sql;
    requires com.zaxxer.hikari;
    requires org.apache.commons.csv;
    requires java.desktop;
    requires com.github.librepdf.openpdf;

    opens org.example.datasensefx to javafx.fxml;
    opens org.example.datasensefx.controllers to javafx.fxml;
    opens org.example.datasensefx.utils to javafx.fxml;

    exports org.example.datasensefx;
    exports org.example.datasensefx.controllers;
    exports org.example.datasensefx.utils;
    exports org.example.datasensefx.model;
    exports org.example.datasensefx.services;
}