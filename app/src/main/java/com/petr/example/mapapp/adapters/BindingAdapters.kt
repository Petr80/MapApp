package com.petr.example.mapapp.adapters

import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.petr.example.mapapp.R

@BindingAdapter("isGone")
fun bindIsGone(view: View, isGone: Boolean) {
    view.visibility = if (isGone) {
        View.GONE
    } else {
        View.VISIBLE
    }
}

@BindingAdapter("imageFromUrl", "circleCrop", requireAll = false)
fun bindImageFromUrl(view: ImageView, imageUrl: String?, circleCrop: Boolean
) {
    val request = Glide.with(view.context).load(imageUrl)
    if (circleCrop) request.circleCrop()
    if (!imageUrl.isNullOrEmpty())
        request
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(view)
}

@BindingAdapter("dropDownItemsDialogRoll1")
fun AutoCompleteTextView.setRoll1Items(breedArray: Array<String>?)=
        setAdapter(ArrayAdapter(
                context,
                R.layout.roll_items,
                resources.getStringArray(R.array.roll_array_1)
                )
        )

@BindingAdapter("dropDownItemsDialogRoll2")
fun AutoCompleteTextView.setRoll2tems(breedArray: Array<String>?)=
        setAdapter(ArrayAdapter(
                context,
                R.layout.roll_items,
                resources.getStringArray(R.array.roll_array_2)
                )
        )