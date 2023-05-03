package pt.ipvc.layout.brands;

import javafx.geometry.Insets;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import pt.ipvc.base.EventListener;
import pt.ipvc.base.Popup;
import pt.ipvc.bll.ModelBLL;
import pt.ipvc.components.buttons.Button;
import pt.ipvc.components.buttons.ButtonSize;
import pt.ipvc.components.card.Card;
import pt.ipvc.components.card.CardTitle;
import pt.ipvc.dal.Brand;
import pt.ipvc.dal.Model;
import pt.ipvc.handlers.SceneHandler;
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

        /* POPUP */
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

        options.getChildren().addAll(addModelButton);
        getChildren().addAll(title, container, options);

        refresh();
    }


    public void refresh() {
        container.getChildren().clear();
        List<Model> models = ModelBLL.getByBrand(data);
        models.forEach(model -> container.getChildren().add(new ModelItem(this, model)));
    }
}
