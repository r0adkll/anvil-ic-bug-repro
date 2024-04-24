package com.r0adkll.anvil_ic.di;

@com.r0adkll.common.scopes.SingleIn(clazz = com.r0adkll.common.scopes.AppScope.class)
@com.squareup.anvil.annotations.MergeComponent(scope = com.r0adkll.common.scopes.AppScope.class)
@dagger.Component(modules = {com.r0adkll.anvil_ic.di.module.CoroutinesModule.class})
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\bg\u0018\u00002\u00020\u0001:\u0001\u0002\u00a8\u0006\u0003"}, d2 = {"Lcom/r0adkll/anvil_ic/di/AppComponent;", "", "Factory", "app_debug"})
public abstract interface AppComponent extends com.r0adkll.anvil_ic.di.UserComponent.Parent {
    
    @dagger.Component.Factory()
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bg\u0018\u00002\u00020\u0001J\u0012\u0010\u0002\u001a\u00020\u00032\b\b\u0001\u0010\u0004\u001a\u00020\u0005H&\u00a8\u0006\u0006"}, d2 = {"Lcom/r0adkll/anvil_ic/di/AppComponent$Factory;", "", "create", "Lcom/r0adkll/anvil_ic/di/AppComponent;", "application", "Landroid/app/Application;", "app_debug"})
    public static abstract interface Factory {
        
        @org.jetbrains.annotations.NotNull()
        public abstract com.r0adkll.anvil_ic.di.AppComponent create(@dagger.BindsInstance()
        @org.jetbrains.annotations.NotNull()
        android.app.Application application);
    }
}