package com.projectprovip.h1eu.giasu.common

import android.content.Context
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.unit.sp
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.projectprovip.h1eu.giasu.R
import com.projectprovip.h1eu.giasu.presentation.common.theme.EDSColors

object Constant {
    const val API_BASE_URL = "http://subedusmart.somee.com/api/"
    const val TOKEN_STRING = "token"
    const val USERNAME_STRING = "username"
    const val USERID_STRING = "userid"
    const val USER_IMAGE_STRING = "user_image"
}

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "share data")

class EDSTextStyle {
    companion object {
        val provider = GoogleFont.Provider(
            providerAuthority = "com.google.android.gms.fonts",
            providerPackage = "com.google.android.gms",
            certificates = R.array.com_google_android_gms_fonts_certs
        )
        fun Header(color : Color = Color.Black) : TextStyle {
            val fontName = GoogleFont("Inter")

            val font = FontFamily(
                Font(googleFont = fontName, fontProvider = provider)
            )

            return TextStyle(
                fontSize = 22.sp,
                color = color,
                fontWeight = FontWeight.W900,
                fontFamily = font
            )
        }

        fun Content(color : Color = Color.Black) : TextStyle {
            val fontName = GoogleFont("Inter")

            val font = FontFamily(
                Font(googleFont = fontName, fontProvider = provider)
            )

            return TextStyle(
                fontSize = 15.sp,
                color = color,
                fontWeight = FontWeight.Medium,
                fontFamily = font
            )
        }
    }
}