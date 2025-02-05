package io.modacoffee.web.components.styling;

import org.apache.wicket.Component;

import static io.modacoffee.web.components.styling.AttributeEditor.Modification;

/**
 * If the component path matches the pattern, the given attribute is modified by applying the given modification with
 * the given value.
 *
 * @param pattern The component path pattern that should be modified, like "HomePage/sign-in/&#42;/button"
 * @param modification The modification to make (append, prepend or replace)
 * @param attribute The attribute to change like "class"
 * @param value The value to apply
 */
public record StyleRule(ComponentPathPattern pattern,
                        Modification modification,
                        String attribute,
                        String value)
{
    /**
     * Apply this style rule to the given component
     *
     * @param component The component to style
     */
    public void apply(Component component)
    {
        // If the path pattern matches the path for the given component,
        if (pattern.matches(new ComponentPath(component)))
        {
            // then add the attribute modifier that will make the change specified by the rule.
            component.add(new AttributeEditor(modification, attribute, value));
        }
    }
}
