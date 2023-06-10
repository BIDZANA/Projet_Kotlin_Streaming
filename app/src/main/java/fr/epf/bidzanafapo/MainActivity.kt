package fr.epf.bidzanafapo

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import fr.epf.bidzanafapo.adapter.MovieAdapterHorizontal
import fr.epf.bidzanafapo.adapter.MovieAdapterVertical
import fr.epf.bidzanafapo.network.MovieApiService
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private val apiKey = "9898580a411c9cc7d443a89ae37ca0ee"
    lateinit var recyclerViewH: RecyclerView
    lateinit var recyclerViewV: RecyclerView
    private var listMovies: ArrayList<Movie> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerViewH = findViewById(R.id.horizontal_recycle_view)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        recyclerViewH.layoutManager =
            LinearLayoutManager(this, GridLayoutManager.HORIZONTAL, false)
        recyclerViewH.layoutManager = GridLayoutManager(this, 1, GridLayoutManager.HORIZONTAL, false)
        recyclerViewH.adapter = MovieAdapterHorizontal(this@MainActivity, listMovies)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        recyclerViewV.layoutManager =
            LinearLayoutManager(this, GridLayoutManager.VERTICAL, false)
        recyclerViewV.layoutManager = GridLayoutManager(this, GridLayoutManager.VERTICAL)
        recyclerViewV.adapter = MovieAdapterVertical(this, listMovies)

        val searchView = findViewById<SearchView>(R.id.searchView)
        val searchButton = findViewById<Button>(R.id.search_button)
        searchButton.setOnClickListener {
            val query = searchView.query.toString()
            performSearch(query)
            recyclerViewV.adapter?.notifyDataSetChanged()
        }

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

    private fun performSearch(query: String) = runBlocking {
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
            val moviesResult = service.getMovies(apiKey, query, 1)

            Log.d("Movies : ", moviesResult.toString())

            if (moviesResult != null) {
                moviesResult.results.map {
                    listMovies.add(
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
                Log.d("liste film: ", listMovies.toString())
            }
        }
        val result = myGlobalVar.await()
        println(result)
    }

    fun getPopularMovies() = runBlocking {
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
            val moviesResult = service.getPopularMovies(apiKey)

            Log.d("Movies : ", moviesResult.toString())

            if (moviesResult != null) {
                moviesResult.results.map {
                    listMovies.add(
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
                Log.d("liste film: ", listMovies.toString())
            }
        }
        val result = myGlobalVar.await()
        println(result)
    }

}
/*



   override fun onCreate(savedInstanceState: Bundle?) {
       super.onCreate(savedInstanceState)
       setContentView(R.layout.activity_main)

       val navigationBar = findViewById<BottomNavigationView>(R.id.navigation_bar_view)
       navigationBar.setOnNavigationItemSelectedListener { menuItem ->
           when (menuItem.itemId) {
               R.id.home_page -> {
                   startActivity(Intent(this, MainActivity::class.java))
                   true
               }
               R.id.favorite_page -> {
                   startActivity(Intent(this, FavoriteActivity::class.java))
                   true
               }
               R.id.scanner_page -> {
                   startActivity(Intent(this, QRCodeActivity::class.java))
                   true
               }
               else -> false
           }
       }

       val repo = MovieRepository()
       repo.updateData {
           loadRecyclerViews()
       }
   }

   private fun loadRecyclerViews() {
       val horizontalRecyclerView = findViewById<RecyclerView>(R.id.horizontal_recycle_view)
       horizontalRecyclerView.layoutManager =
           LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
       horizontalRecyclerView.adapter = MovieAdapter(
           this,
           movieList,
           R.layout.item_horizontal_movie
       )

       val verticalRecyclerView = findViewById<RecyclerView>(R.id.vertical_recycle_view)
       verticalRecyclerView.layoutManager =
           LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
       verticalRecyclerView.adapter = MovieAdapter(
           this,
           movieList,
           R.layout.item_vertical_movie
       )
       verticalRecyclerView.addItemDecoration(MovieItemDecoration())
   }
}*/

