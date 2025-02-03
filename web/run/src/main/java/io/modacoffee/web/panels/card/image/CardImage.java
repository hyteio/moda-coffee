package io.modacoffee.web.panels.card.image;

import io.modacoffee.web.panels.card.CardSection;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.request.resource.ResourceReference;

public class CardImage extends CardSection
{
    public CardImage(String id, ResourceReference image)
    {
        super(id);

        add(new Image("image", image));
    }
}
