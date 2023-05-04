package br.senai.sp.jandira.doetempo.model

import com.google.gson.annotations.SerializedName

data class PostList(
    @SerializedName("all_posts") var allPosts: List<Post>
)
