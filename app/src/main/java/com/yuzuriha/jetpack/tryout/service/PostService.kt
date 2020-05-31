package com.yuzuriha.jetpack.tryout.service

import com.yuzuriha.jetpack.tryout.model.*
import com.yuzuriha.jetpack.tryout.utilities.Service

class PostService {
    suspend fun getPosts(): ArrayList<PostItem> = Service.instance().getPosts()

    suspend fun getComments(postId: Int): ArrayList<CommentsItem> =
        Service.instance().getComments(postId)
}