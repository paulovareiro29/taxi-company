package pt.ipvc.views.screens;

import javafx.scene.Group;
import pt.ipvc.base.EventListener;
import pt.ipvc.base.Popup;
import pt.ipvc.base.Screen;
import pt.ipvc.bll.BookingBLL;
import pt.ipvc.bll.TaxiBLL;
import pt.ipvc.components.Heading;
import pt.ipvc.components.buttons.Button;
import pt.ipvc.components.inputs.TextField;
import pt.ipvc.handlers.SceneHandler;
import pt.ipvc.handlers.ScreensEnum;
import pt.ipvc.layout.EmptyState;
import pt.ipvc.layout.popup.booking.CreateBookingPopup;
import pt.ipvc.layout.popup.taxi.CreateTaxiPopup;
import pt.ipvc.layout.screen.ScreenHeader;
import pt.ipvc.layout.table.BookingsTable;
import pt.ipvc.layout.table.TaxisTable;


public class BookingsScreen extends Screen {

    private final BookingsTable table;
    private final EmptyState emptyState;

    public BookingsScreen() {

        /* TABLE */
        table = new BookingsTable();
        emptyState = new EmptyState("No bookings found");

        /* HEADER */
        Heading title = new Heading("Bookings");
        Button newTaxiButton = new Button("Add new");


        TextField filter = new TextField();
        filter.setPromptText("Search");
        filter.setIcon("search--secondary.png");

        Group searchBar = new Group(filter);

        ScreenHeader header = new ScreenHeader();
        header.addChildrenToLeft(title);
        header.addChildrenToRight(searchBar, newTaxiButton);

        /* FILTER */
        filter.getInput().textProperty()
                .addListener((observable, oldValue, newValue) -> {
                    table.refresh();
                });

        /* POPUP */
        Popup createPopup = new CreateBookingPopup(new EventListener() {
            @Override
            public void onSuccess() {
                table.setVisible(true);
                emptyState.setVisible(false);
                table.refresh();
            }

            @Override
            public void onFail() {}

            @Override
            public void onCancel() {}
        });

        newTaxiButton.setOnAction(e -> createPopup.show(SceneHandler.stage));

        /* ADD EVERYTHING TO SCREEN */
        getChildren().addAll(header, emptyState, table);
    }

    @Override
    public void update() {
        int count = BookingBLL.count();
        table.setVisible(count != 0);
        emptyState.setVisible(count == 0);
    }
}