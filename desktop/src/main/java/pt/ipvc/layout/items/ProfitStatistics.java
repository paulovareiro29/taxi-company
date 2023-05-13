package pt.ipvc.layout.items;

import javafx.scene.layout.VBox;
import pt.ipvc.base.UIComponent;
import pt.ipvc.bll.PaymentBLL;
import pt.ipvc.components.SmallLabel;
import pt.ipvc.components.Text;
import pt.ipvc.components.card.Card;
import pt.ipvc.dal.Payment;
import pt.ipvc.layout.screen.SpaceBetweenRow;

public class ProfitStatistics extends Card implements UIComponent {

    private final VBox historyContainer;
    private final Text todayCount;
    private final Text weekCount;
    private final Text monthCount;
    private final Text totalCount;

    public ProfitStatistics() {
        setSpacing(12);

        Text title = new Text("Payment statistics");
        title.setSize(20);

        SmallLabel countLabel = new SmallLabel("Profit amount");

        todayCount = new Text("+0€");
        todayCount.setColor("-fx-color-success");

        weekCount = new Text("+0€");
        weekCount.setColor("-fx-color-success");

        monthCount = new Text("+0€");
        monthCount.setColor("-fx-color-success");

        totalCount = new Text("+0€");
        totalCount.setColor("-fx-color-success");

        VBox countBox = new VBox();
        countBox.getChildren()
                .addAll(countLabel, new SpaceBetweenRow(new Text("Today"), todayCount),
                                    new SpaceBetweenRow(new Text("Last 7 days"), weekCount),
                                    new SpaceBetweenRow(new Text("Last month"), monthCount),
                                    new SpaceBetweenRow(new Text("All time"),totalCount));

        SmallLabel historyLabel = new SmallLabel("Last 10 payments");
        historyContainer = new VBox();

        VBox box = new VBox();
        box.getChildren().addAll(historyLabel, historyContainer);

        getChildren().addAll(title, countBox, box);
    }


    @Override
    public void update() {
        todayCount.setText("+" + PaymentBLL.todayProfit() + "€");
        weekCount.setText("+" + PaymentBLL.weekProfit() + "€");
        monthCount.setText("+" + PaymentBLL.monthProfit() + "€");
        totalCount.setText("+" + PaymentBLL.totalProfit() + "€");

        historyContainer.getChildren().clear();
        for(Payment payment : PaymentBLL.getLast10()) {
            Text profit = new Text("+" + payment.getAmount() + "€");
            profit.setColor("-fx-color-success");

            historyContainer.getChildren().add(new SpaceBetweenRow(new Text(payment.getCreatedAt().toString()), profit));
        }
    }
}
