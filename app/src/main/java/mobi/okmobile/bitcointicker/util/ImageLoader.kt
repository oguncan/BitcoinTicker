package mobi.okmobile.bitcointicker.util

import android.widget.ImageView
import com.bumptech.glide.Glide
import mobi.okmobile.bitcointicker.R

object ImageLoader {

    fun loadImage(
        view: ImageView,
        url: String,
        placeholder: Int = R.drawable.ic_launcher_background
    ) {
        Glide.with(view)
            .load(url)
            .placeholder(placeholder)
            .error(placeholder)
            .fallback(placeholder)
            .into(view)
    }
}