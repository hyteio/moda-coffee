package io.modacoffee.web.components.styling;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ComponentPathPatternTest
{
    @Test
    public void testMatching()
    {
        assertTrue(ComponentPathPattern.parse("a/b/c/d").matches(ComponentPath.parse("a/b/c/d")));
        assertTrue(ComponentPathPattern.parse("a/*/c/d").matches(ComponentPath.parse("a/b/c/d")));
        assertTrue(ComponentPathPattern.parse("a/*/d").matches(ComponentPath.parse("a/b/c/d")));
        assertTrue(ComponentPathPattern.parse("*/d").matches(ComponentPath.parse("a/b/c/d")));
        assertTrue(ComponentPathPattern.parse("*").matches(ComponentPath.parse("a/b/c/d")));

        assertFalse(ComponentPathPattern.parse("a/b/c/d").matches(ComponentPath.parse("a/b/c")));
        assertFalse(ComponentPathPattern.parse("a/b/c/d").matches(ComponentPath.parse("b/c/d")));
        assertFalse(ComponentPathPattern.parse("a/b/c/d").matches(ComponentPath.parse("a")));
        assertFalse(ComponentPathPattern.parse("*/c").matches(ComponentPath.parse("a/b/c/d")));
    }
}
