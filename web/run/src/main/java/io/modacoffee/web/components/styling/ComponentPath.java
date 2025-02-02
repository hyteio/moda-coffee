package io.modacoffee.web.components.styling;

import org.apache.wicket.Component;

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class ComponentPath implements Iterable<String>
{
    private final List<String> path = new LinkedList<>();

    public static ComponentPath parseComponentPath(String text)
    {
        return new ComponentPath().addAll(text.split("/"));
    }

    protected ComponentPath()
    {
    }

    public ComponentPath(Component component)
    {
        Objects.requireNonNull(component);

        for (var at = component; at != null; at = at.getParent())
        {
            path.addFirst(component.getId());
        }

        path.addFirst(component.getPage().getClass().getSimpleName());
    }

    protected ComponentPath addAll(String[] elements)
    {
        path.addAll(Arrays.asList(elements));
        return this;
    }

    protected ComponentPath add(String element)
    {
        path.add(element);
        return this;
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
}
