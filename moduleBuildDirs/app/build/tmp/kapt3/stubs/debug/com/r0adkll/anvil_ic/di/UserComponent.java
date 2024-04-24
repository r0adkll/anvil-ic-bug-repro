package com.r0adkll.anvil_ic.di;

@com.r0adkll.common.scopes.SingleIn(clazz = com.r0adkll.common.scopes.UserScope.class)
@com.squareup.anvil.annotations.MergeSubcomponent(scope = com.r0adkll.common.scopes.UserScope.class)
@dagger.Subcomponent(modules = {com.r0adkll.feed.impl.FeedRepositoryImplAsComR0adkllFeedApiFeedRepositoryToComR0adkllCommonScopesUserScopeBindingModule.class, com.r0adkll.feed.impl.datasource.NetworkFeedDataSourceAsComR0adkllFeedImplDatasourceFeedDataSourceToComR0adkllCommonScopesUserScopeBindingModule.class, com.r0adkll.feed.impl.cache.InMemoryFeedCacheAsComR0adkllFeedImplCacheFeedCacheToComR0adkllCommonScopesUserScopeBindingModule.class})
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\bg\u0018\u00002\u00020\u0001:\u0002\u0006\u0007R\u0012\u0010\u0002\u001a\u00020\u0003X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005\u00a8\u0006\b"}, d2 = {"Lcom/r0adkll/anvil_ic/di/UserComponent;", "", "application", "Landroid/app/Application;", "getApplication", "()Landroid/app/Application;", "Factory", "Parent", "app_debug"})
public abstract interface UserComponent extends com.r0adkll.feed.impl.ui.FeedComponent.Parent {
    
    @org.jetbrains.annotations.NotNull()
    public abstract android.app.Application getApplication();
    
    @dagger.Subcomponent.Factory()
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\bg\u0018\u00002\u00020\u0001J\b\u0010\u0002\u001a\u00020\u0003H&\u00a8\u0006\u0004"}, d2 = {"Lcom/r0adkll/anvil_ic/di/UserComponent$Factory;", "", "create", "Lcom/r0adkll/anvil_ic/di/UserComponent;", "app_debug"})
    public static abstract interface Factory {
        
        @org.jetbrains.annotations.NotNull()
        public abstract com.r0adkll.anvil_ic.di.UserComponent create();
    }
    
    @com.squareup.anvil.annotations.ContributesTo(scope = com.r0adkll.common.scopes.AppScope.class)
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\bg\u0018\u00002\u00020\u0001R\u0012\u0010\u0002\u001a\u00020\u0003X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005\u00a8\u0006\u0006"}, d2 = {"Lcom/r0adkll/anvil_ic/di/UserComponent$Parent;", "", "userComponentFactory", "Lcom/r0adkll/anvil_ic/di/UserComponent$Factory;", "getUserComponentFactory", "()Lcom/r0adkll/anvil_ic/di/UserComponent$Factory;", "app_debug"})
    public static abstract interface Parent {
        
        @org.jetbrains.annotations.NotNull()
        public abstract com.r0adkll.anvil_ic.di.UserComponent.Factory getUserComponentFactory();
    }
}