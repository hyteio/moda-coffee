package io.modacoffee.web.components.styling;

public class ComponentPathPattern extends ComponentPath
{
    public static ComponentPathPattern parseComponentPathPattern(String pattern)
    {
        return (ComponentPathPattern) new ComponentPathPattern().addAll(pattern.split("/"));
    }

    /**
     * Returns true if this component path pattern matches the given component path
     *
     * @param candidate The candidate path to match against
     * @return True if the candidate is accepted by this pattern
     */
    public boolean matches(ComponentPath candidate)
    {
        // If this pattern and the candidate path are the same length,
        if (size() == candidate.size())
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

    /**
     * Checks this pattern element-by element against the candidate path. The candidate path must be the same length as
     * this path pattern.
     *
     * @param candidate The candidate path to match
     * @return True if the candidate matches this pattern.
     */
    private boolean matchEachElement(final ComponentPath candidate)
    {
        assert size() == candidate.size();

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
