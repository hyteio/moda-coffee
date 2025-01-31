package io.modacoffee.web.components.styling;

import org.apache.wicket.Page;

import java.util.ArrayList;
import java.util.List;

public class Style
{
    private final List<StyleRule> rules = new ArrayList<>();

    public static Style parse(List<String> rules)
    {
        var style = new Style();
        for (var at : rules)
        {
            if (!at.isBlank())
            {
                style.add(StyleRule.parse(at));
            }
        }
        return style;
    }

    public void add(StyleRule rule)
    {
        this.rules.add(rule);
    }

    public void apply(final Page page)
    {
        page.visitChildren((child, ignored) ->
        {
            for (var at : rules)
            {
                at.apply(child);
            }
        });
    }
}
