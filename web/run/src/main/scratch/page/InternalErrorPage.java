package io.modacoffee.web.page;

import org.apache.wicket.request.mapper.parameter.PageParameters;

import io.modacoffee.web.WebApplication;

public class InternalErrorPage extends org.apache.wicket.Page {

    private static final long serialVersionUID = 1L;

    public InternalErrorPage() {
        this(null);
    }

    public InternalErrorPage(PageParameters parameters) {
        super(parameters);
        WebApplication app = WebApplication.get();
        // lastPageError is handled by our ExceptionManager catching the error
        // TODO: jbaker investigate why this blows up w/ Java 1.8
        // setResponsePage(app.getHomePage().equals(previousPage) ?
        // app.getSignInPageClass() : app.getHomePage());
        if (app == null) {
            setResponsePage(LoginPage.class);
        } else if (app.getHomePage() != null && !WebUtil.isUserLoggedIn()) {
            setResponsePage(app.getSignInPageClass());
        } else {
            setResponsePage(app.getHomePage());
        }
    }

}
