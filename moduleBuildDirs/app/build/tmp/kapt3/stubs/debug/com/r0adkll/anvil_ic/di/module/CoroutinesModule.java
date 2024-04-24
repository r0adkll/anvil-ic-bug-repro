package com.r0adkll.anvil_ic.di.module;

@dagger.Module()
@com.squareup.anvil.annotations.ContributesTo(scope = com.r0adkll.common.scopes.AppScope.class)
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u00c7\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H\u0007\u00a8\u0006\u0005"}, d2 = {"Lcom/r0adkll/anvil_ic/di/module/CoroutinesModule;", "", "()V", "provideDispatcherProvider", "Lcom/r0adkll/common/coroutines/DispatcherProvider;", "app_debug"})
public final class CoroutinesModule {
    @org.jetbrains.annotations.NotNull()
    public static final com.r0adkll.anvil_ic.di.module.CoroutinesModule INSTANCE = null;
    
    private CoroutinesModule() {
        super();
    }
    
    @dagger.Provides()
    @com.r0adkll.common.scopes.SingleIn(clazz = com.r0adkll.common.scopes.AppScope.class)
    @org.jetbrains.annotations.NotNull()
    public final com.r0adkll.common.coroutines.DispatcherProvider provideDispatcherProvider() {
        return null;
    }
}