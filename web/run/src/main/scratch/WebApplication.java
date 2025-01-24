/** 
 * HYTE TECHNOLOGIES, INC. CONFIDENTIAL
 * 
 * Copyright © 2017 - 2020 HYTE Technologies, Inc. All Rights Reserved.
 *  
 * NOTICE:  All information contained herein is, and remains the property of HYTE Technologies, Inc. 
 * and its suppliers, if any.  The intellectual and technical concepts contained herein are 
 * proprietary to HYTE Technologies, Inc. and its suppliers and may be covered by U.S. and Foreign 
 * Patents, patents in process, and are protected by trade secret or copyright law.  Dissemination 
 * of this information or reproduction of this material is strictly forbidden unless prior written 
 * permission is obtained from HYTE Technologies, Inc.
 */
package io.modacoffee.web;

import java.io.File;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.wicket.Component;
import org.apache.wicket.IRequestCycleProvider;
import org.apache.wicket.RestartResponseException;
import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.AjaxRequestTarget.IListener;
import org.apache.wicket.ajax.attributes.AjaxRequestAttributes;
import org.apache.wicket.authorization.Action;
import org.apache.wicket.authorization.IAuthorizationStrategy;
import org.apache.wicket.authorization.UnauthorizedActionException;
import org.apache.wicket.authroles.authentication.AbstractAuthenticatedWebSession;
import org.apache.wicket.authroles.authentication.AuthenticatedWebApplication;
import org.apache.wicket.core.request.handler.IPageRequestHandler;
import org.apache.wicket.core.request.handler.PageProvider;
import org.apache.wicket.core.request.handler.RenderPageRequestHandler.RedirectPolicy;
import org.apache.wicket.core.request.mapper.StalePageException;
import org.apache.wicket.devutils.debugbar.DebugBarInitializer;
import org.apache.wicket.devutils.inspector.RenderPerformanceListener;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.pages.RedirectPage;
import org.apache.wicket.request.IRequestHandler;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.Response;
import org.apache.wicket.request.Url;
import org.apache.wicket.request.UrlRenderer;
import org.apache.wicket.request.component.IRequestableComponent;
import org.apache.wicket.request.component.IRequestablePage;
import org.apache.wicket.request.cycle.IRequestCycleListener;
import org.apache.wicket.request.cycle.PageRequestHandlerTracker;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.cycle.RequestCycleContext;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.request.mapper.parameter.PageParametersEncoder;
import org.apache.wicket.request.resource.IResource;
import org.apache.wicket.serialize.java.DeflatedJavaSerializer;
import org.apache.wicket.settings.DebugSettings;

import io.modacoffee.web.spi.AuthorizationStrategy;
import io.modacoffee.web.spi.HttpsForcingRequestCycleProvider;

public class WebApplication extends AuthenticatedWebApplication {
    protected static final Logger log = LogManager.getLogger(WebApplication.class);
    public static final String LAST_PARAM_PREFIX = "MODA.LPP.";
    public static final String LAST_PAGE_NAME = LAST_PARAM_PREFIX + "MODA.LPN";

