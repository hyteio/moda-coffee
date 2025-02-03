package io.modacoffee.web.components.styling;

import org.apache.wicket.WicketRuntimeException;

public class ComponentPathPattern extends ComponentPath
{
    /**
     * Parses the given text into a {@link ComponentPathPattern}. Valid path elements can be wildcard values or text
     * values containing alphanumeric characters, digits, dashes and underscores.
     *
     * @param text The text to parse
     * @return The pattern
     */
    public static ComponentPathPattern parseComponentPathPattern(String text)
    {
        var path = new ComponentPathPattern();
        for (var element : text.split("/"))
        {
            path.add(checkElement(element));
        }
        if (path.size() == 0)
        {
            parseError("A path pattern must contain at least one path element");
        }
        return path;
    }

    /**
     * Checks the given path element, raising a parsing error if it is not valid
     *
     * @param element The element to check
     * @return The element for chaining
     */
    private static String checkElement(final String element)
    {
        if (isWildcard(element) || element.matches("[A-Za-z0-9-_]+"))
        {
            return element;
        }
        return parseError("Not a valid path element: '" + element + "'");
    }

    /**
     * Returns true if this component path pattern matches the given component path
     *
     * @param candidate The candidate path to match against
     * @return True if the candidate is accepted by this pattern
     */
    public boolean matches(ComponentPath candidate)
    {
        // Get the first element of this pattern,
        var head = head();
        if (head != null)
        {
            // and if the pattern and candidate are both single elements,
            if (size() == 1 && candidate.size() == 1)
            {
                // return true if those two elements match.
                return matchElements(head, candidate.head());
            }

            // If the head of the pattern precisely matches (no wildcard allowed) the head of the candidate,
            if (head.equals(candidate.head()))
            {
                // then the pattern matches if the tail of this pattern matches the tail of the candidate.
                return hasTail() && candidate.hasTail() && tail().matches(candidate.tail());
            }

            // If the head didn't match, but it's a wildcard,
            if (isWildcard(head))
            {
                // then the match is successful if the tail of this pattern matches the candidate (in such
                // a case, the wildcard doesn't consume further input, for example, when */a/b is matched
                // against a/b),
                if (hasTail() && tail().matches(candidate))
                {
                    return true;
                }

                // OR

                // the match is successful if this pattern matches the tail of the candidate (in such a case,
                // the wildcard consumes the next input element, for example, when */a/b is matched against x/a/b).
                if (candidate.hasTail())
                {
                    return matches(candidate.tail());
                }
            }
        }

        return false;
    }

    /**
     * Returns true if the given pattern element matches the given path element
     */
    private static boolean matchElements(final String patternElement, final String pathElement)
    {
        return isWildcard(patternElement) || patternElement.equals(pathElement);
    }

    protected ComponentPathPattern newComponentPath()
    {
        return new ComponentPathPattern();
    }

    @Override
    public ComponentPathPattern tail()
    {
        return (ComponentPathPattern) super.tail();
    }

    /**
     * Returns true if the given path element is a wildcard
     */
    private static boolean isWildcard(final String element)
    {
        return element.equals("*");
    }

    /**
     * Signal a parsing error by throwing an exception
     */
    private static String parseError(String error)
    {
        throw new WicketRuntimeException(error);
    }
}
