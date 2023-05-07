package pt.ipvc.views.screens;

import javafx.scene.Group;
import pt.ipvc.base.ComboItem;
import pt.ipvc.base.EventListener;
import pt.ipvc.base.Popup;
import pt.ipvc.base.Screen;
import pt.ipvc.bll.BookingBLL;
import pt.ipvc.bll.BookingStateBLL;
import pt.ipvc.bll.PaymentTypeBLL;
import pt.ipvc.components.Heading;
import pt.ipvc.components.buttons.Button;
import pt.ipvc.components.inputs.ComboBox;
import pt.ipvc.components.inputs.TextField;
import pt.ipvc.handlers.SceneHandler;
import pt.ipvc.layout.EmptyState;
import pt.ipvc.layout.popup.booking.CreateBookingPopup;
import pt.ipvc.layout.popup.paymenttype.CreatePaymentTypePopup;
import pt.ipvc.layout.screen.ScreenHeader;
import pt.ipvc.layout.table.BookingsTable;
import pt.ipvc.layout.table.PaymentTypesTable;
import pt.ipvc.utils.StringUtils;

import java.util.List;
import java.util.stream.Collectors;


public class PaymentTypesScreen extends Screen {

    private final PaymentTypesTable table;
    private final EmptyState emptyState;

    public PaymentTypesScreen() {

        /* TABLE */
        table = new PaymentTypesTable();
        emptyState = new EmptyState("No payment types found");

        /* HEADER */
        Heading title = new Heading("Payment Types");
        Button newTypeButton = new Button("Add new");


        TextField filter = new TextField();
        filter.setPromptText("Search");
        filter.setIcon("search--secondary.png");

        Group searchBar = new Group(filter);

        ScreenHeader header = new ScreenHeader();
        header.addChildrenToLeft(title);
        header.addChildrenToRight(searchBar, newTypeButton);

        /* FILTER */
        filter.getInput().textProperty()
                .addListener((observable, oldValue, newValue) -> {
                    table.setNameFilter(newValue);
                    table.refresh();
                });

        /* POPUP */
        Popup createPopup = new CreatePaymentTypePopup(new EventListener() {
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

        newTypeButton.setOnAction(e -> createPopup.show(SceneHandler.stage));

        /* ADD EVERYTHING TO SCREEN */
        getChildren().addAll(header, emptyState, table);
    }

    @Override
    public void update() {
        int count = PaymentTypeBLL.count();
        table.setVisible(count != 0);
        emptyState.setVisible(count == 0);
    }
}