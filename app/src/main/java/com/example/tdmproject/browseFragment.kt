package com.example.tdmproject


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import org.jetbrains.anko.bundleOf
import com.example.tdmproject.adapter.PharmacyAdapter
import com.example.tdmproject.entity.Pharmacy
import com.example.tdmproject.viewmodel.PharmacyModel
import kotlinx.android.synthetic.main.fragment_browse.*


/**
 * A simple [Fragment] subclass.
 *
 */
class browseFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater!!.inflate(R.layout.fragment_browse, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // View Model instance
        val pharmacyModel = ViewModelProviders.of(activity!!).get(PharmacyModel::class.java)
        // If the list of pharmacies is null, load the list from DB
        if (pharmacyModel.pharmacies==null) {
            pharmacyModel.loadData(activity!!)
        }
        else {
            // After the rotation of the screen, use pharmacies of the ViewModel instance
            listpharmacies.adapter = PharmacyAdapter(activity!!, pharmacyModel.pharmacies!!)
        }


        listpharmacies.setOnItemClickListener { adapterView, view, i, l ->
            val pharmacy = (adapterView.getItemAtPosition(i) as Pharmacy)
            var bundle = bundleOf("id" to pharmacy.idPharmacy)
            view.findNavController().navigate(R.id.action_browseFragment_to_detailFragment,bundle)
        }
    }



}
