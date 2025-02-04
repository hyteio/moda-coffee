package io.modacoffee.web.components;

import de.agilecoders.wicket.core.markup.html.bootstrap.button.BootstrapAjaxLink;
import de.agilecoders.wicket.core.markup.html.bootstrap.button.BootstrapBookmarkablePageLink;
import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons;
import io.modacoffee.web.model.Cart;
import io.modacoffee.web.model.OrderQueue;
import io.modacoffee.web.pages.ModaCoffeeWebPage;
import io.modacoffee.web.panels.ModaCoffeePanel;
import io.modacoffee.web.run.ModaCoffeeSession;
import org.apache.wicket.Component;
import org.apache.wicket.Page;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.request.resource.PackageResourceReference;

import java.util.function.Consumer;

/**
 * Functionality applicable to all Moda components. The {@link ModaCoffeeWebPage} class implements this interface as
 * well as the {@link ModaCoffeePanel} class.
 *
 * @see ModaCoffeeWebPage
 * @see ModaCoffeePanel
 */
public interface ModaCoffeeComponent extends ModaStyleTrait
{
    /**
     * Returns a new Bootstrap AJAX button that calls the given code
     */
    default BootstrapAjaxLink<Void> newAjaxButton(String id, String label, Consumer<AjaxRequestTarget> code)
    {
        return new BootstrapAjaxLink<>(id, null, Buttons.Type.Primary, new Model<>(label))
        {
            @Override
            public void onClick(final AjaxRequestTarget target)
            {
                code.accept(target);
            }
        };
    }

    /**
     * Returns a new Bootstrap button that links to the given bookmarkable page using the given label
     */
    default BootstrapBookmarkablePageLink<String> newButtonLink(String id,
                                                                String label, Class<? extends Page> pageType)
    {
        return new BootstrapBookmarkablePageLink<String>(id, pageType, new PageParameters(), Buttons.Type.Primary)
            .setLabel(() -> label);
    }

    /**
     * Returns the {@link Cart} for the user's session given a component in the session
     */
    default Cart cart(Component component)
    {
        return session(component).cart();
    }

    /**
     * Returns the {@link OrderQueue} for the system
     */
    default OrderQueue orderQueue()
    {
        return OrderQueue.get();
    }

    /**
     * Returns a package resource reference for the given image
     */
    default PackageResourceReference imageResource(Class<? extends Component> type, String image)
    {
        return new PackageResourceReference(type, image);
    }

    /**
     * Returns the {@link ModaCoffeeSession} for the user's session, given any component in their session
     */
    default ModaCoffeeSession session(Component component)
    {
        return (ModaCoffeeSession) component.getSession();
    }

    /**
     * Updates the feedback panel on any {@link ModaCoffeeWebPage} via AJAX
     */
    default void updateFeedbackPanel(Component component, AjaxRequestTarget ajax)
    {
        var feedbackPanel = component.findParent(ModaCoffeeWebPage.class);
        if (feedbackPanel == null && component instanceof ModaCoffeeWebPage)
        {
            feedbackPanel = (ModaCoffeeWebPage) component;
        }
        if (feedbackPanel != null)
        {
            feedbackPanel.get("feedback-panel");
            ajax.add(feedbackPanel);
        }
    }
}
