package com.r0adkll.feed.impl.ui

import com.r0adkll.common.scopes.FeatureScope
import com.squareup.anvil.annotations.ContributesBinding
import javax.inject.Inject

interface FeedEventHandler {

  fun handleEvent()
}

@ContributesBinding(FeatureScope::class)
class RealFeedEventHandler @Inject constructor(): FeedEventHandler {

  override fun handleEvent() {
    TODO("Not yet implemented")
  }
}