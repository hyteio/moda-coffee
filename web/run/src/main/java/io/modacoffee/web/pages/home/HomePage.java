package io.modacoffee.web.pages.home;

import io.modacoffee.web.pages.ModaCoffeeWebPage;
import io.modacoffee.web.pages.menu.MenuPage;
import io.modacoffee.web.pages.order.status.OrderStatusPage;
import io.modacoffee.web.panels.card.CardPanel;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import java.io.Serial;

public class HomePage extends ModaCoffeeWebPage
{
    @Serial
    private static final long serialVersionUID = 1L;

    public HomePage(PageParameters parameters)
    {
        super(parameters);

        add(new Image("background", imageResource(getClass(), "resources/ModaCoffeeBackground.jpg")));

        add(CardPanel.builder("customer-explore-card")
            .title(getString("customer-explore-title"))
            .text(getString("customer-explore-text"))
            .buttonLink(getString("customer-explore-button-label"), MenuPage.class)
            .build());

        add(CardPanel.builder("employee-explore-card")
            .title(getString("employee-explore-title"))
            .text(getString("employee-explore-text"))
            .buttonLink(getString("employee-explore-button-label"), OrderStatusPage.class)
            .build());
    }
}
