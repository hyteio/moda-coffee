package io.modacoffee.web.components.styling;

import org.apache.wicket.WicketRuntimeException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static io.modacoffee.web.components.styling.AttributeModifier.Modification.parseModification;
import static io.modacoffee.web.components.styling.ComponentPathPattern.parseComponentPathPattern;

/**
 * <p>
 * This class parses a restricted YAML-like syntax for defining {@link StyleRule}s. The syntax uses the YAML list syntax
 * to define each rule in the list of rules by preceding it with a hyphen. For example:
 *
 * <pre>
 * - rule:
 *     path: "&#42;/customer-explore-card/&#42;/card-panel"
 *     do: remove
 *     attr: class
 *     value: "card"
 *
 * - rule:
 *     path: "&#42;/customer-explore-card/&#42;/card-panel"
 *     do: append
 *     attr: class
 *     value: "debug"</pre>
 * </p>
 *
 * <p>
 * The first rule here removes the value "card" from the class attribute of all tags matching the Apache Wicket path
 * "&#42;/customer-explore-card/&#42;/card-panel". The second rule then appends the value "debug" to the class attribute
 * of all tags matching the same path.
 * </p>
 *
 * <b>Ordering of attributes</b>
 *
 * <p>
 * The attributes for each rule must be in the order above: path, do, attr, value.
 * </p>
 *
 * <b>Ordering of Rules</b>
 *
 * <p>
 * Style rules are applied in the order they are defined in the YAML file.
 * </p>
 *
 * <b>Apache Wicket Paths</b>
 *
 * <p>
 * An Apache Wicket path uniquely identifies a component in the user's session, starting with the name of the page class
 * and following all the <i>wicket:id</i> attributes down to the component. For example, a "Sign In" button on a
 * <i>SignInPanel</i> added to <i>HomePage</i>:
 *
 * <pre>
 *     HomePage.html
 *
 *       [...]
 *
 *           &lt;div wicket:id="sign-in-panel"/&gt;
 *
 *       [...]
 *
 *     SignInPanel.html
 *
 *       [...]
 *
 *       &lt;wicket:panel&gt;
 *
 *           &lt;a wicket:id="sign-in"/&gt;
 *
 *       &lt;/wicket:panel&gt;
 *
 *       [...]</pre>
 * <p>
 * would have the Apache Wicket path:
 *
 * <pre>
 *     HomePage/sign-in-panel/sign-in</pre>
 *
 * <b>Path Patterns</b>
 *
 * <p>
 * The path patterns defined by rules perform matching against the full path of each component on a page. Patterns allow
 * wildcards (&#42;) that match any number of path elements (similar to "**" in some familiar pattern matching
 * contexts). So, in the case of the first rule above, the path pattern "&#42;/customer-explore-card/&#42;/card-panel"
 * matches all the following:
 *
 * <pre>
 * HomePage/customer-explore-card/card-panel
 * HomePage/a/b/customer-explore-card/c/d/card-panel
 * ViewPage/a/b/customer-explore-card/c/d/card-panel</pre>
 *
 * @see StyleRule
 * @see Styler
 * @see ComponentPath
 * @see ComponentPathPattern
 */
public class StyleYamlParser
{
    private LinkedList<String> lines;

    private String line;

    /**
     * Parses the given YAML text into a list of style rules
     *
     * @param text The text to parse
     * @return The list of style rules
     */
    public List<StyleRule> parseRules(String text)
    {
        var rules = new ArrayList<StyleRule>();

        // Split the text into lines,
        lines = new LinkedList<>(Arrays.asList(text.split("\n")));

        // then loop through the lines until we run out,
        for (line = nextLine(); line != null; line = nextLine())
        {
            // and if the line is not blank or a comment,
            if (!line.isBlank() && !isComment(line))
            {
                // then the line must be the start of a YAML rule definition, so we require that,
                requireExactly("- rule:");

                // then we parse the required attributes in the required order and add the rule.
                rules.add(new StyleRule(
                    parseComponentPathPattern(requireAttribute("path")),
                    parseModification(requireAttribute("do")),
                    requireAttribute("attr"),
                    requireAttribute("value")));
            }
        }

        return rules;
    }

    /**
     * Raises a parser error if the current line does not match the given text exactly
     */
    private void requireExactly(String text)
    {
        // If the current line is null, or it doesn't match the given text,
        if (line == null || !line.equals(text))
        {
            // then raise a parse error.
            parseError("Expected '" + text + "', not: " + line);
        }
    }

    /**
     * @return The next line in the list of lines
     */
    private String nextLine()
    {
        return lines.isEmpty() ? null : lines.removeFirst();
    }

    /**
     * Requires that the current line starts with the YAML attribute syntax "  [name]: ", raising a parser error if it
     * does not
     *
     * @param name The name of the attribute that's required
     */
    private String requireAttribute(String name)
    {
        // If the line doesn't start with a YAML attribute prefix of the form "  <name>: " (including the two
        // space indent, the colon and the trailing space),
        var yamlAttributePrefix = "  " + name + ": ";
        if (!line.startsWith(yamlAttributePrefix))
        {
            // signal that the required attribute was not found,
            return parseError("Expected a line starting with '" + yamlAttributePrefix + "', not: " + line);
        }
        else
        {
            // otherwise, return the attribute value.
            return unquote(line.substring(yamlAttributePrefix.length()));
        }
    }

    /**
     * @return True if the given line is a comment, meaning that it starts with //
     */
    private boolean isComment(final String line)
    {
        return line.strip().startsWith("//");
    }

    /**
     * @param value The value to unquote
     * @return The value without any surrounding double quotes
     */
    private String unquote(final String value)
    {
        if (value.startsWith("\"") && value.endsWith("\""))
        {
            return value.substring(1, value.length() - 1);
        }
        else
        {
            if (!value.matches("[A-Za-z-_]+"))
            {
                parseError("Bare string values can only contain numbers, digits, underscores and dashes: " + value);
            }
        }
        return value;
    }

    /**
     * Signal a parsing error by throwing an exception
     */
    private String parseError(String error)
    {
        throw new WicketRuntimeException(error);
    }
}
