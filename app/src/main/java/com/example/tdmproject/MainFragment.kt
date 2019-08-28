package com.example.tdmproject


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.navigation.findNavController
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_main.*

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class MainFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        login.setOnClickListener(){view ->
            view.findNavController().navigate(R.id.action_main_fragment_to_login_fragment)

            //            Toast.makeText( activity,"heey login",Toast.LENGTH_SHORT).show()
        }

        register.setOnClickListener(){view ->
            view.findNavController().navigate(R.id.action_main_fragment_to_register_fragment)

            //            Toast.makeText( activity,"heey login",Toast.LENGTH_SHORT).show()
        }

        browse.setOnClickListener(){view ->
            view.findNavController().navigate(R.id.action_main_fragment_to_browseFragment)

            //            Toast.makeText( activity,"heey login",Toast.LENGTH_SHORT).show()
        }


        }



}
