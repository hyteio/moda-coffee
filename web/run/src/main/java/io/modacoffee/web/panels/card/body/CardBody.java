package io.modacoffee.web.panels.card.body;

import io.modacoffee.web.components.ComponentFactory;
import io.modacoffee.web.panels.card.CardSection;

public class CardBody extends CardSection
{
    public CardBody(final String id, ComponentFactory childFactory)
    {
        super(id);

        add(childFactory.newComponent("child"));
    }
}

