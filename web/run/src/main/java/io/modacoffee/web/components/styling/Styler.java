package io.modacoffee.web.components.styling;

import org.apache.wicket.Page;

import java.util.List;

public class Styler
{
    private final List<StyleRule> rules;

    public Styler(List<StyleRule> rules)
    {
        this.rules = rules;
    }

    /**
     * Applies the style rules given to this styler to all children on the given page
     *
     * @param page The page to style
     */
    public void apply(final Page page)
    {
        // At each child in the component tree for the given page,
        page.visitChildren((child, ignored) ->
        {
            // loop through each rule,
            for (var at : rules)
            {
                // and apply it to the child.
                at.apply(child);
            }
        });
    }
}
