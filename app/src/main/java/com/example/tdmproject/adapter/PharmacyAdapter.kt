package com.example.tdmproject.adapter


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.tdmproject.R
import com.example.tdmproject.baseUrl
import com.example.tdmproject.entity.Pharmacy
import org.jetbrains.anko.toast



class PharmacyAdapter(_ctx:Context,_listPharmacies:List<Pharmacy>):BaseAdapter() {
    var ctx = _ctx
    val listPharmacies = _listPharmacies

    override fun getItem(p0: Int) = listPharmacies.get(p0)

    override fun getItemId(p0: Int) = listPharmacies.get(p0).hashCode().toLong()

    override fun getCount()= listPharmacies.size


    override fun getView(position: Int, p0: View?, parent: ViewGroup?): View {

        var view = p0
        var viewHolder: ViewHolder
        if(view == null) {
            view = LayoutInflater.from(ctx).inflate(R.layout.pharmacy_layout,parent,false)
            val list_ville = view?.findViewById<TextView>(R.id.list_ville) as TextView
            val list_address = view?.findViewById<TextView>(R.id.list_address) as TextView
            viewHolder= ViewHolder(list_ville, list_address)
            view.setTag(viewHolder)
        }
        else {
            viewHolder = view.getTag() as ViewHolder

        }
        //Glide.with(ctx).load(baseUrl + listPharmacies.get(position).listImage).apply(RequestOptions().placeholder(R.drawable.place_holder)).into(viewHolder.imageList)

        viewHolder.list_ville.setText(listPharmacies.get(position).ville)
        viewHolder.list_address.setText(listPharmacies.get(position).address)
        return view

    }

        private data class ViewHolder(var list_ville:TextView,var list_address:TextView)
}