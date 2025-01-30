package io.modacoffee.web.pages.home;

import io.modacoffee.web.pages.ModaCoffeeWebPage;
import io.modacoffee.web.pages.menu.MenuPage;
import io.modacoffee.web.pages.order.status.OrderStatusPage;
import io.modacoffee.web.panels.card.CardPanel;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import java.io.Serial;
import java.util.List;

import static io.modacoffee.web.panels.card.CardPanelButton.ButtonSettings.button;

public class HomePage extends ModaCoffeeWebPage
{
    @Serial
    private static final long serialVersionUID = 1L;

    public HomePage(PageParameters parameters)
    {
        super(parameters);

        add(new Image("background", imageResource(getClass(), "resources/ModaCoffeeBackground.jpg")));

        add(new CardPanel("customer-explore-card", new CardPanel.Settings(
            getString("customer-explore-title"),
            getString("customer-explore-body"),
            null,
            List.of(button(getString("customer-explore-button-label"), MenuPage.class)))));

        add(new CardPanel("employee-explore-card", new CardPanel.Settings(
            getString("employee-explore-title"),
            getString("employee-explore-body"),
            null,
            List.of(button(getString("employee-explore-button-label"), OrderStatusPage.class)))));
    }
}
