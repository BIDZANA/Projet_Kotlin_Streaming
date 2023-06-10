package fr.epf.bidzanafapo.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import fr.epf.bidzanafapo.MainActivity
import fr.epf.bidzanafapo.MovieRepository.Singleton.movieList
import fr.epf.bidzanafapo.R
import fr.epf.bidzanafapo.adapter.MovieItemDecoration

class FavoriteFragment : Fragment() {

    /*override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_favorite, container, false)

        val context = requireContext() as MainActivity

        // Récupération de la RecyclerView
        val collectionRecyclerView = view.findViewById<RecyclerView>(R.id.favoris_recycler_list)
        collectionRecyclerView.adapter =
            MovieAdapter(context, movieList.filter { it.liked }, R.layout.item_vertical_movie)
        collectionRecyclerView.layoutManager = LinearLayoutManager(context)
        collectionRecyclerView.addItemDecoration(MovieItemDecoration())

        return view
    }*/
}