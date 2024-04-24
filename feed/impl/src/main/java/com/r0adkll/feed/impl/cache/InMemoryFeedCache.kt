package com.r0adkll.feed.impl.cache

import com.r0adkll.common.scopes.SingleIn
import com.r0adkll.common.scopes.UserScope
import com.r0adkll.feed.api.Feed
import com.squareup.anvil.annotations.ContributesBinding
import javax.inject.Inject

@SingleIn(UserScope::class)
@ContributesBinding(UserScope::class)
class InMemoryFeedCache @Inject constructor() : FeedCache {

  private var feed: Feed? = null

  override suspend fun fetch(): Feed? {
    return feed
  }

  override suspend fun store(feed: Feed) {
    this.feed = feed
  }
}