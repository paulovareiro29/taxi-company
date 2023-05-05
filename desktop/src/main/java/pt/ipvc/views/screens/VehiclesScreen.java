package pt.ipvc.views.screens;

import javafx.scene.Group;
import pt.ipvc.base.ComboItem;
import pt.ipvc.base.EventListener;
import pt.ipvc.base.Popup;
import pt.ipvc.base.Screen;
//import pt.ipvc.bll.ModelBLL;
import pt.ipvc.bll.TaxiBLL;
import pt.ipvc.components.Heading;
import pt.ipvc.components.buttons.Button;
import pt.ipvc.components.inputs.ComboBox;
import pt.ipvc.components.inputs.TextField;
import pt.ipvc.handlers.SceneHandler;
import pt.ipvc.handlers.ScreensEnum;
import pt.ipvc.layout.EmptyState;
import pt.ipvc.layout.screen.ScreenHeader;
import pt.ipvc.layout.table.TaxisTable;
//import pt.ipvc.layout.table.TaxisTable;
//import pt.ipvc.utils.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

public class VehiclesScreen extends Screen {

    private final TaxisTable table;
    private final EmptyState emptyState;

    public VehiclesScreen() {

        /* TABLE */
        table = new TaxisTable();
        emptyState = new EmptyState("No vehicles found");

        /* HEADER */
        Heading title = new Heading("Taxis");
        Button newTaxiButton = new Button("Add new");

        Button brandsButton = new Button("Brands");
        brandsButton.setOnAction(e -> SceneHandler.changeScreen(ScreensEnum.BRANDS));

        TextField taxiFilter = new TextField();
        taxiFilter.setPromptText("Search");
        taxiFilter.setIcon("search--secondary.png");

        Group searchBar = new Group(taxiFilter);

        ScreenHeader header = new ScreenHeader();
        header.addChildrenToLeft(title);
        header.addChildrenToRight(brandsButton, searchBar, newTaxiButton);

        /* ADD EVERYTHING TO SCREEN */
        getChildren().addAll(header, emptyState, table);
    }

    @Override
    public void update() {
        int taxiCount = TaxiBLL.count();
        table.setVisible(taxiCount != 0);
        emptyState.setVisible(taxiCount == 0);
    }
}