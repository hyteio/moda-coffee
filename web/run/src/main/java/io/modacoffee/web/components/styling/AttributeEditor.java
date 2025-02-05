package io.modacoffee.web.components.styling;

import org.apache.wicket.AttributeModifier;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Conditionally modifies attributes based on their Apache Wicket path from the page root.
 */
public class AttributeEditor extends AttributeModifier
{
    public enum Modification
    {
        APPEND,
        CLEAR,
        PREPEND,
        REMOVE,
        SET;

        public boolean requiresValue()
        {
            return this != CLEAR;
        }

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
    public AttributeEditor(Modification modification, String attribute, String value)
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
            case CLEAR -> attributes.clear();
            case PREPEND -> attributes.addFirst(value);
            case REMOVE -> attributes.remove(value);
            case SET -> attributes = List.of(value);
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
