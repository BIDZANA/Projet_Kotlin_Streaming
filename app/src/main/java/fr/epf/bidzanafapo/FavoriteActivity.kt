package fr.epf.bidzanafapo

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import fr.epf.bidzanafapo.adapter.MovieAdapterVertical
import fr.epf.bidzanafapo.adapter.MovieItemDecoration
import fr.epf.bidzanafapo.network.MovieApiService
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File

class FavoriteActivity : AppCompatActivity() {

    private val apiKey = "9898580a411c9cc7d443a89ae37ca0ee"
    lateinit var recyclerView: RecyclerView
    private var favoris = mutableListOf<String>()
    private var listefav: ArrayList<MovieModel> = arrayListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        recyclerView = findViewById(R.id.favoris_recycler_list)
        recyclerView.layoutManager =
            LinearLayoutManager(this, GridLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = GridLayoutManager(this, GridLayoutManager.VERTICAL)
        recyclerView.adapter = MovieAdapterVertical(this@FavoriteActivity, listefav)
        recyclerView.addItemDecoration(MovieItemDecoration())
        recupererFavoris()
        Log.d("MovieModel search main: ", listefav.toString())
        recyclerView.adapter?.notifyDataSetChanged()

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

    fun recupererFavoris(): List<String> {
        val file = File("/data/user/0/fr.epf.bidzanafapo/files/myDataDirectory/listefavori.txt")
        if (file.exists() && file.canRead()) {
            file.bufferedReader().useLines { lines ->
                lines.forEach { line ->
                    val id = line.trim()
                    favoris.add(id)
                }
            }
        } else {
            println("Probleme")

        }
        println("Liste favoris $favoris")
        Recupererinfos(favoris)
        return favoris
    }

    fun Recupererinfos(favoris: List<String>) = runBlocking {
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
            for (id in favoris) {
                val movieResult = service.finById(Integer.parseInt(id), apiKey) as MovieModel
                listefav.add(movieResult)
            }
            Log.d("MovieModel search fonction: ", listefav.toString())
        }
        val result = myGlobalVar.await()
        println(result)
    }
}