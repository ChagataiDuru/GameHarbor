package com.ozyegin.project.data

import com.google.protobuf.Timestamp
import proto.Game
import proto.Genre
import proto.Platform
import proto.Screenshot
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import com.ozyegin.project.data.GameDetail as GameDetailEntity
import com.ozyegin.project.data.GameList as GameListEntity

/**
 * Class to transform IGDB entities to useful entities for the app
 */
object GameDetailClass {

    /**
     * Transforms a IGDB game object to a useful GameListEntity object
     */
    fun convertFromGameToGameListEntity(game: Game): GameListEntity {
        println("Converting game to GameListEntity")
        return GameListEntity(game.id.toString(), game.name, game.totalRating.toInt(),
            buildGenresString(game.genresList), buildPlatformsString(game.platformsList),
            game.cover.imageId)
    }

    /**
     * Transforms a IGDB game object to a useful GameDetailEntity object
     */
    fun convertFromGameToGameDetailEntity(game: Game): GameDetailEntity {
        return GameDetailEntity(game.id.toString(), game.name, game.totalRating.toInt(),
            buildGenresString(game.genresList), buildPlatformsString(game.platformsList),
            game.storyline, game.summary, convertDateToString(game.firstReleaseDate),
            game.cover.imageId, buildArtworksListString(game.screenshotsList)
        )
    }

    /**
     * Returns a string with all the game genres separated with commas
     */
    private fun buildGenresString(genresList: List<Genre>): String {
        var genres = ""
        genresList.forEachIndexed { index, genre ->
            genres = if (index == genresList.lastIndex) {
                genres.plus(genre.name)
            } else {
                genres.plus(genre.name).plus(", ")
            }
        }
        return genres
    }

    /**
     * Returns a string with all the game platforms separated with commas
     */
    private fun buildPlatformsString(platformsList: List<Platform>): String {
        var platforms = ""
        platformsList.forEachIndexed { index, platform ->
            platforms = if (index == platformsList.lastIndex) {
                platforms.plus(platform.name)
            } else {
                platforms.plus(platform.name).plus(", ")
            }
        }
        return platforms
    }

    /**
     * Returns a list with all the game imagesId
     */
    private fun buildArtworksListString(artworksList: List<Screenshot>): List<String> {
        return artworksList.map { it.imageId }
    }

    /**
     * Converts from Unix Timestamp to a String date with format "dd-MMM-yy"
     */
    private fun convertDateToString(date: Timestamp): String {
        val formattedDate = Instant
            .ofEpochSecond( date.seconds, date.nanos.toLong())
            .atZone( ZoneId.of( "America/Montreal" ) )
            .toLocalDate()
        return formattedDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))
    }
}