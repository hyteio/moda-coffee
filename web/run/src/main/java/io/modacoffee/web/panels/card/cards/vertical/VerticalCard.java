package io.modacoffee.web.panels.card.cards.vertical;

import io.modacoffee.web.panels.ModaCoffeePanel;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.markup.repeater.RepeatingView;

import java.util.function.Consumer;

public class VerticalCard extends ModaCoffeePanel
{
    public static Builder builder(String id)
    {
        return new Builder(id);
    }

    public VerticalCard(final String id, RepeatingView rows)
    {
        super(id);

        var container = new MarkupContainer("card-container") {};
        container.add(rows);
        add(container);
    }

    public static class Builder
    {
        private final RepeatingView rows = new RepeatingView("rows");

        private final String id;

        Builder(String id)
        {
            this.id = id;
        }

        public Builder row(Consumer<Row.Builder> initializer)
        {
            // Let the caller build a row,
            var builder = Row.builder(rows.newChildId());
            initializer.accept(builder);
            var row = builder.build();

            // then add the row to the repeating view of rows.
            rows.add(row);
            return this;
        }

        public VerticalCard build()
        {
            return new VerticalCard(id, rows);
        }
    }
}
