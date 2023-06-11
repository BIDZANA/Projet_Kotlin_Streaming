package fr.epf.bidzanafapo.network

import fr.epf.bidzanafapo.MovieModel
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface MovieApiService {

    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("api_key") apiKey: String
    ): MovieModel.SearchMoviesResponse

    @GET("search/movie")
    suspend fun getMovies(
        @Query("api_key") apiKey: String,
        @Query("query") query: String,
        @Query("page") page: Int
    ): MovieModel.SearchMoviesResponse

    @GET("movie/{movie_id}/recommendations")
    suspend fun getRecommandatedMovies(
        @Path("movie_id") Id: Int?,
        @Query("api_key") apiKey: String,
    ): MovieModel.SearchMoviesResponse

    @GET("movie/{movie_id}")
    suspend fun finById(
        @Path("movie_id") Id: Int?,
        @Query("api_key") apiKey: String
    ): MovieModel
}
