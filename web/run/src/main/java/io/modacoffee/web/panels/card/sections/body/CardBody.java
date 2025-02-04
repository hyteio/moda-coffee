package io.modacoffee.web.panels.card.sections.body;

import io.modacoffee.web.components.ComponentFactory;
import io.modacoffee.web.panels.card.sections.CardSection;
import org.apache.wicket.Component;

public class CardBody extends CardSection
{
    public CardBody(final String id, ComponentFactory<Component> factory)
    {
        super(id);

        add(factory.newComponent( "child"));
    }
}
