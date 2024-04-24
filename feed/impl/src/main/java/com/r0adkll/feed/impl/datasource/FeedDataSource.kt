package com.r0adkll.feed.impl.datasource

import com.r0adkll.feed.api.Feed

interface FeedDataSource {

  suspend fun fetch(): Feed
}