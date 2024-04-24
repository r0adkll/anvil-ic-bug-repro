package com.r0adkll.anvil_ic.di;

import android.app.Application;
import android.content.Context;
import com.r0adkll.anvil_ic.di.module.CoroutinesModule_ProvideDispatcherProviderFactory;
import com.r0adkll.common.coroutines.DispatcherProvider;
import com.r0adkll.feed.impl.FeedRepositoryImpl;
import com.r0adkll.feed.impl.cache.InMemoryFeedCache;
import com.r0adkll.feed.impl.cache.InMemoryFeedCache_Factory;
import com.r0adkll.feed.impl.datasource.NetworkFeedDataSource;
import com.r0adkll.feed.impl.ui.FeedComponent;
import com.r0adkll.feed.impl.ui.FeedScreen;
import com.r0adkll.feed.impl.ui.FeedScreen_MembersInjector;
import dagger.internal.DaggerGenerated;
import dagger.internal.DoubleCheck;
import dagger.internal.Preconditions;
import dagger.internal.Provider;
import javax.annotation.processing.Generated;

@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava",
    "cast"
})
public final class DaggerAppComponent {
  private DaggerAppComponent() {
  }

  public static AppComponent.Factory factory() {
    return new Factory();
  }

  private static final class Factory implements AppComponent.Factory {
    @Override
    public AppComponent create(Application application) {
      Preconditions.checkNotNull(application);
      return new AppComponentImpl(application);
    }
  }

  private static final class UserComponentFactory implements UserComponent.Factory {
    private final AppComponentImpl appComponentImpl;

    private UserComponentFactory(AppComponentImpl appComponentImpl) {
      this.appComponentImpl = appComponentImpl;
    }

    @Override
    public UserComponent create() {
      return new UserComponentImpl(appComponentImpl);
    }
  }

  private static final class FeedComponentFactory implements FeedComponent.Factory {
    private final AppComponentImpl appComponentImpl;

    private final UserComponentImpl userComponentImpl;

    private FeedComponentFactory(AppComponentImpl appComponentImpl,
        UserComponentImpl userComponentImpl) {
      this.appComponentImpl = appComponentImpl;
      this.userComponentImpl = userComponentImpl;
    }

    @Override
    public FeedComponent create(Context activityContext) {
      Preconditions.checkNotNull(activityContext);
      return new FeedComponentImpl(appComponentImpl, userComponentImpl, activityContext);
    }
  }

  private static final class FeedComponentImpl implements FeedComponent {
    private final AppComponentImpl appComponentImpl;

    private final UserComponentImpl userComponentImpl;

    private final FeedComponentImpl feedComponentImpl = this;

    private FeedComponentImpl(AppComponentImpl appComponentImpl,
        UserComponentImpl userComponentImpl, Context activityContextParam) {
      this.appComponentImpl = appComponentImpl;
      this.userComponentImpl = userComponentImpl;


    }

    @Override
    public void inject(FeedScreen screen) {
      injectFeedScreen(screen);
    }

    private FeedScreen injectFeedScreen(FeedScreen instance) {
      FeedScreen_MembersInjector.injectFeedRepository(instance, userComponentImpl.feedRepositoryImpl());
      return instance;
    }
  }

  private static final class UserComponentImpl implements UserComponent {
    private final AppComponentImpl appComponentImpl;

    private final UserComponentImpl userComponentImpl = this;

    private Provider<InMemoryFeedCache> inMemoryFeedCacheProvider;

    private UserComponentImpl(AppComponentImpl appComponentImpl) {
      this.appComponentImpl = appComponentImpl;

      initialize();

    }

    private NetworkFeedDataSource networkFeedDataSource() {
      return new NetworkFeedDataSource(appComponentImpl.provideDispatcherProvider.get());
    }

    private FeedRepositoryImpl feedRepositoryImpl() {
      return new FeedRepositoryImpl(inMemoryFeedCacheProvider.get(), networkFeedDataSource());
    }

    @SuppressWarnings("unchecked")
    private void initialize() {
      this.inMemoryFeedCacheProvider = DoubleCheck.provider(InMemoryFeedCache_Factory.create());
    }

    @Override
    public FeedComponent.Factory getFeedComponentFactory() {
      return new FeedComponentFactory(appComponentImpl, userComponentImpl);
    }

    @Override
    public Application getApplication() {
      return appComponentImpl.application;
    }
  }

  private static final class AppComponentImpl implements AppComponent {
    private final Application application;

    private final AppComponentImpl appComponentImpl = this;

    private Provider<DispatcherProvider> provideDispatcherProvider;

    private AppComponentImpl(Application applicationParam) {
      this.application = applicationParam;
      initialize(applicationParam);

    }

    @SuppressWarnings("unchecked")
    private void initialize(final Application applicationParam) {
      this.provideDispatcherProvider = DoubleCheck.provider(CoroutinesModule_ProvideDispatcherProviderFactory.create());
    }

    @Override
    public UserComponent.Factory getUserComponentFactory() {
      return new UserComponentFactory(appComponentImpl);
    }
  }
}
