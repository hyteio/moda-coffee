package io.modacoffee.web.panels.card.title;

import io.modacoffee.web.panels.card.CardSection;
import org.apache.wicket.markup.html.basic.Label;

public class CardTitle extends CardSection
{
    public CardTitle(final String id, String title)
    {
        super(id);

        add(new Label("title", title));
    }
}

