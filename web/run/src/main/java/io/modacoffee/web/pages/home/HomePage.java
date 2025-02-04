package io.modacoffee.web.pages.home;

import io.modacoffee.web.pages.ModaCoffeeWebPage;
import io.modacoffee.web.pages.menu.MenuPage;
import io.modacoffee.web.pages.order.status.OrderStatusPage;
import io.modacoffee.web.panels.card.cards.vertical.VerticalCard;
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

        add(VerticalCard.builder("customer-explore-card")
            .row(row -> row.title(getString("customer-explore-title")))
            .row(row -> row.text(getString("customer-explore-text")))
            .row(row -> row.buttonLink("Explore", MenuPage.class))
            .build());

        add(VerticalCard.builder("employee-explore-card")
            .row(row -> row.title(getString("employee-explore-title")))
            .row(row -> row.text(getString("employee-explore-text")))
            .row(row -> row.buttonLink("Explore", OrderStatusPage.class))
            .build());
    }
}
