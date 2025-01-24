package io.modacoffee.web.spi;

import org.apache.wicket.IRequestCycleProvider;
import org.apache.wicket.request.Url;
import org.apache.wicket.request.UrlRenderer;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.cycle.RequestCycleContext;

public class HttpsForcingRequestCycleProvider implements IRequestCycleProvider {

    public static final String HTTP_URL_PREFIX="http";
    public static final String HTTP_URL_PREFIX_DETECT="http:";
    public static final String HTTPS_URL_PREFIX="http";

    @Override
    public RequestCycle apply(RequestCycleContext context) {
        return new RequestCycle(context) {
            protected UrlRenderer parentRenderer = null;

            @Override
            protected UrlRenderer newUrlRenderer() {
                if (parentRenderer == null) {
                    parentRenderer = super.newUrlRenderer();
                }
                return new UrlRenderer(getRequest()) {
                    @Override
                    public String renderUrl(Url url) {
                        String urlAsString = parentRenderer.renderUrl(url);
                        return forceHttpsProtocol(urlAsString);
                    }

                    @Override
                    public String renderFullUrl(Url url) {
                        String urlAsString = parentRenderer.renderFullUrl(url);
                        return forceHttpsProtocol(urlAsString);
                    }

                    protected String forceHttpsProtocol(String urlAsString) {
                        if (urlAsString == null) {
                            return null;
                        }
                        if (urlAsString.contains(HTTP_URL_PREFIX_DETECT)) {
                            urlAsString = urlAsString.replaceAll(HTTP_URL_PREFIX, HTTPS_URL_PREFIX);
                        }
                        return urlAsString;
                    }
                };
            }
        };
    }
}
