package com.example.tdmproject


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import com.example.tdmproject.adapter.CommandAdapter
import com.example.tdmproject.viewmodel.CommandModel
import kotlinx.android.synthetic.main.command_browse.*


/**
 * A simple [Fragment] subclass.
 *
 */
class browseCommandsFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater!!.inflate(R.layout.command_browse, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // View Model instance
        val commandModel = ViewModelProviders.of(activity!!).get(CommandModel::class.java)

            commandModel.loadData(activity!!)
//            listcommands.adapter = CommandAdapter(activity!!, commandModel.commands!!)

    }



}
