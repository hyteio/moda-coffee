package io.modacoffee.web.panels;

import io.modacoffee.web.components.ModaCoffeeComponent;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;

/**
 * Base class for Moda Coffee reusable panels
 */
public class ModaCofeePanel extends Panel implements ModaCoffeeComponent
{
    public ModaCofeePanel(final String id)
    {
        super(id);
    }

    public ModaCofeePanel(final String id, final IModel<?> model)
    {
        super(id, model);
    }
}
