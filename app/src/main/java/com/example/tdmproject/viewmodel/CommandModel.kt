package com.example.tdmproject.viewmodel


import android.app.Activity
import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import android.view.View
import com.example.tdmproject.entity.Command
import com.example.tdmproject.adapter.CommandAdapter
import com.example.tdmproject.retrofit.RetrofitService
import kotlinx.android.synthetic.main.command_browse.*
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class CommandModel:ViewModel() {



    var command: Command? = null
    var commands:List<Command>? = null


    fun loadData(act:Activity) {

        act.progressBar2.visibility = View.VISIBLE
        getCommandsFromRemote(act)

        act.progressBar2.visibility = View.GONE

        Log.d("youcef","act: "+commands)
     //   act.listcommands.adapter = CommandAdapter(act, commands!!)




    }

    private fun getCommandsFromRemote(act:Activity) {

        val pref = act.getSharedPreferences("fileName", Context.MODE_PRIVATE)
        val nss= pref.getInt("nss",1)

        val call = RetrofitService.endpoint.getCommands(nss)
        call.enqueue(object : Callback<List<Command>> {
            override fun onResponse(call: Call<List<Command>>?, response: Response<List<Command>>?) {
                act.progressBar2.visibility = View.GONE

                if (response?.isSuccessful!!) {
                    commands = response.body()
                    Log.d("youcef","cmds: "+commands)
                    act.progressBar2.visibility = View.GONE
                    act.listcommands.adapter = CommandAdapter(act, commands!!)
                } else {
                    act.toast("Une erreur s'est produite 1.1: ")
                }

            }

            override fun onFailure(call: Call<List<Command>>?, t: Throwable?) {
                act.progressBar2.visibility = View.GONE
                act.toast("Une erreur s'est produite 2.1: "+ t.toString() )
            }


        })
    }

/*
    fun displayDetail(act: Activity,pharmacy: Pharmacy) {
        //Glide.with(act).load(baseUrl + city.detailImage).apply(RequestOptions().placeholder(R.drawable.place_holder)).into(act.imageDetail)

        Log.d("youcef","displayDetail "+pharmacy+ " ??")
        Log.d("youcef","phoneBefore"+act.phone_num_detail.text+ " !!")
        act.address_detail.text = pharmacy.address
        act.timing_detail.text = pharmacy.timing
        act.phone_num_detail.text = pharmacy.phone_num
        act.caisse_convention_detail.text = pharmacy.caisse_convention
        act.facebook_detail.text = pharmacy.facebook
        act.localisation_detail.text = pharmacy.localisation

        Log.d("youcef","address "+act.address_detail.text+" !!")
        //act.places.text = act.getString(R.string.places)+city.places
    }

*/


}