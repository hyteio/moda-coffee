package io.modacoffee.web.pages;

import de.agilecoders.wicket.core.markup.html.bootstrap.button.BootstrapBookmarkablePageLink;
import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.request.resource.PackageResourceReference;

import java.io.Serial;

public class ModaCoffeeWebPage extends WebPage
{
    @Serial
    private static final long serialVersionUID = 1L;

    @Override
    protected void onInitialize()
    {
        add(new Image("logo", imageResource("logo.png")));
    }

    protected BootstrapBookmarkablePageLink<String> bookmarkablePageLink(String id, Class<? extends ModaCoffeeWebPage> pageType)
    {
        return new BootstrapBookmarkablePageLink<>(id, pageType, Buttons.Type.Primary);
    }

    protected PackageResourceReference imageResource(String image)
    {
        return new PackageResourceReference(getPageClass(), "images/" + image + ".jpg");
    }
}
