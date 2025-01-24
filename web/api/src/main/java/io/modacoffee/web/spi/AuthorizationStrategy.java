package io.modacoffee.web.spi;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.wicket.Component;
import org.apache.wicket.authorization.Action;
import org.apache.wicket.authorization.IAuthorizationStrategy;
import org.apache.wicket.request.component.IRequestableComponent;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.request.resource.IResource;

public class AuthorizationStrategy implements IAuthorizationStrategy {
    private static final Logger logger = LogManager.getLogger(AuthorizationStrategy.class);

    @Override
    public <T extends IRequestableComponent> boolean isInstantiationAuthorized(Class<T> componentClass) {
        if (logger.isTraceEnabled()) {
            logger.trace("Authorizing Instantiation: " + componentClass.getName());
        }
        return true;
    }

    @Override
    public boolean isActionAuthorized(Component component, Action action) {
        // return WebSecurityUtil.authorizeComponent(component);
        return true;
    }

    @Override
    public boolean isResourceAuthorized(IResource resource, PageParameters parameters) {
        return true;
    }
}
