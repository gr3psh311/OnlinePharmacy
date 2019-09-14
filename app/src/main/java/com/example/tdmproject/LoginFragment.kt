package com.example.tdmproject


import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.edit
import androidx.navigation.findNavController
import com.example.tdmproject.entity.User
import com.example.tdmproject.retrofit.RetrofitService
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_main.*
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response



/**
 * A simple [Fragment] subclass.
 *
 */
class LoginFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }



    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        signin.setOnClickListener { view ->


            if (phone_num_input.text.isEmpty()) activity!!.toast("Please, type your phone number!")
            else if (password_input.text.isEmpty()) activity!!.toast("Please, type your password!")
            else{

//                val tmpUser = User(0,"","","", phone_num_input.text.toString(), password_input.text.toString())

                val phone = phone_num_input.text.toString()
                val password = password_input.text.toString()
                val call = RetrofitService.endpoint.authuser(phone,password)

                call.enqueue(object : Callback<User> {
                    override fun onResponse(call: Call<User>?, response: Response<User>?) {

                        if (response?.isSuccessful!!) {
                            Log.d("youcef","auth response: " + response.body())

                            var userDetail = response.body()

                            if(userDetail == null) {
                                activity!!.toast("Authentication Failed, verify your password!")
                            }
                            else {
                                activity!!.toast("* Welcome "+ userDetail.name +" "+ userDetail.firstname +" *")

                                val pref = activity!!.getSharedPreferences("myfile",Context.MODE_PRIVATE)
                                pref.edit {
                                    putBoolean("connected", true)
                                    putString("info",""+ userDetail.name +" "+ userDetail.firstname +" ")
                                    putInt("nss",userDetail.nss)
                                    Log.d("youcef","nss added: "+userDetail.nss)
                                }
                                Log.d("youcef","Pref (connected): "+ pref.getBoolean("connected",false))
                                Log.d("youcef","Pref (info): "+ pref.getString("info","none"))


                                view.findNavController().popBackStack(R.id.main_fragment,false)

                            }



                        } else {
                            activity!!.toast("error, could not login! ")
                            Log.d("youcef","error: " + response.body().toString())
                        }

                    }

                    override fun onFailure(call: Call<User>?, t: Throwable?) {
                        Log.d("youcef","failure: "+ t.toString())
                        activity!!.toast("Authentication impossible! Failed to Connect" )
                    }


                })
            }


        }



    }





}
