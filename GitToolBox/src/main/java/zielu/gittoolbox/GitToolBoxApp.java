package zielu.gittoolbox;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.ApplicationComponent;
import com.intellij.openapi.diagnostic.Logger;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class GitToolBoxApp implements ApplicationComponent {
  private final Logger log = Logger.getInstance(getClass());
  private ScheduledExecutorService autoFetchExecutor;

  public static GitToolBoxApp getInstance() {
    return ApplicationManager.getApplication().getComponent(GitToolBoxApp.class);
  }

  public ScheduledExecutorService autoFetchExecutor() {
    return autoFetchExecutor;
  }

  @Override
  public void initComponent() {
    autoFetchExecutor = Executors.newSingleThreadScheduledExecutor(
        new ThreadFactoryBuilder().setDaemon(true).setNameFormat("AutoFetch-%s").build()
    );
  }

  @Override
  public void disposeComponent() {
    autoFetchExecutor.shutdownNow().forEach(notStarted -> log.info("Task " + notStarted + " was never started"));
  }
}
