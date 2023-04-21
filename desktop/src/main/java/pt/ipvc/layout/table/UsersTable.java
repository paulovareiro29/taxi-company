package pt.ipvc.layout.table;

import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.cell.PropertyValueFactory;
import pt.ipvc.base.table.Table;
import pt.ipvc.base.table.TableColumn;
import pt.ipvc.bll.UserBLL;
import pt.ipvc.dal.User;

import java.util.List;
import java.util.stream.Collectors;

public class UsersTable extends Table<User> {

    private String nameFilter;
    private String roleFilter;

    public UsersTable() {
        TableColumn<User, String> nameColumn = new TableColumn<>("Name");
        TableColumn<User, String> emailColumn = new TableColumn<>("Email");
        TableColumn<User, String> joinedAtColumn = new TableColumn<>("Joined At");
        TableColumn<User, String> roleColumn = new TableColumn<>("Role");
        getColumns().addAll(nameColumn, emailColumn, joinedAtColumn, roleColumn);

        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        joinedAtColumn.setCellValueFactory(new PropertyValueFactory<>("createdAt"));
        roleColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getRole().getName()));


        addFilter(u -> nameFilter == null || u.getName().contains(nameFilter));
        addFilter(u -> roleFilter == null || u.getRole().getName().equals(roleFilter));

        refresh();
    }

    @Override
    public void refresh() {
        List<User> users = UserBLL.index().stream()
                .filter(filters)
                .collect(Collectors.toList());

        updateData(users);
        update();
    }

    public void update() {
        super.update();
    }

    public void filterByName(String name) {
        nameFilter = name;
        refresh();
    }

    public void filterByRole(String role) {
        roleFilter = role;
        refresh();
    }

    public void clearFilters() {
        nameFilter = null;
        roleFilter = null;
    }
}
