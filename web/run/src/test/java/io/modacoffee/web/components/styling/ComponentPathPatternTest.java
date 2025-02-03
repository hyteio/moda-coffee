package io.modacoffee.web.components.styling;

import org.junit.jupiter.api.Test;

import static io.modacoffee.web.components.styling.ComponentPath.parseComponentPath;
import static io.modacoffee.web.components.styling.ComponentPathPattern.parseComponentPathPattern;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ComponentPathPatternTest
{
    @Test
    public void testMatching()
    {
        assertFalse(parseComponentPathPattern("*/c").matches(parseComponentPath("a/b/c/d")));
        assertFalse(parseComponentPathPattern("a/b/c/d").matches(parseComponentPath("a/b/c")));
        assertFalse(parseComponentPathPattern("a/b/c/d").matches(parseComponentPath("b/c/d")));
        assertFalse(parseComponentPathPattern("a/b/c/d").matches(parseComponentPath("a")));

        assertTrue(parseComponentPathPattern("*").matches(parseComponentPath("a/b/c/d")));
        assertTrue(parseComponentPathPattern("a/b/c/d").matches(parseComponentPath("a/b/c/d")));
        assertTrue(parseComponentPathPattern("a/*/c/d").matches(parseComponentPath("a/b/c/d")));
        assertTrue(parseComponentPathPattern("a/*/d").matches(parseComponentPath("a/b/c/d")));
        assertTrue(parseComponentPathPattern("*/d").matches(parseComponentPath("a/b/c/d")));
    }

    @Test
    public void testRegressions()
    {
        assertTrue(parseComponentPathPattern("*/customer-explore-card/*/card-panel")
            .matches(parseComponentPath("HomePage/0/customer-explore-card/card-panel")));
    }
}
