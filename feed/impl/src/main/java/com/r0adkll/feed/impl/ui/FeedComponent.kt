package com.r0adkll.feed.impl.ui

import android.content.Context
import com.r0adkll.common.scopes.FeatureScope
import com.r0adkll.common.scopes.SingleIn
import com.r0adkll.common.scopes.UserScope
import com.squareup.anvil.annotations.ContributesTo
import com.squareup.anvil.annotations.MergeSubcomponent
import dagger.BindsInstance
import dagger.Subcomponent

@SingleIn(FeatureScope::class)
@MergeSubcomponent(FeatureScope::class)
interface FeedComponent {

  fun inject(screen: FeedScreen)

  @ContributesTo(UserScope::class)
  interface Parent {
    val feedComponentFactory: Factory
  }

  @MergeSubcomponent.Factory
  interface Factory {
    fun create(
      @BindsInstance activityContext: Context,
    ): FeedComponent
  }
}