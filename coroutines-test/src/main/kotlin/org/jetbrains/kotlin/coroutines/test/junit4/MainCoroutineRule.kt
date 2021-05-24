package org.jetbrains.kotlin.coroutines.test.junit4

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.rules.ExternalResource
import kotlin.coroutines.ContinuationInterceptor

@ExperimentalCoroutinesApi
class MainCoroutineRule : ExternalResource(), TestCoroutineScope by TestCoroutineScope() {

    override fun before() {
        Dispatchers.setMain(this.coroutineContext[ContinuationInterceptor] as CoroutineDispatcher)
    }

    override fun after() {
        Dispatchers.resetMain()
        cleanupTestCoroutines()
    }
}