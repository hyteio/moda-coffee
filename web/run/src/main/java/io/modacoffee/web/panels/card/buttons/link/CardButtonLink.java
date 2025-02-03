package io.modacoffee.web.panels.card.buttons.link;

import io.modacoffee.web.panels.card.CardSection;
import org.apache.wicket.Page;

public class CardButtonLink extends CardSection
{
    public CardButtonLink(final String id, String label, Class<? extends Page> pageClass)
    {
        super(id);

        add(newButtonLink("button", label, pageClass));
    }
}

