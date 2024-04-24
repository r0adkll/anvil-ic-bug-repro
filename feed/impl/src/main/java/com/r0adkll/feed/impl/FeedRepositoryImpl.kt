package com.r0adkll.feed.impl

import com.r0adkll.common.scopes.UserScope
import com.r0adkll.feed.api.Feed
import com.r0adkll.feed.api.FeedRepository
import com.r0adkll.feed.impl.cache.FeedCache
import com.r0adkll.feed.impl.datasource.FeedDataSource
import com.squareup.anvil.annotations.ContributesBinding
import javax.inject.Inject

@ContributesBinding(UserScope::class)
class FeedRepositoryImpl @Inject constructor(
  private val cache: FeedCache,
  private val source: FeedDataSource,
) : FeedRepository {

  override suspend fun fetch(): Feed {
    var feed = cache.fetch()
    if (feed == null) {
      feed = source.fetch()
      cache.store(feed)
    }

    return feed
  }
}