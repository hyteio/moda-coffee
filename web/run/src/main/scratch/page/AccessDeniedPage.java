package io.modacoffee.web.page;

import org.apache.wicket.Page;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import io.modacoffee.web.WebApplication;
import io.modacoffee.web.WebApplication.LastPageError;

public class AccessDeniedPage extends Page {

    private static final long serialVersionUID = 1L;

    public AccessDeniedPage() {
        this(null);
    }

    public AccessDeniedPage(PageParameters parameters) {
        super(parameters);
        WebApplication app = WebApplication.get();
        Session.get().setLastPageError(new LastPageError("Access denied.", LogLevel.ERROR));
        if (!WebUtil.isUserLoggedIn()) {
            setResponsePage(app.getSignInPageClass());
        } else {
            setResponsePage(app.getHomePage());
        }
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
