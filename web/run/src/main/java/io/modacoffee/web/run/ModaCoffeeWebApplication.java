package io.modacoffee.web.run;

import de.agilecoders.wicket.core.Bootstrap;
import de.agilecoders.wicket.core.settings.BootstrapSettings;
import io.modacoffee.web.pages.home.HomePage;
import io.modacoffee.web.pages.menu.MenuPage;
import io.modacoffee.web.pages.order.checkout.CheckoutPage;
import io.modacoffee.web.pages.order.status.OrderStatusPage;
import io.modacoffee.web.pages.order.thanks.ThankYouPage;
import org.apache.wicket.Page;
import org.apache.wicket.Session;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.Response;
import org.apache.wicket.util.file.Path;

import java.io.File;
import java.time.Duration;

import static org.apache.wicket.RuntimeConfigurationType.DEVELOPMENT;
import static org.apache.wicket.settings.ExceptionSettings.SHOW_EXCEPTION_PAGE;

public class ModaCoffeeWebApplication extends WebApplication
{

    @Override
    protected void init()
    {
        initializeKaraf();

        super.init();

        configureApacheWicket();
        configureApacheWicketBootstrap();

        mountPage("/", HomePage.class);
        mountPage("/home", HomePage.class);
        mountPage("/menu", MenuPage.class);
        mountPage("/order/checkout", CheckoutPage.class);
        mountPage("/order/status", OrderStatusPage.class);
        mountPage("/order/thank-you", ThankYouPage.class);
    }

    @Override
    public Session newSession(final Request request, final Response response)
    {
        return new ModaCoffeeSession(request);
    }

    private void configureApacheWicketBootstrap()
    {
        var settings = new BootstrapSettings();
        Bootstrap.install(this, settings);
    }

    private void configureApacheWicket()
    {
        if (getConfigurationType() == DEVELOPMENT)
        {
            getDebugSettings()
                .setComponentUseCheck(true)
                .setDevelopmentUtilitiesEnabled(true)
                .setOutputMarkupContainerClassName(true);

            getExceptionSettings().setUnexpectedExceptionDisplay(SHOW_EXCEPTION_PAGE);

            getMarkupSettings()
                .setStripWicketTags(false)
                .setStripComments(false);

            getResourceSettings()
                .setResourcePollFrequency(Duration.ofSeconds(1))
                .getResourceFinders().addFirst(new Path("src/main/java"));
        }
    }

    private void initializeKaraf()
    {
        // Set a fake karaf.home for bundle.web's upload testing.
        System.setProperty("karaf.home", new File("." + File.separator + "target" + File.separator + "test.karaf.home").getAbsolutePath());
    }

    @Override
    public Class<? extends Page> getHomePage()
    {
        return HomePage.class;
    }

    //    @Override
    //    public Boot getBoot() {
    //        if (this.boot == null) {
    //            try {
    //                DefaultBoot defaultBoot = new DefaultBoot();
    //                BootConfig bootConfig = BootConfigBuilder.create().setConfigInitDisabled(false)
    //                        .setDataDir("data" + File.separator + "hyte").setModuleActivationDisabled(true)
    //                        .setServiceInitDisabled(false).setScanSource(ScanSource.CLASSLOADER).setTestModeEnabled(true)
    //                        .setFileLockingDisabled(false).setTestCloneBaseDir("src/test/resources/hyte-boot").build();
    //                defaultBoot.activate(bootConfig);
    //                DefaultLdapStoreService ldapStoreService = defaultBoot.getAppContext()
    //                        .getService(DefaultLdapStoreService.class);
    //                if (ldapStoreService != null) {
    //                    ldapStoreService.updateConfigDirectoryFromBoot(bootConfig);
    //                }
    //                this.boot = defaultBoot;
    //            } catch (Exception e) {
    //                e.printStackTrace();
    //                throw new RuntimeException(e);
    //            }
    //        }
    //        return this.boot;
    //    }
}
