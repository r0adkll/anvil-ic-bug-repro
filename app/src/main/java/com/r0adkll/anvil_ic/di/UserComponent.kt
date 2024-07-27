package com.r0adkll.anvil_ic.di

import android.app.Application
import com.r0adkll.common.scopes.AppScope
import com.r0adkll.common.scopes.SingleIn
import com.r0adkll.common.scopes.UserScope
import com.squareup.anvil.annotations.ContributesTo
import com.squareup.anvil.annotations.MergeSubcomponent
import dagger.Subcomponent

@SingleIn(UserScope::class)
@MergeSubcomponent(UserScope::class)
interface UserComponent {


  val application: Application


  @ContributesTo(AppScope::class)
  interface Parent {
    val userComponentFactory: Factory
  }

  @MergeSubcomponent.Factory
  interface Factory {
    fun create(): UserComponent
  }
}