package io.modacoffee.web.panels.card.cards.horizontal;

import io.modacoffee.web.panels.ModaCoffeePanel;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.markup.repeater.RepeatingView;

import java.util.function.Consumer;

public class HorizontalCard extends ModaCoffeePanel
{
    public static Builder builder(String id)
    {
        return new Builder(id);
    }

    public HorizontalCard(final String id, RepeatingView columns)
    {
        super(id);

        var container = new MarkupContainer("card-container") {};
        container.add(columns);
        add(container);
    }

    public static class Builder
    {
        private final RepeatingView columns = new RepeatingView("columns");

        private final String id;

        Builder(String id)
        {
            this.id = id;
        }

        public Builder column(Consumer<Column.Builder> initializer)
        {
            // Let the caller build a column,
            var builder = Column.builder(columns.newChildId());
            initializer.accept(builder);
            var column = builder.build();

            // then add the column to the repeating view of columns.
            columns.add(column);
            return this;
        }

        public HorizontalCard build()
        {
            return new HorizontalCard(id, columns);
        }
    }
}
