package io.modacoffee.web.panels.card.sections.text;

import io.modacoffee.web.panels.card.sections.CardSection;
import org.apache.wicket.markup.html.basic.Label;

public class CardText extends CardSection
{
    public CardText(final String id, String text)
    {
        super(id);

        add(new Label("text", text));
    }
}

