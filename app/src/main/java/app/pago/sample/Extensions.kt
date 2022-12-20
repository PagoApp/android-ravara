package app.pago.sample

import android.content.res.Resources

val Int.fromDpToPx: Int
    get() = (this * Resources.getSystem().displayMetrics.density + 0.5f).toInt()