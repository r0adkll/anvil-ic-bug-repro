package com.r0adkll.feed.impl.datasource

import com.r0adkll.common.coroutines.DispatcherProvider
import com.r0adkll.common.scopes.UserScope
import com.r0adkll.feed.api.Feed
import com.r0adkll.feed.api.Posts
import com.squareup.anvil.annotations.ContributesBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import javax.inject.Inject

@ContributesBinding(UserScope::class)
class NetworkFeedDataSource @Inject constructor(
  private val dispatcherProvider: DispatcherProvider,
) : FeedDataSource {

  override suspend fun fetch(): Feed {
    return withContext(dispatcherProvider.io) {
      delay(1000L) // do stuff
      Feed(
        listOf(
          Posts("1", "post 1", "post 1"),
          Posts("2", "post 2", "post 2"),
          Posts("3", "post 3", "post 3"),
        )
      )
    }
  }
}