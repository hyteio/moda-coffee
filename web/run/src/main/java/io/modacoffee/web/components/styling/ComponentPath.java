package io.modacoffee.web.components.styling;

import org.apache.wicket.Component;
import org.apache.wicket.WicketRuntimeException;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class ComponentPath implements Iterable<String>
{
    private final List<String> path = new LinkedList<>();

    public static ComponentPath parseComponentPath(String text)
    {
        var path = new ComponentPath();
        for (var element : text.split("/"))
        {
            if (element.isBlank())
            {
                parseError("Blank element in path");
            }
            else
            {
                path.add(element);
            }
        }
        if (path.size() == 0)
        {
            parseError("Empty path");
        }
        return path;
    }

    protected ComponentPath()
    {
    }

    public ComponentPath(Component component)
    {
        Objects.requireNonNull(component);

        for (var at = component; at != null; at = at.getParent())
        {
            path.addFirst(at.getId());
        }

        path.addFirst(component.getPage().getClass().getSimpleName());
    }

    protected ComponentPath add(String element)
    {
        path.add(element);
        return this;
    }

    @Override
    public boolean equals(final Object object)
    {
        if (object instanceof ComponentPath that)
        {
            return this.path.equals(that.path);
        }
        return false;
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(path);
    }

    public boolean hasTail()
    {
        return size() > 1;
    }

    public String head()
    {
        return size() > 0 ? path.getFirst() : null;
    }

    public ComponentPath tail()
    {
        var tail = newComponentPath();
        for (var index = 1; index < size(); index++)
        {
            tail.add(at(index));
        }
        return tail;
    }

    public String toString()
    {
        return String.join("/", this.path);
    }

    protected ComponentPath newComponentPath()
    {
        return new ComponentPath();
    }

    public String at(int index)
    {
        return path.get(index);
    }

    @Override
    public Iterator<String> iterator()
    {
        return path.iterator();
    }

    public int size()
    {
        return path.size();
    }

    /**
     * Signal a parsing error by throwing an exception
     */
    private static void parseError(String error)
    {
        throw new WicketRuntimeException(error);
    }
}
