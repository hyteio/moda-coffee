package io.modacoffee.web.panels.card;

import io.modacoffee.web.pages.ModaCoffeeWebPage;
import io.modacoffee.web.panels.ModaCofeePanel;
import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.ajax.AjaxRequestTarget;

import java.util.function.Consumer;

public class CardPanelButton extends ModaCofeePanel
{
    /**
     * The settings for a button on the card panel. If a page class is provided, the button will be a bookmarkable page
     * link. If a callback is provided, the button will be an AJAX button. Both values should not be provided at the
     * same time.
     *
     * @param buttonLabel The button's label
     * @param pageClass The page to link to
     * @param callback The code to call via AJAX
     */
    public record ButtonSettings(String buttonLabel,
                                 Class<? extends ModaCoffeeWebPage> pageClass,
                                 Consumer<AjaxRequestTarget> callback)
    {
        public static ButtonSettings button(String buttonLabel, Class<? extends ModaCoffeeWebPage> pageClass)
        {
            return new ButtonSettings(buttonLabel, pageClass, null);
        }

        public static ButtonSettings button(String buttonLabel, Consumer<AjaxRequestTarget> callback)
        {
            return new ButtonSettings(buttonLabel, null, callback);
        }

        public void check()
        {
            if (pageClass == null && callback == null)
            {
                throw new WicketRuntimeException("Must specify either a page class or an AJAX callback");
            }
            if (pageClass != null && callback != null)
            {
                throw new WicketRuntimeException("Must specify a page class or an AJAX callback, but not both");
            }
        }
    }

    public CardPanelButton(final String id, final ButtonSettings settings)
    {
        super(id);

        settings.check();

        if (settings.callback != null)
        {
            add(newAjaxButton("button", settings.buttonLabel, settings.callback));
        }
        else
        {
            add(newButtonLink("button", settings.buttonLabel, settings.pageClass));
        }
    }
}
