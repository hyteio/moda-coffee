package io.modacoffee.web.components.styling;

import org.apache.wicket.Component;
import org.apache.wicket.WicketRuntimeException;

import static io.modacoffee.web.components.styling.AttributeModifier.ModificationType;

/**
 * If the component path matches the pattern, the given attribute is modified by applying the given modification with
 * the given value.
 *
 * @param pattern The component path pattern that should be modified, like "HomePage/sign-in/&#42;/button"
 * @param attribute The attribute to change like "class"
 * @param modificationType The modification to make (append, prepend or replace)
 * @param value The value to apply
 */
public record StyleRule(ComponentPathPattern pattern, String attribute, ModificationType modificationType,
                        String value)
{
    /**
     * Parses a textually defined rule such as "HomePage/sign-in/&#42;/button:class:append:moda-button"
     *
     * @param rule The rule to parse
     * @return The styling rule
     */
    public static StyleRule parse(String rule)
    {
        var parts = rule.split(":");
        if (parts.length != 4)
        {
            throw new WicketRuntimeException("Styling rules must have 4 components: pattern:attribute:modification-type:value");
        }

        var pattern = ComponentPathPattern.parse(parts[0]);
        var attribute = parts[1];
        var modificationType = ModificationType.valueOf(parts[2].toUpperCase());
        var value = parts[3];

        return new StyleRule(pattern, attribute, modificationType, value);
    }

    public void apply(Component component)
    {
        var path = new ComponentPath(component);
        if (pattern.matches(path))
        {
            component.add(new AttributeModifier(attribute, modificationType, value));
        }
    }
}
