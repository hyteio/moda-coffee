package io.modacoffee.web.pages;

import io.modacoffee.web.components.ModaCoffeeComponent;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import java.io.Serial;

/**
 * Base class for all web pages on the Moda Coffee web site. The logo that appears on all pages is added here as well as
 * a {@link FeedbackPanel} for messages to the user.
 */
public class ModaCoffeeWebPage extends WebPage implements ModaCoffeeComponent
{
    @Serial
    private static final long serialVersionUID = 1L;

    public ModaCoffeeWebPage()
    {
        this(new PageParameters());
    }

    public ModaCoffeeWebPage(PageParameters parameters)
    {
        super(parameters);

        add(new FeedbackPanel("feedback-panel").setOutputMarkupId(true));
        add(new Image("logo", imageResource(ModaCoffeeWebPage.class, "resources/ModaCoffeeLogo.png")));
    }
}
