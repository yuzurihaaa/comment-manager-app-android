package com.yuzuriha.jetpack.tryout.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yuzuriha.jetpack.tryout.model.CommentsItem
import com.yuzuriha.jetpack.tryout.model.PostItem
import com.yuzuriha.jetpack.tryout.service.PostService
import com.yuzuriha.jetpack.tryout.utilities.Resource
import com.yuzuriha.jetpack.tryout.utilities.containsIgnoreCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AppViewModel(private val service: PostService) : ViewModel() {
    var postState = MutableLiveData<Resource<ArrayList<PostItem>>>()
    var comments = MutableLiveData<List<CommentsItem>>()

    private lateinit var allComments: ArrayList<CommentsItem>

    init {
        viewModelScope.launch(Dispatchers.Main) {
            postState.postValue(Resource.loading(data = null))
            try {
                val data = service.getPosts()
                postState.postValue(Resource.success(data = data))
            } catch (exception: Exception) {
                postState.postValue(
                    Resource.error(
                        data = null,
                        message = exception.message ?: "Error Occurred!"
                    )
                )
            }
        }
    }

    fun getComments(postId: Int) {
        comments.postValue(null)
        viewModelScope.launch(Dispatchers.Main) {
            Log.i("TAG", "comment oncreate")
            try {
                allComments = service.getComments(postId)
                comments.postValue(allComments)
            } catch (exception: Exception) {
                exception.printStackTrace()
            }
        }
    }

    fun filterComment(keyword: String?) {
        if (keyword.isNullOrEmpty()) {
            comments.postValue(allComments)
            return
        }

        val filtered = allComments.filter {
            val validBody = it.body.containsIgnoreCase(keyword)
            val validName = it.name.containsIgnoreCase(keyword)
            val validEmail = it.email.containsIgnoreCase(keyword)

            validBody || validName || validEmail
        }

        comments.postValue(filtered)
    }
}