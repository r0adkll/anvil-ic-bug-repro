package com.r0adkll.feed.impl.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import com.r0adkll.common.ComponentHolder
import com.r0adkll.common.InjectWith
import com.r0adkll.feed.api.Feed
import com.r0adkll.feed.api.FeedRepository
import com.r0adkll.feed.impl.ui.theme.AnvilICIssueSampleTheme
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@InjectWith
class PopularFeedScreen : ComponentActivity() {

  @Inject
  lateinit var feedRepository: FeedRepository

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    injectFeature()
    setContent {
      val feed by remember {
        flow {
          val feed = feedRepository.fetch()
          emit(feed)
        }
      }.collectAsState(null)

      AnvilICIssueSampleTheme {
        // A surface container using the 'background' color from the theme
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
          if (feed == null) {
            LoadingState()
          } else {
            if (feed!!.posts.isEmpty()) {
              EmptyState()
            } else {
              FeedList(feed!!)
            }
          }
        }
      }
    }
  }

  private fun injectFeature() {
//    val feedComponent = ComponentHolder.component<FeedComponent.Parent>()
//      .feedComponentFactory
//      .create(this)
//    feedComponent.inject(this)
  }

  @Composable
  fun LoadingState(
    modifier: Modifier = Modifier,
  ) {
    Box(modifier.fillMaxSize()) {
      CircularProgressIndicator()
    }
  }

  @Composable
  fun EmptyState(
    modifier: Modifier = Modifier,
  ) {
    Box(modifier.fillMaxSize()) {
      Text("Empty State")
    }
  }

  @Composable
  fun FeedList(
    feed: Feed,
    modifier: Modifier = Modifier,
  ) {
    LazyColumn(modifier.testTag("An IC test!").testTag("Another")) {
      items(feed.posts) {
        Text(it.text)
      }
    }
  }
}