package mobi.okmobile.bitcointicker.util

import android.content.Context
import android.content.Intent
import android.widget.TextView
import androidx.core.content.ContextCompat
import mobi.okmobile.bitcointicker.R
import mobi.okmobile.bitcointicker.util.extensions.trimParanthesis

object PercentHelper {
    fun showChangePercent(textView: TextView, _change: Double?) {
        val changePercent = "%.2f %%".format(_change).trimParanthesis()

        textView.text = changePercent
        val context = textView.context
        if (changePercent.contains("-")) {
            textView.setTextColor(
                ContextCompat.getColor(context, R.color.red)
            )
            textView.setCompoundDrawablesWithIntrinsicBounds(
                null, null,
                ContextCompat.getDrawable(context, R.drawable.ic_downtrend),
                null
            )
        } else {
            textView.setTextColor(
                ContextCompat.getColor(context, R.color.green)
            )
            textView.setCompoundDrawablesWithIntrinsicBounds(
                null, null,
                ContextCompat.getDrawable(context, R.drawable.ic_uptrend),
                null
            )
        }
    }
}