    @Override
    protected void init() {
        super.init();
        try {
            log.info("Initializing HYTE Console");
            // disable new CSP wicket 9.x functionality per migration guide
            getCspSettings().blocking().disabled();

            if (getApplicationSettingsPage() != null) {
                mount(new WebMountedMapper(getApplicationSettingsPage().getSimpleName(), getApplicationSettingsPage()));
            }

            getAjaxRequestTargetListeners().add(new IListener() {
                @Override
                public void onBeforeRespond(Map<String, Component> map, AjaxRequestTarget target) {
                    org.apache.wicket.Page p = target.getPage();
                    if (p instanceof Page) {
                        ((Page) p).onAjaxEvent(target);
                    }
                }

                @Override
                public void updateAjaxAttributes(AbstractDefaultAjaxBehavior behavior, AjaxRequestAttributes attributes) {
                    /*
                     * FIXME: Have a config for AjaxReqeustWiatTimeInSeconds
                     * 
                     *  WebAppConfig config = CoreModule.getConfig();
                        if (config != null) {
                            attributes.setRequestTimeout(Duration.ofSeconds(config.getAjaxRequestWaitTimeInSeconds()));
                        }
                    */
                    attributes.setRequestTimeout(Duration.ofSeconds(15));
                }
            });
            getApplicationSettings().setAccessDeniedPage(AccessDeniedPage.class);
            getApplicationSettings().setInternalErrorPage(InternalErrorPage.class);
            getApplicationSettings().setPageExpiredErrorPage(PageExpiredPage.class);
            // enable fetching page from request:
            // https://cwiki.apache.org/confluence/display/WICKET/RequestCycle+in+Wicket+1.5
            getRequestCycleListeners().add(new PageRequestHandlerTracker());
            WebExceptionUtil.addExceptionHandler(new WebApplicationExceptionHandler());
            getRequestCycleListeners().add(new RequestManager());
            getSecuritySettings().setAuthorizationStrategy(new AuthorizationStrategy());

            initializeApplication();

            for (Module module : getBoot().getAppContext().listSortedModules()) {
                if (!WebModule.class.isAssignableFrom(module.getClass())) {
                    continue;
                }
                WebModule webModule = (WebModule) module;
                handleMounting(webModule, true);
                ServerFeatureDelegate d = webModule.getDashboardDelegate();
                if (d != null) {
                    WebUtil.getServerManager().getDelegates().add(d);
                }
                ConfigDelegate.registerDelegate(webModule.getConfigDelegate());
            }

            getDebugSettings().setAjaxDebugModeEnabled(true);
            getDebugSettings().setComponentUseCheck(false);

            // WebAppConfig config = CoreModule.getConfig();
            // if (Boolean.TRUE.equals(config.isEnableWicketDebug())) {
            if (Boolean.TRUE.equals(true)) {
                DebugSettings ds = getDebugSettings();
                ds.setAjaxDebugModeEnabled(true);
                ds.setComponentPathAttributeName("debugComponentPath");
                ds.setDevelopmentUtilitiesEnabled(true);
                ds.setLinePreciseReportingOnAddComponentEnabled(true);
                ds.setLinePreciseReportingOnNewComponentEnabled(true);
                new DebugBarInitializer().init(this);
            }

            if (Boolean.TRUE.equals(true)) {
                setRequestCycleProvider(new HttpsForcingRequestCycleProvider());
            }

            if (Boolean.TRUE.equals(false)) {
                getFrameworkSettings().setSerializer(new DeflatedJavaSerializer(getApplicationKey()));
            }

//            getMarkupSettings().setCompressWhitespace(Boolean.TRUE.equals(config.isSessionCompressWhitespaceEnabled()));
//            getStoreSettings().setAsynchronous(Boolean.TRUE.equals(config.isSessionAsynchronousPageStoreEnabled()));
//            getRequestCycleSettings().setTimeout(Duration.ofSeconds(config.getRequestWaitTimeInSeconds()));

            getMarkupSettings().setCompressWhitespace(Boolean.TRUE.equals(false));
            getStoreSettings().setAsynchronous(Boolean.TRUE.equals(false));
            getRequestCycleSettings().setTimeout(Duration.ofSeconds(15));

            if (Boolean.TRUE.equals(true)) {
                getComponentInstantiationListeners().add(new RenderPerformanceListener() {
                    @Override
                    protected boolean accepts(Component component) {
                        return true;
                    }
                });
            }
            log.info("MODA Web initialization complete. Version: " + Revision.getRevisionDisplayName()
                    + ", Config Version: " + configVersion);
        } catch (Throwable e) {
            log.error("Error occurred while bootstrapping application.", e);
            throw new WebException("Error occurred while bootstrapping application.", e);
        }
    }

    private static class WebApplicationExceptionHandler extends ExceptionHandler {
        @Override
        public boolean handleException(Throwable e) {
            Throwable rootCause = e;
            while (rootCause.getCause() != null) {
                rootCause = rootCause.getCause();
            }
            if (e instanceof WebException) {
                if (rootCause instanceof SubscriptionException || rootCause instanceof AuthAuthenticationException
                        || rootCause instanceof AuthNotFoundException || rootCause instanceof AuthExistsException) {
                    WebExceptionUtil.feedbackError(rootCause);
                    if (log.isDebugEnabled()) {
                        log.debug(rootCause.getMessage(), e);
                    } else {
                        log.warn(rootCause.getMessage());
                    }
                    return true;
                }
                WebExceptionUtil.feedbackInternalError(e);
                return true;
            } else if (e instanceof NullPointerException) {
                WebExceptionUtil.feedbackInternalError(e);
                return true;
            } else if (e instanceof StalePageException) {
                if (log.isDebugEnabled()) {
                    log.debug("StalePageException occurred, cause: " + rootCause.getMessage(), e);
                }
                return true;
            } else if (e instanceof RestartResponseException) {
                if (log.isDebugEnabled()) {
                    log.debug("RestartResponseException occurred, cause: " + rootCause.getMessage(), e);
                }
                return true;
            } else if (e instanceof UnauthorizedActionException) {
                if (log.isDebugEnabled()) {
                    log.debug("Unauthorized page or action access attempt occurred, cause: " + rootCause.getMessage(),
                            e);
                }
                return true;
            } else if (e instanceof SubscriptionException || e instanceof AuthAuthenticationException
                    || e instanceof AuthNotFoundException || e instanceof AuthExistsException) {
                WebExceptionUtil.feedbackError(e);
                if (log.isDebugEnabled()) {
                    log.debug(rootCause.getMessage(), e);
                } else {
                    log.warn(rootCause.getMessage());
                }
                return true;
            }
            return false;
        }
    }

