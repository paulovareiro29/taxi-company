package pt.ipvc.views.screens;

import javafx.scene.Group;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import pt.ipvc.base.Screen;
import pt.ipvc.bll.BrandBLL;
import pt.ipvc.components.Heading;
import pt.ipvc.components.ScrollPane;
import pt.ipvc.components.buttons.Button;
import pt.ipvc.components.inputs.TextField;
import pt.ipvc.layout.brands.BrandItem;
import pt.ipvc.layout.screen.ScreenHeader;


public class BrandsScreen extends Screen {

    private VBox container;
    private String filter;

    public BrandsScreen() {

        /* HEADER */
        Heading title = new Heading("Car brands");
        Button newTaxiButton = new Button("Add new");

        TextField taxiFilter = new TextField();
        taxiFilter.setPromptText("Search");
        taxiFilter.setIcon("search--secondary.png");

        Group searchBar = new Group(taxiFilter);

        ScreenHeader header = new ScreenHeader();
        header.addChildrenToLeft(title);
        header.addChildrenToRight(searchBar, newTaxiButton);

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setFitToWidth(true);
        scrollPane.setHbarPolicy(javafx.scene.control.ScrollPane.ScrollBarPolicy.NEVER);

        container = new VBox(8);
        scrollPane.setContent(container);

        /* ADD EVERYTHING TO SCREEN */
        getChildren().addAll(header, scrollPane);

        taxiFilter.getInput().textProperty()
                .addListener((observable, oldValue, newValue) -> {
                    filter = newValue;
                    refresh();
                });

        refresh();
    }

    private void refresh() {
        container.getChildren().clear();
        BrandBLL.index()
                .stream()
                .filter(b -> b.getName().toLowerCase().contains(filter != null ? filter : ""))
                .forEach(brand -> {
            container.getChildren().add(new BrandItem(brand));
        });
    }

    @Override
    public void update() {
        refresh();
    }
}