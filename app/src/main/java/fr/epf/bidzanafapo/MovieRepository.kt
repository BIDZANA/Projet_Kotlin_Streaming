package fr.epf.bidzanafapo


import android.content.ContentValues.TAG
import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import fr.epf.bidzanafapo.MovieRepository.Singleton.databaseRef
import fr.epf.bidzanafapo.MovieRepository.Singleton.movieList


class MovieRepository {

    // Utilisation d'une base de données Firebase
    object Singleton {
        // Se connecter à la référence "movies"
        val databaseRef = FirebaseDatabase.getInstance().getReference("movies")

        // Créer une liste qui va contenir nos films
        val movieList = arrayListOf<MovieModel>()
    }

    fun updateData(callback: () -> Unit) {
        // On récupère les données en BD et on remplit la liste de films
        databaseRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                movieList.clear()
                // recolter la liste
                for (ds in snapshot.children) {
                    // construire un objet movie
                    val movie = ds.getValue(MovieModel::class.java)
                    // l'ajouter à la liste en vériant qu'il n'est pas null
                    if (movie != null) {
                        movieList.add(movie)
                    }
                }
                callback()
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d(TAG, "Value is: ${error.toException()}")
            }

        })
    }

    // Mettre à jour un objet movie en bd
    fun updateMovie(movie: MovieModel) = databaseRef.child(movie.id).setValue(movie)


}