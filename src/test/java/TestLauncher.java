
import first.UserServiceTest;
import org.junit.platform.engine.DiscoverySelector;
import org.junit.platform.engine.discovery.DiscoverySelectors;
import org.junit.platform.launcher.LauncherDiscoveryRequest;
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.junit.platform.launcher.core.LauncherFactory;
import org.junit.platform.launcher.listeners.SummaryGeneratingListener;

import java.io.PrintWriter;

public class TestLauncher {


    public static void main(String[] args) {
       var launcher = LauncherFactory.create();
        var summaryGenerationListener = new SummaryGeneratingListener();
      // launcher.registerLauncherDiscoveryListeners();
       //launcher.registerLauncherDiscoveryListeners();


       LauncherDiscoveryRequest request = LauncherDiscoveryRequestBuilder.request()
               //.selectors(DiscoverySelectors.selectClass(UserServiceTest.class))
               .selectors(DiscoverySelectors.selectPackage("first"))
               //.listeners()
                               .build();

       launcher.execute(request, summaryGenerationListener);

       try (var writer = new PrintWriter(System.out)) {
           summaryGenerationListener.getSummary().printTo(writer);
       }
    }
}
