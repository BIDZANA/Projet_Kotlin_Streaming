package fr.epf.bidzanafapo.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.squareup.picasso.Picasso
import fr.epf.bidzanafapo.Movie
import fr.epf.bidzanafapo.MovieDetailsActivity
import fr.epf.bidzanafapo.R

class MovieViewHolder(view: View) : ViewHolder(view)

class MovieAdapterVertical(val activity: Context, val movieList: List<Movie>) :
    RecyclerView.Adapter<MovieViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        Log.d("PopularMovie inside Adapter: ", movieList.toString())
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.item_vertical_movie, parent, false)
        return MovieViewHolder(view)
    }

    override fun getItemCount(): Int = movieList.size

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movieList[position]
        val view = holder.itemView

        val affiche = view.findViewById<ImageView>(R.id.client_view_imageview)
        val posterPath = movie.poster_path
        val baseImageUrl = "https://image.tmdb.org/t/p/"
        val posterUrl = baseImageUrl + "w600_and_h900_bestv2" + posterPath

        val titre = view.findViewById<TextView>(R.id.textViewTitle)
        val release = view.findViewById<TextView>(R.id.textViewReleaseYear)
        val note = view.findViewById<TextView>(R.id.textViewVoteAverage)

        titre.text = movie.title
        release.text = movie.release_date
        note.text = movie.vote_average.toString() + "/10"
        Picasso.get()
            .load(posterUrl)
            .into(affiche)

        view.setOnClickListener {
            val intent = Intent(activity, MovieDetailsActivity::class.java)
            intent.putExtra("movie", movie)
            Log.d("film movie_adapter: ", movie.toString())
            activity.startActivity(intent)
        }
    }

}

class MovieAdapterHorizontal(val activity: Context, val movieList: List<Movie>) :
    RecyclerView.Adapter<MovieViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        Log.d("PopularMovie inside Adapter: ", movieList.toString())
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.item_horizontal_movie, parent, false)
        return MovieViewHolder(view)
    }

    override fun getItemCount(): Int = movieList.size

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movieList[position]
        val view = holder.itemView

        val affiche = view.findViewById<ImageView>(R.id.image_item)
        val posterPath = movie.poster_path
        val baseImageUrl = "https://image.tmdb.org/t/p/"
        val posterUrl = baseImageUrl + "w600_and_h900_bestv2" + posterPath

        Picasso.get()
            .load(posterUrl)
            .into(affiche)

        view.setOnClickListener {
            val intent = Intent(activity, MovieDetailsActivity::class.java)
            intent.putExtra("movie", movie)
            Log.d("film movieadapter: ", movie.toString())
            activity.startActivity(intent)
        }
    }

}