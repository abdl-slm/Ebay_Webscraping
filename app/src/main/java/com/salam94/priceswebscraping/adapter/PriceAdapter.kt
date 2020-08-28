package com.salam94.priceswebscraping.adapter

import android.text.Html
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.salam94.priceswebscraping.R
import com.salam94.priceswebscraping.model.Price
import com.salam94.priceswebscraping.utils.inflate
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_price.view.*


class PriceAdapter(private val card: List<Price>) :
    RecyclerView.Adapter<PriceAdapter.SetHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SetHolder {
        val inflatedView = parent.inflate(R.layout.item_price, false)
        return SetHolder(inflatedView)
    }

    override fun getItemCount(): Int {
        return card.size
    }

    override fun onBindViewHolder(holder: SetHolder, position: Int) {
        val itemCard = card[position]
        holder.bindSet(itemCard)
    }


    class SetHolder(v: View) : RecyclerView.ViewHolder(v),
        View.OnClickListener {
        private var view: View = v
        private var itemPrice: Price? = null

        init {
            v.setOnClickListener(this)
        }

        override fun onClick(v: View) {

        }


        fun bindSet(itemPrice: Price) {
            this.itemPrice = itemPrice

            view.txtName.text = Html.fromHtml(itemPrice.name).toString()
            view.txtPrice.text = Html.fromHtml(itemPrice.price).toString()
            Picasso.get().load(itemPrice.imageUrl).into(view.itemPhoto)
        }
    }


}