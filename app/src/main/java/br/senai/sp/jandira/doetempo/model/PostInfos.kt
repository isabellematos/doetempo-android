package br.senai.sp.jandira.doetempo.model

import androidx.compose.ui.graphics.painter.Painter

data class PostInfo(

    val name: String,
    val profilePic: Painter,
    val date: String,
    val postText: String,
    val likeCount: Int,
    val commentCount: Int,
    val comment: String,
    val postPhoto: Painter?,
    val postVideo: String,
    val verified: Boolean
)
