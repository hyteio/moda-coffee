package io.modacoffee.web.pages;

import org.apache.wicket.Page;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import io.modacoffee.web.WebApplication;
import io.modacoffee.web.WebApplication.LastPageError;

public class SessionExpiredPage extends Page {

    private static final long serialVersionUID = 1L;

    public SessionExpiredPage() {
        this(null);
    }

    public SessionExpiredPage(PageParameters parameters) {
        super(parameters);
        Session.get().setLastPageError(new LastPageError("Session expired, please login.", LogLevel.INFO));
        setResponsePage(WebApplication.get().getSignInPageClass());
    }

    @Override
    protected boolean isSecuredPage() {
        return false;
    }

    @Override
    protected boolean addBreadcrumbForPage() {
        return false;
    }
}
