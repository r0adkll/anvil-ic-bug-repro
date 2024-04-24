package com.r0adkll.feed.api

interface FeedRepository {

  suspend fun fetch(): Feed
}