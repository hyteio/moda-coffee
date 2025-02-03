package io.modacoffee.web.panels.card.text;

import io.modacoffee.web.panels.card.CardSection;
import org.apache.wicket.markup.html.basic.Label;

public class CardText extends CardSection
{
    public CardText(final String id, String text)
    {
        super(id);

        add(new Label("text", text));
    }
}

