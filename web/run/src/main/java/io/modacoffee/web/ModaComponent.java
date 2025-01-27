package io.modacoffee.web;

import de.agilecoders.wicket.core.markup.html.bootstrap.button.BootstrapAjaxLink;
import de.agilecoders.wicket.core.markup.html.bootstrap.button.BootstrapBookmarkablePageLink;
import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons;
import io.modacoffee.web.pages.ModaCoffeeWebPage;
import org.apache.wicket.Component;
import org.apache.wicket.Page;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.request.resource.PackageResourceReference;

import java.util.function.Consumer;

public interface ModaComponent
{
    default void updateFeedbackPanel(Component component, AjaxRequestTarget ajax)
    {
        var feedbackPanel = component.findParent(ModaCoffeeWebPage.class).get("feedback-panel");
        ajax.add(feedbackPanel);
    }

    default BootstrapAjaxLink<Void> ajaxLink(String id, String label, Consumer<AjaxRequestTarget> code)
    {
        return new BootstrapAjaxLink<>(id, null, Buttons.Type.Link, new Model<>(label))
        {
            @Override
            public void onClick(final AjaxRequestTarget target)
            {
                code.accept(target);
            }
        };
    }

    default BootstrapBookmarkablePageLink<String> bookmarkablePageLink(String id,
                                                                       Class<? extends Page> pageType,
                                                                       String label)
    {
        return bookmarkablePageLink(id, pageType, new PageParameters(), label);
    }

    default BootstrapBookmarkablePageLink<String> bookmarkablePageLink(String id,
                                                                       Class<? extends Page> pageType,
                                                                       PageParameters parameters,
                                                                       String label)
    {
        return new BootstrapBookmarkablePageLink<String>(id, pageType, parameters, Buttons.Type.Primary)
            .setLabel(() -> label);
    }

    default PackageResourceReference imageResource(Class<? extends Page> type, String image)
    {
        return new PackageResourceReference(type, image);
    }
}
