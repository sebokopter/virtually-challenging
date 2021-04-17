package de.heilsen.compat.edittext

import android.content.res.Configuration
import androidx.core.os.ConfigurationCompat
import java.util.*

val Configuration.localeCompat: Locale
    get() = ConfigurationCompat.getLocales(this)[0]