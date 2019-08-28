package com.example.tdmproject.viewmodel


import android.app.Activity
import android.util.Log
import androidx.lifecycle.ViewModel
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.tdmproject.entity.Pharmacy
import com.example.tdmproject.adapter.PharmacyAdapter
import com.example.tdmproject.R
import com.example.tdmproject.retrofit.RetrofitService
import com.example.tdmproject.baseUrl
import com.example.tdmproject.roomdatabase.RoomService
import kotlinx.android.synthetic.main.fragment_browse.*
import kotlinx.android.synthetic.main.fragment_detail.*
import kotlinx.android.synthetic.main.fragment_main.*
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.math.log


class PharmacyModel:ViewModel() {



    var pharmacy: Pharmacy? = null
    var pharmacies:List<Pharmacy>? = null


    fun loadData(act:Activity) {

        act.progressBar1.visibility = View.VISIBLE
        // Get pharmacies from SQLite DB
        pharmacies = RoomService.appDataBase.getPharmacyDao().getPharmacies()

        if (pharmacies?.size == 0) {
            // If the list of pharmacies is empty, load from server and save them in SQLite DB
            getPharmaciesFromRemote(act)
        }
        else {

            act.progressBar1.visibility = View.GONE
            act.listpharmacies.adapter = PharmacyAdapter(act, pharmacies!!)
        }




    }

    private fun getPharmaciesFromRemote(act:Activity) {

        val call = RetrofitService.endpoint.getPharmacies()
        call.enqueue(object : Callback<List<Pharmacy>> {
            override fun onResponse(call: Call<List<Pharmacy>>?, response: Response<List<Pharmacy>>?) {
                act.progressBar1.visibility = View.GONE

                if (response?.isSuccessful!!) {
                    pharmacies = response?.body()
                    act.progressBar1.visibility = View.GONE
                    act.listpharmacies.adapter = PharmacyAdapter(act, pharmacies!!)
                    // save pharmacies in SQLite DB
                    RoomService.appDataBase.getPharmacyDao().addPharmacies(pharmacies!!)
                } else {
                    act.toast("Une erreur s'est produite 1: ")
                }

            }

            override fun onFailure(call: Call<List<Pharmacy>>?, t: Throwable?) {
                act.progressBar1.visibility = View.GONE
                act.toast("Une erreur s'est produite 2: "+ t.toString() )
            }


        })
    }

    fun loadDetail(act:Activity,idPharmacy:Int) {

        act.progressBar2.visibility = View.VISIBLE
        // load pharmacy detail from SQLite DB
        this.pharmacy = RoomService.appDataBase.getPharmacyDao().getPharmacyById(idPharmacy)
        //this.pharmacy = RoomService.appDataBase.getPharmacyDao().getPharmacyById(1)
        if(this.pharmacy?.idPharmacy==null) {
            // if the pharmacy details don't exist, load the details from server and update SQLite DB
            loadDetailFromRemote(act,idPharmacy)
        }
        else {
            act.progressBar2.visibility = View.GONE
            displayDetail(act, this.pharmacy!!)
        }

    }

    private fun loadDetailFromRemote(act:Activity,idPharmacy:Int) {

        val call = RetrofitService.endpoint.getDetailPharmacy(idPharmacy)
        call.enqueue(object : Callback<Pharmacy> {
            override fun onResponse(call: Call<Pharmacy>?, response: Response<Pharmacy>?) {
                act.progressBar2.visibility = View.GONE
                if(response?.isSuccessful!!) {
                    var pharmacyDetail = response?.body()
                    pharmacyDetail = pharmacy!!.copy(
                        //idPharmacy = pharmacyDetail?.idPharmacy,
                        ville = pharmacyDetail?.ville,
                        address = pharmacyDetail?.address,
                        timing = pharmacyDetail?.timing,
                        phone_num = pharmacyDetail?.phone_num,
                        caisse_convention = pharmacyDetail?.caisse_convention,
                        facebook = pharmacyDetail?.facebook,
                        localisation = pharmacyDetail?.localisation)
                    displayDetail(act,pharmacyDetail)
                    // update the pharmacy in the SQLite DB to support offline mode
                    RoomService.appDataBase.getPharmacyDao().updatePharmacy(pharmacyDetail)
                    // update ViewModel
                    this@PharmacyModel.pharmacy = pharmacyDetail

                }
                else {
                    act.toast("Une erreur s'est produite")

                }


            }

            override fun onFailure(call: Call<Pharmacy>?, t: Throwable?) {
                act.progressBar2.visibility = View.GONE
                act.toast("Une erreur s'est produite")

            }
        })
    }

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




}