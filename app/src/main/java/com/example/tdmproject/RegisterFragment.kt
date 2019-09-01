package com.example.tdmproject


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import kotlinx.android.synthetic.main.fragment_register.*
import com.example.tdmproject.entity.User
import com.example.tdmproject.retrofit.RetrofitService
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
//import com.twilio.Twilio
//import com.twilio.rest.api.v2010.account.Message
//import com.twilio.type.PhoneNumber



/**
 * A simple [Fragment] subclass.
 *
 */
class RegisterFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register, container, false)
    }



    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        signup.setOnClickListener { view ->


            if (nss_reg.text.isEmpty()) activity!!.toast("Please, type your NSS code!")
            else if (name_reg.text.isEmpty()) activity!!.toast("Please, type your name!")
            else if (firstname_reg.text.isEmpty()) activity!!.toast("Please, type your first name!")
            else if (address_reg.text.isEmpty()) activity!!.toast("Please, type your address!")
            else if (phone_num_reg.text.isEmpty()) activity!!.toast("Please, type your phone number!")
            else{

                val tmpUser = User(nss_reg.text.toString().toInt(), name_reg.text.toString(),firstname_reg.text.toString(),address_reg.text.toString(),phone_num_reg.text.toString(),"")
                val call = RetrofitService.endpoint.adduser(tmpUser)
                call.enqueue(object : Callback<String> {
                    override fun onResponse(call: Call<String>?, response: Response<String>?) {

                        if (response?.isSuccessful!!) {
                            Log.d("youcef","post registration to API: " + response.body())
                            activity!!.toast("* Registration completed * " + response.body())

                            //view.findNavController().popBackStack()
                            view.findNavController().navigate(R.id.action_register_fragment_to_login_fragment)
                        } else {
                            activity!!.toast("error, registration uncompleted: ")
                            Log.d("youcef","error: " + response.body().toString())
                        }

                    }

                    override fun onFailure(call: Call<String>?, t: Throwable?) {
                        activity!!.toast("Failure, registration uncompleted: "+ t.toString() )
                    }


                })
            }


        }



    }



}
