package com.formationandroid.webservices

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.formationandroid.webservices.ws.RetourWSPlanetes
import com.formationandroid.webservices.ws.RetrofitSingleton
import com.formationandroid.webservices.ws.WSInterface
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.random.Random

class MainActivity : AppCompatActivity()
{

	// Retrofit :
	private val serviceRetrofit = RetrofitSingleton.retrofit.create(WSInterface::class.java)


	override fun onCreate(savedInstanceState: Bundle?)
	{
		// init :
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)
	}

	/**
	 * Listener clic bouton récupérer planète.
	 * @param view Vue cliquée (ici bouton récupérer planète)
	 */
	fun clicBoutonRecupererPlanete(view: View)
	{
		// affichage du spinner d'attente :
		progressbar_attente.visibility = View.VISIBLE

		// appel au webservice :
		val call = serviceRetrofit.getPlanets()
		call.enqueue(object : Callback<RetourWSPlanetes>
		{
			override fun onResponse(call: Call<RetourWSPlanetes>, response: Response<RetourWSPlanetes>)
			{
				if (response.isSuccessful)
				{
					val retourWSPlanetes = response.body()
					retourWSPlanetes?.results?.let { liste ->

						// récupération d'une planète au hasard :
						val planete = liste[Random.nextInt(0, liste.size)]

						// création du texte :
						val stringBuilder = StringBuilder()
						stringBuilder.append(getString(R.string.main_planete_nom, planete.name))
						stringBuilder.append(getString(R.string.main_planete_rotation, planete.rotation_period))
						stringBuilder.append(getString(R.string.main_planete_periode, planete.orbital_period))
						stringBuilder.append(getString(R.string.main_planete_diametre, planete.diameter))
						stringBuilder.append(getString(R.string.main_planete_climat, planete.climate))
						stringBuilder.append(getString(R.string.main_planete_gravite, planete.gravity))
						stringBuilder.append(getString(R.string.main_planete_terrain, planete.terrain))
						stringBuilder.append(getString(R.string.main_planete_eau, planete.surface_water))
						stringBuilder.append(getString(R.string.main_planete_population, planete.population))

						// affichage final :
						texte_planete.text = stringBuilder.toString()
						progressbar_attente.visibility = View.GONE
					}
				}
			}

			override fun onFailure(call: Call<RetourWSPlanetes>, t: Throwable)
			{
				texte_planete.text = t.message
				progressbar_attente.visibility = View.GONE
			}

		})
	}

}