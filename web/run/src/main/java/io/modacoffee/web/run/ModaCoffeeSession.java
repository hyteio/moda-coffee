package io.modacoffee.web.run;

import io.modacoffee.web.model.Cart;
import org.apache.wicket.protocol.http.WebSession;
import org.apache.wicket.request.Request;

public class ModaCoffeeSession extends WebSession
{
    private final Cart cart = new Cart();

    public ModaCoffeeSession(final Request request)
    {
        super(request);
    }

    public Cart cart()
    {
        return cart;
    }
}
