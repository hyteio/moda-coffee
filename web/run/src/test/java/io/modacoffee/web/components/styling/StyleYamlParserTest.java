package io.modacoffee.web.components.styling;

import io.modacoffee.web.components.styling.AttributeEditor.Modification;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.function.Consumer;

import static io.modacoffee.web.components.styling.AttributeEditor.Modification.APPEND;
import static io.modacoffee.web.components.styling.AttributeEditor.Modification.CLEAR;
import static io.modacoffee.web.components.styling.AttributeEditor.Modification.PREPEND;
import static io.modacoffee.web.components.styling.AttributeEditor.Modification.REMOVE;
import static io.modacoffee.web.components.styling.ComponentPathPattern.parseComponentPathPattern;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class StyleYamlParserTest
{
    @Test
    public void testParseSuccessModifications()
    {
        // The test case for CLEAR is different because it does not require (or allow) an attribute value
        assertParseSucceeds(rule("a/b/c", CLEAR, "x", null), """
            - rule:
              path: "a/b/c"
              do: clear
              attr: "x"
            """);

        for (var modification : Modification.values())
        {
            if (modification.requiresValue())
            {
                assertParseSucceeds(rule("a/b/c", modification, "x", "y"), """
                    - rule:
                      path: "a/b/c"
                      do: [modification]
                      attr: "x"
                      value: "y"
                    """
                    .replace("[modification]", modification.name()));
            }
        }
    }

    @Test
    public void testParseMultipleRules()
    {
        assertParseSucceeds(List.of(
            rule("a/b/c", APPEND, "x", "y"),
            rule("a/*/c", PREPEND, "x", "y"),
            rule("*/c", REMOVE, "x", "y")), """
            - rule:
              path: "a/b/c"
              do: append
              attr: "x"
              value: "y"

            - rule:
              path: "a/*/c"
              do: prepend
              attr: "x"
              value: "y"

            - rule:
              path: "*/c"
              do: remove
              attr: "x"
              value: "y"
            """);
    }

    @Test
    public void testParseSuccess()
    {
        assertParseSucceeds(rule("a/b/c", APPEND, "class", "debug"), """
            - rule:
              path: "a/b/c"
              do: append
              attr: "class"
              value: "debug"
            """);

        assertParseSucceeds(rule("a/*/c/d", REMOVE, "class", "debug centered"), """
            - rule:
              path: "a/*/c/d"
              do: remove
              attr: "class"
              value: "debug centered"
            """);
    }

    @Test
    public void testParseSuccessBareValues()
    {
        assertParseSucceeds(rule("a/*/c/d", APPEND, "class", "debug"), """
            - rule:
              path: "a/*/c/d"
              do: append
              attr: class
              value: debug
            """);
    }

    @Test
    public void testParseFailureNonsense()
    {
        assertParseFails(null);
        assertParseFails("x");
    }

    @Test
    public void testParseFailureInvalidBareValues()
    {
        assertParseFails("""
            - rule:
              path: a/b/c
              do: append
              attr: class
              value: debug
            """);
        assertParseFails("""
            - rule:
              path: "a/b/c"
              do: append
              attr: !
              value: debug
            """);
        assertParseFails("""
            - rule:
              path: "a/b/c"
              do: append
              attr: class
              value: debug centered
            """);
    }

    @Test
    public void testParseFailureInvalidPath()
    {
        assertParseFails("""
            - rule:
              path: ""
              do: append
              attr: "x"
              value: "y"
            """);
        assertParseFails("""
            - rule:
              path: "!"
              do: append
              attr: "x"
              value: "y"
            """);
        assertParseFails("""
            - rule:
              path:
              do: append
              attr: "x"
              value: "y"
            """);
    }

    @Test
    public void testParseFailureAttributesOutOfOrder()
    {
        assertParseFails("""
            - rule:
              do: append
              path: "a/b/c"
              attr: "x"
              value: "y"
            """);
    }

    @Test
    public void testParseFailureAttributesMissing()
    {
        assertParseFails("""
            - rule:
              do: append
              attr: "x"
              value: "y"
            """);
        assertParseFails("""
            - rule:
              path: "a/b/c"
              attr: "x"
              value: "y"
            """);
        assertParseFails("""
            - rule:
              path: "a/b/c"
              do: append
              value: "y"
            """);
        assertParseFails("""
            - rule:
              path: "a/b/c"
              do: append
              attr: "x"
            """);
    }

    private void assertParseSucceeds(final StyleRule expected, final String text)
    {
        parseRules(text, rules ->
        {
            assertEquals(1, rules.size());
            assertEquals(expected, rules.getFirst());
        });
    }

    private void assertParseSucceeds(final List<StyleRule> expected, final String text)
    {
        parseRules(text, rules ->
        {
            assertEquals(expected.size(), rules.size());
            assertEquals(expected, rules);
        });
    }

    private void parseRules(final String text, Consumer<List<StyleRule>> code)
    {
        var parser = new StyleYamlParser();
        try
        {
            code.accept(parser.parseRules(text));
        }
        catch (Exception e)
        {
            fail("Could not parse text: " + text, e);
        }
    }

    private void assertParseFails(final String text)
    {
        var parser = new StyleYamlParser();
        try
        {
            parser.parseRules(text);
            fail("Should not have parsed text: " + text);
        }
        catch (Exception ignored)
        {
        }
    }

    private static StyleRule rule(String pattern, Modification modification, String attribute, String value)
    {
        return new StyleRule(parseComponentPathPattern(pattern), modification, attribute, value);
    }
}
