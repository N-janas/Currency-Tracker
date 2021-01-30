package com.example.currency_tracker.View

import android.content.res.TypedArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.example.currency_tracker.Model.Entities.Favourites
import com.example.currency_tracker.R
import kotlinx.android.synthetic.main.favorite_row.view.*


class FavouritesListAdapter(private val stringArray: Array<String>, private val flags: TypedArray, private val navController: NavController) : RecyclerView.Adapter<FavouritesListAdapter.MyViewHolder>() {
    private var favouritesList = emptyList<Favourites>()

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.favorite_row,parent,false)
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem  = favouritesList[position]
        holder.itemView.favorite_row_baseTextView.text = stringArray[currentItem.currencyBaseId]
        holder.itemView.favorite_row_baseImage.setImageResource(flags.getResourceId(currentItem.currencyBaseId,0))
        holder.itemView.favorite_row_symbolTextView.text = stringArray[currentItem.currencySymbolId]
        holder.itemView.favorite_row_symbolImage.setImageResource(flags.getResourceId(currentItem.currencySymbolId,0))

        holder.itemView.setOnClickListener {
            val action = NewWelcomeFragmentDirections.actionNewWelcomeFragmentToNewConverterFragment(stringArray[currentItem.currencyBaseId],stringArray[currentItem.currencySymbolId])
            navController.navigate(action)
        }
    }

    override fun getItemCount(): Int {
       return this.favouritesList.size
    }

    fun setData(fav: List<Favourites>) {
        favouritesList = fav
        notifyDataSetChanged()
    }
}