package fr.epf.bidzanafapo.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import fr.epf.bidzanafapo.MainActivity
import fr.epf.bidzanafapo.MovieRepository.Singleton.movieList
import fr.epf.bidzanafapo.R
import fr.epf.bidzanafapo.adapter.MovieItemDecoration

class HomeFragment(private val context: MainActivity) : Fragment() {

/*
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)


        // Récupérer le recyclerview du dessus
        val horizontalRecyclerView = view.findViewById<RecyclerView>(R.id.horizontal_recycle_view)
        if (horizontalRecyclerView != null) {
            horizontalRecyclerView.adapter = MovieAdapter(context, movieList, R.layout.item_horizontal_movie)
        }

        // Récupérer le second recyclerview
        val verticalRecyclerView = view.findViewById<RecyclerView>(R.id.vertical_recycle_view)
        if (verticalRecyclerView != null) {
            verticalRecyclerView.adapter = MovieAdapter(context, movieList, R.layout.item_vertical_movie)
            verticalRecyclerView.addItemDecoration(MovieItemDecoration())
        }

        return view
    }*/
}