    public void reinitializeConfiguration(String configDirectoryAbsolutePath) throws Exception {
        log.info("Configuration is being re-created, path:" + configDirectoryAbsolutePath);

        // FIXME: Drop any trailing "/" or "\" characters
        // clear out change data dir, write boot config back to disk
        BootConfig bootConfig = WebUtil.getAppContext().getBootConfig();
        bootConfig.setDataDir(configDirectoryAbsolutePath);
        Properties propertiesFile = BootModelUtil.toProperties(bootConfig);

        try (FileOutputStream bootCfg = new FileOutputStream(
                bootConfig.getCfgDir() + File.separator + "io.hyte.boot.cfg")) {
            propertiesFile.store(bootCfg, null);
        }

        // clear out initialized modules config
        WebUtil.getAppContext().getInitializedModules().clear();
        destroy();
        initializeApplication();
    }

    protected void initializeApplication() throws Exception {
        Boot tmpBoot = getBoot();
        AppContext appContext = tmpBoot.getAppContext();
        WebUtil.setAppContext(appContext);

        if (appContext.listSortedModules().isEmpty()) {
            throw new WebException("Cannot bootstrap application, no modules are registered.");
        }
        WebUtil.init();

        tmpBoot.activateModules();
        customizeConfiguration();
        ConfigurationUpdater.updateConfiguration();

        // tell ReplicaImportBootListener to run import
        Event event = EventModelUtil.generateEvent(ModulePhase.class, EventType.INFO, ModulePhase.ACTIVE.value());
        WebUtil.getEventService().send(event);
    }

    protected void customizeConfiguration() {
    }

    protected void destroy() {}

    @Override
    protected void onDestroy() {
        log.info("HYTE Console is being shutdown.");
        /*
         * Note: This specific module is not declared like we normally would in a
         * wicket.properties file, because this console.core.web jar provides wicket the
         * ConsoleApplication, not just a module. Because of this, wicket has no idea
         * about ConsoleSecurityModule, and doesn't know to initialize or destroy it, so
         * we do that manually here when the application goes down.
         */
        super.onDestroy();
        destroy();
    }

    public static Page getCurrentPage() {
        RequestCycle requestCycle = RequestCycle.get();
        if (requestCycle == null) {
            return null;
        }
        IPageRequestHandler handler = PageRequestHandlerTracker.getLastHandler(requestCycle);
        IRequestablePage page = handler == null ? null : handler.getPage();
        return (page != null && page instanceof Page) ? (Page) page : null;
    }

    private class RequestManager implements IRequestCycleListener {
        @Override
        public void onBeginRequest(RequestCycle cycle) {
            User user = WebUtil.getUser();
            if (user != null) {
                WebAuditService.setRemoteAddressForUser(user);
            }
        }

        @Override
        public IRequestHandler onException(RequestCycle cycle, Exception ex) {
            WebExceptionUtil.handleException(ex);
            return null;
        }
    }

    public static WebApplication get() {
        return (WebApplication) AuthenticatedWebApplication.get();
    }

    public void doLogout(Page lastPage, PageParameters lastPageParameters, boolean returnToThisPage) {
        Session session = Session.get();
        if (session.getAuthSessionId() != null) {
            AuthSession authSession = session.getAuthSession(false);
            User user = authSession == null ? null : authSession.getUser();
            WebUtil.getAuditService().auditAction("Logout", user, "Auth Session Id: " + session.getAuthSessionId(),
                    true);
        }

        LastPageError lastPageError = session.getLastPageError();

        // invalidate session, then create new one via Session.get()
        session.invalidateNow();
        session = Session.get();

        if (lastPageError != null) {
            session.setLastPageError(lastPageError);
        }

        PageParameters signInPageParams = new PageParameters();
        if (returnToThisPage && lastPage != null) {
            lastPageParameters = lastPageParameters == null ? new PageParameters() : lastPageParameters;
            String url = lastPage.getUrlForPage(lastPage.getClass(), lastPageParameters);
            signInPageParams.add("LAST_PAGE_URL", url);
        }
        if (lastPage != null) {
            lastPage.setResponsePage(getSignInPageClass(), signInPageParams);
        } else {
            throw new RestartResponseException(new PageProvider(getSignInPageClass(), signInPageParams),
                    RedirectPolicy.AUTO_REDIRECT);
        }
    }

