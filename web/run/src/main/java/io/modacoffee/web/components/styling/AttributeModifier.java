package io.modacoffee.web.components.styling;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Conditionally modifies attributes based on their Apache Wicket path from the page root.
 */
public class AttributeModifier extends org.apache.wicket.AttributeModifier
{
    public enum Modification
    {
        APPEND,
        PREPEND,
        REMOVE,
        SET;

        public static Modification parseModification(String value)
        {
            return valueOf(value.toUpperCase());
        }
    }

    private final Modification modification;

    private final String value;

    /**
     * @param modification The type of modification to perform
     * @param attribute The attribute name
     * @param value The value to append-to, prepend-to or replace the existing value
     */
    public AttributeModifier(Modification modification, String attribute, String value)
    {
        super(attribute, value);

        this.modification = modification;
        this.value = value;
    }

    @Override
    protected Serializable newValue(final String currentValue, final String replacementValue)
    {
        var attributes = attributes(currentValue);

        switch (modification)
        {
            case APPEND -> attributes.add(value);
            case PREPEND -> attributes.addFirst(value);
            case SET -> attributes = List.of(value);
            case REMOVE -> attributes.remove(value);
        }
        return String.join(" ", attributes);
    }

    private static List<String> attributes(final String currentValue)
    {
        return currentValue.isBlank()
            ? new ArrayList<>()
            : new ArrayList<>(Arrays.asList(currentValue.split(" ")));
    }
}
