package io.modacoffee.web.pages;

import io.modacoffee.web.components.ModaCoffeeComponent;
import io.modacoffee.web.components.styling.StyleYamlParser;
import io.modacoffee.web.components.styling.Styler;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.util.resource.ResourceStreamNotFoundException;

import java.io.IOException;
import java.io.Serial;
import java.nio.charset.StandardCharsets;

/**
 * Base class for all web pages on the Moda Coffee web site. The logo that appears on all pages is added here as well as
 * a {@link FeedbackPanel} for messages to the user.
 */
public class ModaCoffeeWebPage extends WebPage implements ModaCoffeeComponent
{
    @Serial
    private static final long serialVersionUID = 1L;

    private transient Styler styler = null;

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

    @Override
    protected void onBeforeRender()
    {
        super.onBeforeRender();

        var styler = styler();
        if (styler != null)
        {
            styler.apply(this);
        }
    }

    private Styler styler()
    {
        // If we haven't already found the styler rules,
        if (styler == null)
        {
            // loop through the application's resource finders,
            for (var finder : getApplication().getResourceSettings().getResourceFinders())
            {
                // and if the finder locates the resource <page-class>.style.yaml,
                try (var resource = finder.find(getPageClass(), getPageClass().getSimpleName() + ".style.yaml"))
                {
                    if (resource != null)
                    {
                        // get the resource's input stream,
                        try (var input = resource.getInputStream())
                        {
                            if (input != null)
                            {
                                // read all text from that stream,
                                var text = new String(input.readAllBytes(), StandardCharsets.UTF_8);

                                // parse the rules from the text, and create the page styler.
                                styler = new Styler(new StyleYamlParser().parseRules(text));
                                return styler;
                            }
                        }
                        catch (ResourceStreamNotFoundException ignored)
                        {
                        }
                    }
                }
                catch (IOException ignored)
                {
                }
            }
        }
        return styler;
    }
}
