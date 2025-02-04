package io.modacoffee.web.panels.card.cards.horizontal;

import io.modacoffee.web.components.ComponentFactory;
import io.modacoffee.web.panels.ModaCoffeePanel;
import io.modacoffee.web.panels.card.sections.body.CardBody;
import io.modacoffee.web.panels.card.sections.buttons.ajax.CardAjaxButton;
import io.modacoffee.web.panels.card.sections.buttons.link.CardButtonLink;
import io.modacoffee.web.panels.card.sections.child.CardContainer;
import io.modacoffee.web.panels.card.sections.image.CardImage;
import io.modacoffee.web.panels.card.sections.text.CardText;
import io.modacoffee.web.panels.card.sections.title.CardTitle;
import org.apache.wicket.Component;
import org.apache.wicket.Page;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.request.resource.ResourceReference;

import java.util.function.Consumer;

public class Column extends ModaCoffeePanel
{
    public static Builder builder(String id)
    {
        return new Builder(id);
    }

    public Column(final String id, RepeatingView rows)
    {
        super(id);
        add(rows);
    }

    @SuppressWarnings("unused")
    public static class Builder
    {
        private final RepeatingView rows = new RepeatingView("rows");

        private final String id;

        public Builder(String id)
        {
            this.id = id;
        }

        public Builder body(ComponentFactory<Component> childFactory)
        {
            return add(new CardBody(rows.newChildId(), childFactory));
        }

        public Builder buttonLink(String label, Class<? extends Page> pageClass)
        {
            return add(new CardButtonLink(rows.newChildId(), label, pageClass));
        }

        public Builder child(ComponentFactory<Component> component)
        {
            return add(new CardContainer(rows.newChildId(), component));
        }

        public Builder ajaxButton(String label, Consumer<AjaxRequestTarget> ajax)
        {
            return add(new CardAjaxButton(rows.newChildId(), label, ajax));
        }

        public Builder text(String text)
        {
            return add(new CardText(rows.newChildId(), text));
        }

        public Builder title(String title)
        {
            return add(new CardTitle(rows.newChildId(), title));
        }

        public Builder image(ResourceReference image)
        {
            return add(new CardImage(rows.newChildId(), image));
        }

        public Column build()
        {
            return new Column(id, rows);
        }

        private Builder add(Component component)
        {
            rows.add(component);
            return this;
        }
    }
}
