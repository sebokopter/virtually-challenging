package de.heilsen.compat.edittext

import android.content.Context
import android.os.Build
import android.text.InputType
import android.text.method.DigitsKeyListener
import androidx.annotation.RequiresApi
import androidx.core.os.ConfigurationCompat
import java.util.*

class LocalDigitsKeyListener : DigitsKeyListener {
    @Suppress("DEPRECATION")
    @Deprecated("Use the constructor with a locale if on SDK 26+.")
    constructor() : super()

    @RequiresApi(Build.VERSION_CODES.O)
    constructor(locale: Locale, sign: Boolean, decimal: Boolean) : super(locale, sign, decimal)

    @Suppress("DEPRECATION")
    @Deprecated("Use the constructor with a locale if on SDK 26+.")
    constructor(sign: Boolean, decimal: Boolean) : super(sign, decimal)

    override fun getInputType(): Int {
        return InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL
    }

    companion object {
        fun localDigitsKeyListener(context: Context, decimal: Boolean): LocalDigitsKeyListener {
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                LocalDigitsKeyListener(
                    ConfigurationCompat.getLocales(context.resources.configuration)[0],
                    sign = false,
                    decimal = decimal
                )
            } else {
                @Suppress("DEPRECATION")
                LocalDigitsKeyListener(false, decimal)
            }
        }
    }
}