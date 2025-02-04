package io.modacoffee.web.panels.card.sections.buttons.ajax;

import io.modacoffee.web.panels.card.sections.CardSection;
import org.apache.wicket.ajax.AjaxRequestTarget;

import java.util.function.Consumer;

public class CardAjaxButton extends CardSection
{
    public CardAjaxButton(final String id, String label, Consumer<AjaxRequestTarget> ajax)
    {
        super(id);

        add(newAjaxButton("button", label, ajax));
    }
}

