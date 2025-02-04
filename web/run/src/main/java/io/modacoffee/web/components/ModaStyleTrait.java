package io.modacoffee.web.components;

import de.agilecoders.wicket.core.markup.html.bootstrap.behavior.AlignmentBehavior;
import de.agilecoders.wicket.core.markup.html.bootstrap.layout.ContainerBehavior;
import de.agilecoders.wicket.core.markup.html.bootstrap.layout.RowBehavior;
import de.agilecoders.wicket.core.markup.html.bootstrap.layout.SpanBehavior;
import de.agilecoders.wicket.core.markup.html.bootstrap.layout.col.SpanType;
import de.agilecoders.wicket.core.markup.html.bootstrap.layout.offset.MediumOffsetType;
import de.agilecoders.wicket.core.markup.html.bootstrap.layout.offset.OffsetType;
import de.agilecoders.wicket.core.markup.html.bootstrap.layout.order.MediumOrderType;
import de.agilecoders.wicket.core.markup.html.bootstrap.layout.order.OrderType;
import de.agilecoders.wicket.core.markup.html.bootstrap.utilities.BackgroundColorBehavior;
import de.agilecoders.wicket.core.markup.html.bootstrap.utilities.ColorBehavior;
import org.apache.wicket.Component;

@SuppressWarnings("unused")
public interface ModaStyleTrait
{
    default Component component()
    {
        return (Component) this;
    }

    default Component styleAsContainer()
    {
        return component().add(new ContainerBehavior());
    }

    default Component styleAsRow()
    {
        return component().add(new RowBehavior());
    }

    default Component styleAsColumn(SpanType type, OffsetType offset, OrderType order)
    {
        return component().add(new SpanBehavior(type, offset, order));
    }

    default Component styleAsColumn(SpanType type, OffsetType offset)
    {
        return component().add(new SpanBehavior(type, offset, MediumOrderType.ORDER0));
    }

    default Component styleAsColumn(SpanType type, OrderType order)
    {
        return component().add(new SpanBehavior(type, MediumOffsetType.OFFSET0, order));
    }

    default Component styleAsColumn(SpanType type)
    {
        return component().add(new SpanBehavior(type, MediumOffsetType.OFFSET0, MediumOrderType.ORDER0));
    }

    default Component styleAsAligned(AlignmentBehavior.Alignment alignment)
    {
        return component().add(new AlignmentBehavior(alignment));
    }

    default Component styleWithBackgroundColor(BackgroundColorBehavior.Color color)
    {
        return component().add(new BackgroundColorBehavior(color));
    }

    default Component styleWithColor(ColorBehavior.Color color)
    {
        return component().add(new ColorBehavior(color));
    }
}
