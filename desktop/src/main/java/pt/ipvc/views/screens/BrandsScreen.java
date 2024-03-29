package pt.ipvc.views.screens;

import javafx.scene.Group;
import javafx.scene.layout.VBox;
import pt.ipvc.base.EventListener;
import pt.ipvc.base.Popup;
import pt.ipvc.base.Screen;
import pt.ipvc.bll.BrandBLL;
import pt.ipvc.components.Heading;
import pt.ipvc.components.ScrollPane;
import pt.ipvc.components.buttons.Button;
import pt.ipvc.components.inputs.TextField;
import pt.ipvc.handlers.SceneHandler;
import pt.ipvc.layout.EmptyState;
import pt.ipvc.layout.items.BrandItem;
import pt.ipvc.layout.popup.brand.CreateBrandPopup;
import pt.ipvc.layout.screen.ScreenHeader;


public class BrandsScreen extends Screen {

    private final VBox container;
    private String filter;

    public BrandsScreen() {

        /* HEADER */
        Heading title = new Heading("Car brands");
        Button newBrandButton = new Button("Add new");

        TextField brandFilter = new TextField();
        brandFilter.setPromptText("Search");
        brandFilter.setIcon("search--secondary.png");

        Group searchBar = new Group(brandFilter);

        ScreenHeader header = new ScreenHeader();
        header.addChildrenToLeft(title);
        header.addChildrenToRight(searchBar, newBrandButton);

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setFitToWidth(true);
        scrollPane.setHbarPolicy(javafx.scene.control.ScrollPane.ScrollBarPolicy.NEVER);

        container = new VBox(8);
        scrollPane.setContent(container);


        brandFilter.getInput().textProperty()
                .addListener((observable, oldValue, newValue) -> {
                    filter = newValue;
                    refresh();
                });


        /* POPUP */
        Popup createBrandPopup = new CreateBrandPopup(new EventListener() {
            @Override
            public void onSuccess() {
                refresh();
            }

            @Override
            public void onFail() {}

            @Override
            public void onCancel() {}
        });
        newBrandButton.setOnAction(e -> createBrandPopup.show(SceneHandler.stage));

        refresh();

        /* ADD EVERYTHING TO SCREEN */
        getChildren().addAll(header, scrollPane);
    }

    private void refresh() {
        container.getChildren().clear();
        BrandBLL.index()
                .stream()
                .filter(b -> b.getName().toLowerCase().contains(filter != null ? filter : ""))
                .forEach(brand -> {
            container.getChildren().add(new BrandItem(brand));
        });

        if(container.getChildren().size() == 0){
            container.getChildren().add(new EmptyState("No brands found"));

        }
    }

    @Override
    public void update() {
        refresh();
    }
}