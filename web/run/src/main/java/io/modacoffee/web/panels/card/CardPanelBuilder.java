package io.modacoffee.web.panels.card;

import io.modacoffee.web.components.ComponentFactory;
import io.modacoffee.web.panels.card.body.CardBody;
import io.modacoffee.web.panels.card.buttons.ajax.CardAjaxButton;
import io.modacoffee.web.panels.card.buttons.link.CardButtonLink;
import io.modacoffee.web.panels.card.image.CardImage;
import io.modacoffee.web.panels.card.text.CardText;
import io.modacoffee.web.panels.card.title.CardTitle;
import org.apache.wicket.Page;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.request.resource.ResourceReference;

import java.util.function.Consumer;

@SuppressWarnings("unused")
public class CardPanelBuilder
{
    private final RepeatingView sections = new RepeatingView("sections");

    private final String id;

    public CardPanelBuilder(String id)
    {
        this.id = id;
    }

    public CardPanelBuilder body(ComponentFactory childFactory)
    {
        sections.add(new CardBody(sections.newChildId(), childFactory));
        return this;
    }

    public CardPanelBuilder buttonLink(String label, Class<? extends Page> pageClass)
    {
        sections.add(new CardButtonLink(sections.newChildId(), label, pageClass));
        return this;
    }

    public CardPanelBuilder child(ComponentFactory component)
    {
        sections.add(component.newComponent(sections.newChildId()));
        return this;
    }

    public CardPanelBuilder ajaxButton(String label, Consumer<AjaxRequestTarget> ajax)
    {
        sections.add(new CardAjaxButton(sections.newChildId(), label, ajax));
        return this;
    }

    public CardPanelBuilder text(String text)
    {
        sections.add(new CardText(sections.newChildId(), text));
        return this;
    }

    public CardPanelBuilder title(String title)
    {
        sections.add(new CardTitle(sections.newChildId(), title));
        return this;
    }

    public CardPanelBuilder image(ResourceReference image)
    {
        sections.add(new CardImage(sections.newChildId(), image));
        return this;
    }

    public CardPanel build()
    {
        return new CardPanel(id, sections);
    }
}
