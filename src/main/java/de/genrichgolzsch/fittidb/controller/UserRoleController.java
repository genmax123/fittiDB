package de.genrichgolzsch.fittidb.controller;

import de.genrichgolzsch.fittidb.dao.UserRoleDao;
import de.genrichgolzsch.fittidb.model.UserRoleView;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.Connection;


public class UserRoleController {

    @FXML private TableView<UserRoleView> table;
    @FXML private TableColumn<UserRoleView, Integer> colUserId;
    @FXML private TableColumn<UserRoleView, String> colUsername;
    @FXML private TableColumn<UserRoleView, String> colRole;
    @FXML private TableColumn<UserRoleView, String> colDescription;
    @FXML private TableColumn<UserRoleView, Boolean> colActive;

    private final ObservableList<UserRoleView> data = FXCollections.observableArrayList();
    private UserRoleDao dao;

    public void setConnection(Connection conn) {
        this.dao = new UserRoleDao(conn);
        load();
    }

    @FXML
    public void initialize() {
        colUserId.setCellValueFactory(new PropertyValueFactory<>("userId"));
        colUsername.setCellValueFactory(new PropertyValueFactory<>("username"));
        colRole.setCellValueFactory(new PropertyValueFactory<>("roleName"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("roleDescription"));
        colActive.setCellValueFactory(new PropertyValueFactory<>("active"));

        table.setItems(data);
    }

    @FXML
    private void onRefresh() {
        load();
    }

    private void load() {
        try {
            data.setAll(dao.findAll());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
