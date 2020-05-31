package com.yuzuriha.jetpack.tryout

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.stub
import com.nhaarman.mockitokotlin2.verify
import com.yuzuriha.jetpack.tryout.model.CommentsItem
import com.yuzuriha.jetpack.tryout.model.PostItem
import com.yuzuriha.jetpack.tryout.service.PostService
import com.yuzuriha.jetpack.tryout.utilities.Resource
import com.yuzuriha.jetpack.tryout.utilities.myModule
import com.yuzuriha.jetpack.tryout.viewmodel.AppViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.test.AutoCloseKoinTest
import org.koin.test.KoinTestRule
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
@ExperimentalCoroutinesApi
class AppViewModelTest : AutoCloseKoinTest() {

    @Mock
    lateinit var postStateObserver: Observer<Resource<ArrayList<PostItem>>>

    @Mock
    lateinit var commentStateObserver: Observer<List<CommentsItem>>

    @Mock
    lateinit var service: PostService

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val testCoroutineDispatcher = TestCoroutineDispatcher()

    @get:Rule
    val koinTestRule = KoinTestRule.create {
        modules(
            listOf(
                myModule
            )
        )
    }

    @Before
    fun setup() {
        Dispatchers.setMain(testCoroutineDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testCoroutineDispatcher.cleanupTestCoroutines()
    }


    @Test
    fun testReceivePostData() {
        val posts = arrayListOf(PostItem("test", 1, "title", 1))
        service.stub {
            onBlocking { getPosts() }.doReturn(posts)
        }
        val viewModel = AppViewModel(service)

        viewModel.postState.observeForever(postStateObserver)

        verify(postStateObserver).onChanged(Resource.success(data = posts))
    }

    @Test
    fun testReceiveCommentData() {
        val comments = arrayListOf(CommentsItem("test", "some email", 1, "some name", 1))
        service.stub {
            onBlocking { getComments(1) }.doReturn(comments)
        }
        val viewModel = AppViewModel(service)

        viewModel.getComments(1)

        viewModel.comments.observeForever(commentStateObserver)

        verify(commentStateObserver).onChanged(comments)
    }

    @Test
    fun filterOutValue() {
        val comments = arrayListOf(
            CommentsItem("test1", "some email 1", 1, "some name 1", 1),
            CommentsItem("test2", "some email 2", 2, "some name 2", 2)
        )
        service.stub {
            onBlocking { getComments(1) }.doReturn(comments)
        }
        val viewModel = AppViewModel(service)

        viewModel.getComments(1)

        viewModel.comments.observeForever(commentStateObserver)

        verify(commentStateObserver).onChanged(comments)

        viewModel.filterComment("test2")

        assert(viewModel.comments.value?.size == 1)
    }

    @Test
    fun filterOutValueBackToEmpty() {
        val comments = arrayListOf(
            CommentsItem("test1", "some email 1", 1, "some name 1", 1),
            CommentsItem("test2", "some email 2", 2, "some name 2", 2)
        )
        service.stub {
            onBlocking { getComments(1) }.doReturn(comments)
        }
        val viewModel = AppViewModel(service)

        viewModel.getComments(1)

        viewModel.comments.observeForever(commentStateObserver)

        verify(commentStateObserver).onChanged(comments)

        viewModel.filterComment("test2")

        assert(viewModel.comments.value?.size == 1)

        viewModel.filterComment("")

        assert(viewModel.comments.value?.size == 2)
    }
}