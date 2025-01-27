package io.modacoffee.web.pages;

import io.modacoffee.web.ModaComponent;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.request.resource.PackageResourceReference;

import java.io.Serial;

public class ModaCoffeeWebPage extends WebPage implements ModaComponent
{
    @Serial
    private static final long serialVersionUID = 1L;

    public ModaCoffeeWebPage()
    {
        addComponents();
    }

    public ModaCoffeeWebPage(PageParameters parameters)
    {
        super(parameters);
        addComponents();
    }

    private void addComponents()
    {
        add(new FeedbackPanel("feedback-panel").setOutputMarkupId(true));
        add(new Image("logo", imageResource(ModaCoffeeWebPage.class, "resources/logo.png")));
    }

    public PackageResourceReference imageResource(String image)
    {
        return imageResource(getPageClass(), image);
    }
}
