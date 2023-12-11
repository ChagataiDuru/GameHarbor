package com.ozyegin.project.data

class GameData {
    data class Game(
        val id: Long,
        val title: String,
        val description: String,
        val score: Double,
        val userReviews: String,
        val screenshots: List<String>,
        val links: String
    )
}