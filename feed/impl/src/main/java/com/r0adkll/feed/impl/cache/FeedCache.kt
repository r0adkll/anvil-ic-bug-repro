package com.r0adkll.feed.impl.cache

import com.r0adkll.feed.api.Feed

interface FeedCache {

  suspend fun fetch(): Feed?
  suspend fun store(feed: Feed)
}