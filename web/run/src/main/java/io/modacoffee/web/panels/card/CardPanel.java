package io.modacoffee.web.panels.card;

import io.modacoffee.web.panels.ModaCofeePanel;
import org.apache.wicket.markup.repeater.RepeatingView;

public class CardPanel extends ModaCofeePanel
{
    public static CardPanelBuilder builder(String id)
    {
        return new CardPanelBuilder(id);
    }

    public CardPanel(final String id, RepeatingView repeater)
    {
        super(id);

        add(repeater);
    }
}
