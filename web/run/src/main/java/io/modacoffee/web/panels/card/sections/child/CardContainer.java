package io.modacoffee.web.panels.card.sections.child;

import io.modacoffee.web.components.ComponentFactory;
import io.modacoffee.web.panels.card.sections.CardSection;
import org.apache.wicket.Component;

public class CardContainer extends CardSection
{
    public CardContainer(final String id, ComponentFactory<Component> factory)
    {
        super(id);

        add(factory.newComponent("child"));
    }
}
