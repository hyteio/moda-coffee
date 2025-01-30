package io.modacoffee.web.panels.card;

import io.modacoffee.web.panels.ModaCofeePanel;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.request.resource.ResourceReference;

import java.util.List;

import static io.modacoffee.web.panels.card.CardPanelButton.*;

public class CardPanel extends ModaCofeePanel
{

    /**
     * Settings for a card panel, any of which can be null. If the setting is null, that part of the card will be
     * hidden.
     */
    public record Settings(String title,
                           String body,
                           ResourceReference image,
                           List<ButtonSettings> buttons)
    {
    }

    public CardPanel(String id, Settings settings)
    {
        super(id);
        add(new Label("title", settings.title).setVisible(settings.title != null));
        add(new Label("body", settings.body).setVisible(settings.body != null));
        add(new Image("image", settings.image).setVisible(settings.image != null));

        var repeater = new RepeatingView("buttons");
        for (var button : settings.buttons)
        {
            repeater.add(new CardPanelButton(repeater.newChildId(), button));
        }
        add(repeater.setVisible(!settings.buttons.isEmpty()));
    }
}
