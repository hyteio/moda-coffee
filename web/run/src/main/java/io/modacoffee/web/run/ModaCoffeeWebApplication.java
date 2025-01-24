package io.modacoffee.web.run;

import java.io.File;

import org.apache.wicket.Page;
import org.apache.wicket.protocol.http.WebApplication;

import io.modacoffee.web.page.HomePage;

public class ModaCoffeeWebApplication extends WebApplication {

    @Override
    protected void init() {
        // set a fake karaf.home for bundle.web's upload testing.
        System.setProperty("karaf.home", new File("." + File.separator + "target" + File.separator + "test.karaf.home").getAbsolutePath());
        super.init();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public Class<? extends Page> getHomePage() {
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
