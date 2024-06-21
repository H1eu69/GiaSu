package com.projectprovip.h1eu.giasu.common

import android.content.Context
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.unit.sp
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.projectprovip.h1eu.giasu.R
import kotlin.random.Random

object Constant {
    const val API_BASE_URL = "http://matthomelab.dns.army:8080/api/"
    const val LOCATION_API_BASE_URL = "https://vapi.vnappmob.com"
    const val RECOMMEND_API_BASE_URL = "https://recommendapis.azurewebsites.net"
    const val DEEPLINK_BANK_BASE_URL = "https://api.vietqr.io/v2/"
    const val TOKEN_STRING = "token"
    const val USERNAME_STRING = "username"
    const val USERID_STRING = "userid"
    const val USER_IMAGE_STRING = "user_image"
    const val USER_EMAIL_STRING = "email"
    const val USER_ROLE_STRING = "role"

}

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "share data")

class EDSTextStyle {
    companion object {
        val provider = GoogleFont.Provider(
            providerAuthority = "com.google.android.gms.fonts",
            providerPackage = "com.google.android.gms",
            certificates = R.array.com_google_android_gms_fonts_certs
        )

        fun Header(color: Color = Color.Black): TextStyle {
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

        fun Content(color: Color = Color.Black): TextStyle {
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

        fun H1Reg(color: Color = Color.Black): TextStyle {
            val fontName = GoogleFont("Inter")

            val font = FontFamily(
                Font(googleFont = fontName, fontProvider = provider)
            )

            return TextStyle(
                fontSize = 18.sp,
                color = color,
                fontFamily = font
            )
        }

        fun H1Med(color: Color = Color.Black): TextStyle {
            val fontName = GoogleFont("Inter")

            val font = FontFamily(
                Font(googleFont = fontName, fontProvider = provider)
            )

            return TextStyle(
                fontSize = 18.sp,
                color = color,
                fontFamily = font
            )
        }

        fun H1Large(color: Color = Color.Black): TextStyle {
            val fontName = GoogleFont("Inter")

            val font = FontFamily(
                Font(googleFont = fontName, fontProvider = provider)
            )

            return TextStyle(
                fontSize = 24.sp,
                color = color,
                fontFamily = font
            )
        }

        fun H1MedBold(color: Color = Color.Black): TextStyle {
            val fontName = GoogleFont("Inter")

            val font = FontFamily(
                Font(googleFont = fontName, fontProvider = provider)
            )

            return TextStyle(
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = color,
                fontFamily = font
            )
        }

        fun H2Reg(color: Color = Color.Black): TextStyle {
            val fontName = GoogleFont("Inter")

            val font = FontFamily(
                Font(googleFont = fontName, fontProvider = provider)
            )

            return TextStyle(
                fontSize = 15.sp,
                color = color,
                fontFamily = font
            )
        }

        fun H3Reg(color: Color = Color.Black): TextStyle {
            val fontName = GoogleFont("Inter")

            val font = FontFamily(
                Font(googleFont = fontName, fontProvider = provider)
            )

            return TextStyle(
                fontSize = 12.sp,
                color = color,
                fontFamily = font
            )
        }

        fun H2Thin(color: Color = Color.Black): TextStyle {
            val fontName = GoogleFont("Inter")

            val font = FontFamily(
                Font(googleFont = fontName, fontProvider = provider)
            )

            return TextStyle(
                fontSize = 15.sp,
                fontWeight = FontWeight.W300,
                color = color,
                fontFamily = font
            )
        }

        fun H2Bold(color: Color = Color.Black): TextStyle {
            val fontName = GoogleFont("Montserrat")

            val font = FontFamily(
                Font(
                    googleFont = fontName, fontProvider = provider,
                    weight = FontWeight.Bold
                )
            )

            return TextStyle(
                fontSize = 18.sp,
                color = color,
                fontFamily = font
            )
        }

        fun Logo(color: Color = Color.Black): TextStyle {
            val fontFamily = FontFamily(
                androidx.compose.ui.text.font.Font(R.font.mont_bold, FontWeight.Bold),
                androidx.compose.ui.text.font.Font(R.font.mont_regular, FontWeight.Normal)
            )

            return TextStyle(
                fontSize = 30.sp,
                color = color,
                fontFamily = fontFamily
            )
        }
    }
}

object CodeGenerator {
    fun generate(codeLength: Int = 6): String {
        val stringBuilder = StringBuilder()

        repeat(codeLength) {
            val digit = Random.nextInt(0, 10)
            stringBuilder.append(digit)
        }

        return stringBuilder.toString()
    }
}