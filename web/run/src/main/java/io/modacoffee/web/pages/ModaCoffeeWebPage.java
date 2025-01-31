package io.modacoffee.web.pages;

import io.modacoffee.web.components.ModaCoffeeComponent;
import io.modacoffee.web.components.styling.Style;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import java.io.IOException;
import java.io.Serial;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

/**
 * Base class for all web pages on the Moda Coffee web site. The logo that appears on all pages is added here as well as
 * a {@link FeedbackPanel} for messages to the user.
 */
public class ModaCoffeeWebPage extends WebPage implements ModaCoffeeComponent
{
    @Serial
    private static final long serialVersionUID = 1L;

    private transient Style style = null;

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

        var style = style();
        if (style != null)
        {
            style.apply(this);
        }
    }

    private Style style()
    {
        if (style == null)
        {
            try (var input = getClass().getResourceAsStream(getPageClass().getSimpleName() + ".style"))
            {
                if (input != null)
                {
                    var text = new String(input.readAllBytes(), StandardCharsets.UTF_8);
                    style = Style.parse(lines(text));
                }
                return null;
            }
            catch (IOException e)
            {
                throw new RuntimeException(e);
            }
        }
        return style;
    }

    private List<String> lines(String text)
    {
        return Arrays.stream(text.split("\n")).toList();
    }
}
