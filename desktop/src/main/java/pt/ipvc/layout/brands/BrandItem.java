package pt.ipvc.layout.brands;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import pt.ipvc.base.EventListener;
import pt.ipvc.base.Popup;
import pt.ipvc.bll.BrandBLL;
import pt.ipvc.bll.ModelBLL;
import pt.ipvc.components.buttons.Button;
import pt.ipvc.components.buttons.ButtonIcon;
import pt.ipvc.components.buttons.ButtonSize;
import pt.ipvc.components.card.Card;
import pt.ipvc.components.card.CardTitle;
import pt.ipvc.dal.Brand;
import pt.ipvc.dal.Model;
import pt.ipvc.handlers.SceneHandler;
import pt.ipvc.handlers.ScreensEnum;
import pt.ipvc.layout.popup.DangerConfirmationPopup;
import pt.ipvc.layout.popup.brand.UpdateBrandPopup;
import pt.ipvc.layout.popup.model.CreateModelPopup;

import java.util.List;

public class BrandItem extends Card {

    private final Brand data;
    private final VBox container;
    
    public BrandItem(Brand data) {
        this.data = data;
        
        CardTitle title = new CardTitle(data.getName());
        container = new VBox(8);
        container.setPadding(new Insets(8,0,8,8));

        HBox options = new HBox(8);
        Button addModelButton = new Button("Add Model", ButtonSize.SMALL);

        /* CREATE MODEL POPUP */
        Popup createModelPopup = new CreateModelPopup(data, new EventListener() {
            @Override
            public void onSuccess() {
                refresh();
            }

            @Override
            public void onFail() {}

            @Override
            public void onCancel() {}
        });
        addModelButton.setOnAction(e -> createModelPopup.show(SceneHandler.stage));

        HBox brandOptions = new HBox(4);
        HBox.setHgrow(brandOptions, Priority.ALWAYS);
        brandOptions.setAlignment(Pos.CENTER_RIGHT);

        Button removeButton = new ButtonIcon("trash.png");
        removeButton.setStyle("-fx-background-color: -fx-color-danger");

        Button updateButton = new ButtonIcon("pencil.png");
        updateButton.setStyle("-fx-background-color: -fx-color-warning");

        /* REMOVE BRAND POPUP */
        Popup removePopup = new DangerConfirmationPopup(new EventListener() {
            @Override
            public void onSuccess() {
                handleDelete();
            }

            @Override
            public void onFail() {}

            @Override
            public void onCancel() {}
        });
        removeButton.setOnAction(e -> removePopup.show(SceneHandler.stage));

        /* UPDATE BRAND POPUP */
        UpdateBrandPopup updatePopup = new UpdateBrandPopup(new EventListener() {
            @Override
            public void onSuccess() {
                handleUpdate();
            }

            @Override
            public void onFail() {}

            @Override
            public void onCancel() {}
        });
        updatePopup.setBrand(data);
        updateButton.setOnAction(e -> updatePopup.show(SceneHandler.stage));

        brandOptions.getChildren().addAll(updateButton, removeButton);
        options.getChildren().addAll(addModelButton, brandOptions);
        getChildren().addAll(title, container, options);

        refresh();
    }


    public void refresh() {
        container.getChildren().clear();
        List<Model> models = ModelBLL.getByBrand(data);
        models.forEach(model -> container.getChildren().add(new ModelItem(this, model)));
    }

    private void handleDelete() {
        try {
            BrandBLL.remove(data.getId());
            SceneHandler.updateScreen(ScreensEnum.BRANDS);
        }catch(Exception ignore){}
    }

    private void handleUpdate() {
        SceneHandler.updateScreen(ScreensEnum.BRANDS);
    }
}
