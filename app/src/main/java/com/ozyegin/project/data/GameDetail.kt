package com.ozyegin.project.data

class GameDetail {
    var id: String = ""
    var name: String = ""
    var rating: Int = 0
    var genres: String = ""
    var platforms: String = ""
    var storyline: String = ""
    var summary: String = ""
    lateinit var firstReleaseDate: String
    var imageId: String = ""
    var artworksIds: List<String> = emptyList()
    constructor(
        id: String,
        name: String,
        rating: Int,
        genres: String,
        platforms: String,
        storyline: String,
        summary: String,
        first_release_date: String,
        imageId: String,
        artworksIds: List<String>
    ) {
        this.id = id
        this.name = name
        this.rating = rating
        this.genres = genres
        this.platforms = platforms
        this.storyline = storyline
        this.summary = summary
        this.firstReleaseDate = first_release_date
        this.imageId = imageId
        this.artworksIds = artworksIds
    }
}