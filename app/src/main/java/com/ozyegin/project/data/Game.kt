package com.ozyegin.project.data

data class Game(
    val id: Long,
    val title: String,
    val description: String,
    val score: Int,
    val userReviews: String,
    val screenshots: List<String>,
    val links: String
)