    public void restoreLastPageOrHomePage(Page currentPage, String errorMessage, boolean restartResponse) {
        Session s = Session.get();
        if (s != null) {
            s.setLastPageError(errorMessage == null ? null : new LastPageError(errorMessage, LogLevel.WARN));
        }

        String redirectURL = currentPage.getPageParameterAsString("LAST_PAGE_URL");
        Class<? extends WebPage> targetPage = getHomePage();
        if (WebUtil.shouldShowLicenseToUser()) {
            targetPage = WebUtil.getConfig().getLicensePage();
        } else if (WebUtil.shouldShowEmailVerificationToUser()) {
            targetPage = WebUtil.getConfig().getEmailVerificationPage();
        } else if (redirectURL != null) {
            if (log.isDebugEnabled()) {
                log.debug("Redirecting user to url: " + redirectURL + ", message: " + errorMessage);
            }
            RequestCycle.get().setResponsePage(new RedirectPage(redirectURL));
            return;
        }
        if (log.isDebugEnabled()) {
            log.debug("Redirecting user to page: " + targetPage.getName() + ", message: " + errorMessage);
        }
        if (restartResponse) {
            throw new RestartResponseException(targetPage);
        }
        RequestCycle.get().setResponsePage(targetPage);
    }

    public Class<? extends IRequestablePage> getApplicationSettingsPage() {
        return ConfigPage.class;
    }

    protected void handleMounting(WebModule module, boolean doMount) {
        List<WebMountedMapper> mounts = module.getPathMap();
        if (mounts == null) {
            return;
        }
        for (WebMountedMapper mapper : mounts) {
            if (doMount) {
                mount(mapper);
            } else {
                unmount(mapper.getMountPath());
            }
        }
    }

    @Override
    public Session newSession(Request request, Response response) {
        Session session = new Session(request);
        session.bind();
        if (log.isDebugEnabled()) {
            log.debug("Created session: " + session);
        }
        return session;
    }

    @Override
    protected Class<? extends AbstractAuthenticatedWebSession> getWebSessionClass() {
        return Session.class;
    }

    @Override
    public Class<? extends WebPage> getHomePage() {
        return WebUtil.getConfig().getHomePage();
    }

    public static class LastPageError implements Serializable {
        private static final long serialVersionUID = 1L;

        public static enum LogLevel {
            INFO, WARN, ERROR
        }

        private String errorMessage;
        private LogLevel logLevel;

        public LastPageError(String errorMessage, LogLevel logLevel) {
            this.errorMessage = errorMessage;
            this.logLevel = logLevel;
        }

        public String getErrorMessage() {
            return errorMessage;
        }

        public void setErrorMessage(String errorMessage) {
            this.errorMessage = errorMessage;
        }

        public LogLevel getLogLevel() {
            return logLevel;
        }

        public void setLogLevel(LogLevel logLevel) {
            this.logLevel = logLevel;
        }
    }

    public static class WebMountedMapper extends org.apache.wicket.core.request.mapper.MountedMapper {
        protected String mountPath;

        public WebMountedMapper(String mountPath, Class<? extends IRequestablePage> pageClass) {
            super(mountPath, pageClass, new PageParametersEncoder());
            this.mountPath = mountPath;
        }

        public String getMountPath() {
            return mountPath;
        }
    }

    public boolean sendConsoleEvent(String eventId, Object eventData, Page currentPage, boolean returnOnFirstHandle) {
        boolean result = false;
        for (WebModule m : WebUtil.getWebModules()) {
            if (m.handleConsoleEvent(eventId, eventData, currentPage)) {
                result = true;
                if (returnOnFirstHandle) {
                    return true;
                }
            }
        }
        return result;
    }

    @Override
    protected Class<? extends WebPage> getSignInPageClass() {
        try {
            boolean installFinished = Boolean.TRUE.equals(getBoot().getBootStore().isInstallFinished());
            return installFinished ? LoginPage.class : InstallPage.class;
        } catch (Exception e) {
            log.error("Exception while determining sign in page class, returning LoginPage as the page to use.", e);
            return LoginPage.class;
        }
    }

}
