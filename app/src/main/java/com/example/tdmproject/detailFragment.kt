package com.example.tdmproject


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import com.example.tdmproject.entity.Pharmacy
import com.example.tdmproject.viewmodel.PharmacyModel
import kotlinx.android.synthetic.main.fragment_detail.*


/**
 * A simple [Fragment] subclass.
 *
 */
class detailFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater!!.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val idPharmacy = arguments?.getInt("id")
        val pharmacyModel = ViewModelProviders.of(this).get(PharmacyModel::class.java)

        if (pharmacyModel.pharmacy!=null) {
            pharmacyModel.displayDetail(activity!!,pharmacyModel.pharmacy!!)
        }
        else {
            pharmacyModel.loadDetail(activity!!,idPharmacy!!)
        }
    }

}
