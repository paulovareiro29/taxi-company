package pt.ipvc.layout.table;

import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.TableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import pt.ipvc.base.EventListener;
import pt.ipvc.base.table.ButtonIconTableCell;
import pt.ipvc.base.table.Table;
import pt.ipvc.base.table.TableColumn;
import pt.ipvc.bll.RoleBLL;
import pt.ipvc.bll.SessionBLL;
import pt.ipvc.bll.UserBLL;
import pt.ipvc.dal.User;
import pt.ipvc.handlers.SceneHandler;
import pt.ipvc.layout.popup.user.UpdateUserPopup;

import java.util.List;
import java.util.stream.Collectors;

public class UsersTable extends Table<User> {

    private String nameFilter;
    private String emailFilter;
    private String roleFilter;
    private final UpdateUserPopup editPopup;

    public UsersTable() {
        editPopup = new UpdateUserPopup(new EventListener() {
            @Override
            public void onSuccess() {
                refresh();
            }

            @Override
            public void onFail() {}

            @Override
            public void onCancel() {}
        });

        TableColumn<User, String> nameColumn = new TableColumn<>("Name");
        TableColumn<User, String> emailColumn = new TableColumn<>("Email");
        TableColumn<User, String> joinedAtColumn = new TableColumn<>("Joined At");
        TableColumn<User, String> roleColumn = new TableColumn<>("Role");
        TableColumn<User, String> settingsColumn = new TableColumn<>("");
        settingsColumn.setPrefWidth(64);
        settingsColumn.setMinWidth(64);
        settingsColumn.setMaxWidth(64);

        getColumns().addAll(nameColumn, emailColumn, joinedAtColumn, roleColumn, settingsColumn);

        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        joinedAtColumn.setCellValueFactory(new PropertyValueFactory<>("createdAt"));
        roleColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getRole().getName()));
        settingsColumn.setCellValueFactory(data -> new SimpleStringProperty(""));
        settingsColumn.setCellFactory(data -> {

            ButtonIconTableCell<User> cell = new ButtonIconTableCell<>("settings.png");
            cell.setOnClick(event -> {
                User user = cell.getTableView().getItems().get(cell.getIndex());

                boolean visible = !(SessionBLL.getAuthenticatedUser().getRole().getName().equals(RoleBLL.getSecretaryRole().getName())
                        && user.getRole().getName().equalsIgnoreCase(RoleBLL.getAdminRole().getName()));

                if(!visible) cell.setGraphic(null);

                if(!visible) return;

                editPopup.setUser(user);
                editPopup.show(SceneHandler.stage);
            });

            return cell;
        });


        addOrFilter(u -> u.getName().toLowerCase().contains(nameFilter != null ? nameFilter : ""));
        addOrFilter(u -> u.getEmail().toLowerCase().contains(emailFilter != null ? emailFilter : ""));
        addAndFilter(u -> u.getRole().getName().toLowerCase().contains(roleFilter != null ? roleFilter : ""));

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

    public void setNameFilter(String name) {
        nameFilter = name;
    }

    public void setEmailFilter(String email) {
        emailFilter = email;
    }

    public void setRoleFilter(String role) {
        roleFilter = role;
    }

    public void clearFilters() {
        nameFilter = null;
        roleFilter = null;
    }
}
