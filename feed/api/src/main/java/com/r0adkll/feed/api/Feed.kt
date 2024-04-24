package com.r0adkll.feed.api

data class Feed(
  val posts: List<Posts>,
)

data class Posts(
  val id: String,
  val title: String,
  val text: String,
)