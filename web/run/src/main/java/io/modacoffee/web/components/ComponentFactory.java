package io.modacoffee.web.components;

import org.apache.wicket.Component;
import org.apache.wicket.markup.repeater.RepeatingView;

/**
 * Creates Apache Wicket components
 *
 * @see Component
 * @see RepeatingView
 */
public interface ComponentFactory
{
    /**
     * Creates a new {@link Component} given an id. There are other use cases for this interface, but one of the main
     * use cases is with {@link RepeatingView}s, where the repeater determines the child identifier.
     *
     * @param id The id for the component
     * @return The new component
     */
    Component newComponent(String id);
}
