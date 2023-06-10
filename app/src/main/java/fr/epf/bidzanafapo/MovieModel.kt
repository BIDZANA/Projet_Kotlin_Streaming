package fr.epf.bidzanafapo

class MovieModel() {
    var id: String = ""
    var title: String = ""
    var releaseDate: String = ""
    var overview: String = ""
    var popularity: Double = 0.0
    var voteAverage: Double = 0.0
    var voteCount: Int = 0
    var posterPath: String = ""
    var liked: Boolean = false

    constructor(
        id: String,
        title: String,
        releaseDate: String,
        overview: String,
        popularity: Double,
        voteAverage: Double,
        voteCount: Int,
        posterPath: String,
        liked: Boolean
    ) : this() {
        this.id = id
        this.title = title
        this.releaseDate = releaseDate
        this.overview = overview
        this.popularity = popularity
        this.voteAverage = voteAverage
        this.voteCount = voteCount
        this.posterPath = posterPath
        this.liked = liked
    }
}