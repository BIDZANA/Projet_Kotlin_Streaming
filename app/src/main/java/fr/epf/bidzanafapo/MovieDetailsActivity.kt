package fr.epf.bidzanafapo

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.squareup.picasso.Picasso
import fr.epf.bidzanafapo.adapter.MovieAdapterVertical
import fr.epf.bidzanafapo.network.MovieApiService
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.io.FileWriter

class MovieDetailsActivity : AppCompatActivity() {

    private val apiKey = "9898580a411c9cc7d443a89ae37ca0ee"
    private lateinit var recyclerView: RecyclerView
    private var RecommandationList: ArrayList<Movie> = arrayListOf()

    @SuppressLint("MissingInflatedId", "SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_details)

        val extras = intent.extras
        val movieExtra = extras?.get("movie") as? Movie
        Log.d("film : ", movieExtra.toString())
        recyclerView = findViewById(R.id.recommand_movies_recyclerview)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        recyclerView.layoutManager =
            LinearLayoutManager(this, GridLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = GridLayoutManager(this, GridLayoutManager.VERTICAL)
        recyclerView.adapter = MovieAdapterVertical(this@MovieDetailsActivity, RecommandationList)

        val favoriteButton = findViewById<Button>(R.id.FavButton)
        val title = findViewById<TextView>(R.id.moviedetails_title_textview)
        val resume = findViewById<TextView>(R.id.moviedetails_resume_textview)
        val date = findViewById<TextView>(R.id.moviedetails_date_textview)
        val language = findViewById<TextView>(R.id.moviedetails_language_textview)
        val note = findViewById<TextView>(R.id.moviedetails_note_textview)
        val affiche = findViewById<ImageView>(R.id.detailsmovie_view_imageview)
        val posterPath = movieExtra?.poster_path
        val baseImageUrl = "https://image.tmdb.org/t/p/"
        val posterUrl = baseImageUrl + "w600_and_h900_bestv2" + posterPath

        Picasso.get()
            .load(posterUrl)
            .into(affiche)
        title.text = movieExtra?.title
        resume.text = movieExtra?.overview
        date.text = movieExtra?.release_date
        language.text = movieExtra?.original_language
        note.text = movieExtra?.vote_average.toString() + "/10"

        favoriteButton.setOnClickListener {
            addFavorite(movieExtra!!.id.toString())
            favoriteButton.isEnabled = false
        }
        Recommandations()

        val navigationBar = findViewById<BottomNavigationView>(R.id.navigation_bar_view)
        navigationBar.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.favorite_page -> {
                    this.startActivity(Intent(this, FavoriteActivity::class.java))
                    return@setOnNavigationItemSelectedListener true
                }

                R.id.home_page -> {
                    this.startActivity(Intent(this, MainActivity::class.java))
                    return@setOnNavigationItemSelectedListener true
                }

                R.id.scanner_page -> {
                    this.startActivity(Intent(this, QRCodeActivity::class.java))
                    return@setOnNavigationItemSelectedListener true
                }
            }
            true
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun Recommandations() = runBlocking {
        val extras = intent.extras
        val movieExtra = extras?.get("movie") as? Movie
        val myGlobalVar = GlobalScope.async {

            val httpLoggingInterceptor = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }

            val client = OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .build()

            val retrofit = Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/3/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()

            val service = retrofit.create(MovieApiService::class.java)
            val moviesResult = service.getRecommandatedMovies(movieExtra?.id, apiKey)

            Log.d("Movies : ", moviesResult.toString())

            if (moviesResult != null) {
                moviesResult.results.map {
                    RecommandationList.add(
                        Movie(
                            it.adult,
                            it.overview,
                            it.release_date,
                            it.id,
                            it.original_language,
                            it.title,
                            it.popularity,
                            it.vote_count,
                            it.vote_average,
                            it.poster_path
                        )
                    )
                }
                Log.d("liste reco: ", RecommandationList.toString())
            }
        }
        val result = myGlobalVar.await()
        println(result)
    }


    private fun addFavorite(filmId: String) {
        val dataDir = applicationContext.filesDir
        val directoryName = "myDataDirectory"
        val directory = File(dataDir, directoryName)
        if (!directory.exists()) {
            directory.mkdirs()
        }

        val file = File(directory, "listefavori.txt")

        try {
            // Vérifier si le fichier existe
            if (!file.exists()) {
                file.createNewFile()
                if (file.exists()) {
                    println("Fichier existant")
                    val chemin = file.absolutePath.toString()
                    println("Chemin du fichier : $chemin")
                } else {
                    println("Fichier non existant")
                }
            }
            val contenu = file.readText()
            if (contenu.contains(filmId)) {
                println("Le film est déjà présent dans les favoris.")
                Toast.makeText(
                    applicationContext,
                    "Le film est déjà présent dans les favoris.",
                    Toast.LENGTH_SHORT
                ).show()
                return
            }
            // Ajouter le favori dans le fichier
            val fileWriter = FileWriter(file, true)
            fileWriter.write("$filmId\n")
            fileWriter.close()
            println("Contenu du fichier :")
            println(contenu)
            println("Le fichier existe.")
            val chemin = file.absolutePath.toString()
            println("Chemin du fichier : $chemin")
            println("Favori ajouté avec succès.")
            Toast.makeText(applicationContext, "Ajouté!", Toast.LENGTH_SHORT)
                .show()
        } catch (e: Exception) {
            println("Erreur lors de l'ajout aux favoris : ${e.message}")
        }
    }

}