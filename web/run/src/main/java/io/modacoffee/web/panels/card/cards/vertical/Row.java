package io.modacoffee.web.panels.card.cards.vertical;

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
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.Page;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.request.resource.ResourceReference;

import java.util.function.Consumer;

public class Row extends ModaCoffeePanel
{
    public static Builder builder(String id)
    {
        return new Builder(id);
    }

    public Row(final String id, RepeatingView columns)
    {
        super(id);

        var row = new MarkupContainer("row") {};
        row.add(columns);
        add(row);
    }

    @SuppressWarnings("unused")
    public static class Builder
    {
        private final RepeatingView columns = new RepeatingView("columns");

        private final String id;

        public Builder(String id)
        {
            this.id = id;
        }

        public Builder body(ComponentFactory<Component> childFactory)
        {
            return add(new CardBody(columns.newChildId(), childFactory));
        }

        public Builder buttonLink(String label, Class<? extends Page> pageClass)
        {
            return add(new CardButtonLink(columns.newChildId(), label, pageClass));
        }

        public Builder child(ComponentFactory<Component> component)
        {
            return add(new CardContainer(columns.newChildId(), component));
        }

        public Builder ajaxButton(String label, Consumer<AjaxRequestTarget> ajax)
        {
            return add(new CardAjaxButton(columns.newChildId(), label, ajax));
        }

        public Builder text(String text)
        {
            return add(new CardText(columns.newChildId(), text));
        }

        public Builder title(String title)
        {
            return add(new CardTitle(columns.newChildId(), title));
        }

        public Builder image(ResourceReference image)
        {
            return add(new CardImage(columns.newChildId(), image));
        }

        public Row build()
        {
            return new Row(id, columns);
        }

        private Builder add(Component component)
        {
            columns.add(component);
            return this;
        }
    }
}
