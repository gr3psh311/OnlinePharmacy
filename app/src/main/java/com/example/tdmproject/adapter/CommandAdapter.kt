package com.example.tdmproject.adapter


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
//import android.widget.ImageView
import android.widget.TextView
import com.example.tdmproject.R
import com.example.tdmproject.entity.Command
import android.util.Log



class CommandAdapter(_ctx:Context,_listCommands:List<Command>):BaseAdapter() {
    var ctx = _ctx
    val listCommands = _listCommands



    override fun getItem(p0: Int) = listCommands.get(p0)

    override fun getItemId(p0: Int) = listCommands.get(p0).hashCode().toLong()

    override fun getCount()= listCommands.size


    override fun getView(position: Int, p0: View?, parent: ViewGroup?): View {

        Log.d("youcef","listCmds: "+listCommands)
        Log.d("youcef","listCmdsSize: "+listCommands.size)

        var view = p0
        var viewHolder: ViewHolder
        if(view == null) {
            view = LayoutInflater.from(ctx).inflate(R.layout.command_layout,parent,false)
            val list_date = view?.findViewById<TextView>(R.id.list_date) as TextView
            val list_address2 = view?.findViewById<TextView>(R.id.list_address2) as TextView
            val list_status = view?.findViewById<TextView>(R.id.list_status) as TextView
            viewHolder= ViewHolder(list_date, list_address2,list_status)
            view.setTag(viewHolder)
        }
        else {
            viewHolder = view.getTag() as ViewHolder

        }
        //Glide.with(ctx).load(baseUrl + listPharmacies.get(position).listImage).apply(RequestOptions().placeholder(R.drawable.place_holder)).into(viewHolder.imageList)

        viewHolder.list_date.setText(listCommands.get(position).date)
        viewHolder.list_address2.setText(listCommands.get(position).pharmacy_address)
        viewHolder.list_status.setText(listCommands.get(position).etat)
        return view

    }

     data class ViewHolder(var list_date:TextView,var list_address2:TextView, var list_status:TextView)
}