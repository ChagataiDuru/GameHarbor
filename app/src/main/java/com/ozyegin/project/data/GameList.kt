package com.ozyegin.project.data

class GameList {

    /**
     * id
     */
    var id: String = ""

    /**
     * name
     */
    var name: String = ""

    /**
     * rating
     */
    var rating: Int = 0

    /**
     * genres
     */
    var genres: String = ""

    /**
     * platforms
     */
    var platforms: String = ""

    /**
     * imageId
     */
    var imageId: String = ""

    constructor()
    constructor(
        id: String,
        name: String,
        rating: Int,
        genres: String,
        platforms: String,
        coverUrl: String
    ) {
        this.id = id
        this.name = name
        this.rating = rating
        this.genres = genres
        this.platforms = platforms
        this.imageId = coverUrl
    }
}