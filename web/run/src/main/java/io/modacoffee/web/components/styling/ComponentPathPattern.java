package io.modacoffee.web.components.styling;

public class ComponentPathPattern extends ComponentPath
{
    public static ComponentPathPattern parse(String pattern)
    {
        return (ComponentPathPattern) new ComponentPathPattern().addAll(pattern.split("/"));
    }

    protected ComponentPathPattern()
    {
        super();
    }

    public boolean matches(ComponentPath candidate)
    {
        // If the pattern and the path are the same length,
        if (candidate.size() == size())
        {
            // then just compare each element 1:1.
            return matchEachElement(candidate);
        }

        // If the head of the pattern matches the head of the candidate,
        var head = head();
        if (head.equals(candidate.head()))
        {
            // then the pattern matches if the two tails match.
            return tail().matches(candidate.tail());
        }

        // If the head didn't match, but it's a wildcard,
        if (isWildcard(head))
        {
            // then we can loop through the tail of the candidate,
            var tail = candidate.tail();
            while (tail.size() > 0)
            {
                // and if this pattern matches that remainder,
                if (matches(candidate.tail()))
                {
                    // then we matched fully,
                    return true;
                }

                // otherwise, we can try assuming the wildcard matches the
                // head of the candidate and move forward to see if the remainder
                // of the candidate matches.
                tail = tail.tail();
            }
        }

        return false;
    }

    private boolean matchEachElement(final ComponentPath candidate)
    {
        var index = 0;

        // For each element in the given path,
        for (var at : candidate)
        {
            // get the next element of this pattern path,
            var next = at(index++);

            // and if it's not a wildcard and the elements don't match,
            if (!isWildcard(next) && !at.equals(next))
            {
                // then this path doesn't match the pattern
                return false;
            }
        }

        // If we got to the end via wildcards and/or matching elements, then
        // this path matches the pattern.
        return index == size();
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

    private boolean isWildcard(final String next)
    {
        return next.equals("*");
    }
}
