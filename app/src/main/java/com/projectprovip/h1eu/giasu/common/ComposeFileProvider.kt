package com.projectprovip.h1eu.giasu.common

import android.content.Context
import android.net.Uri
import androidx.core.content.FileProvider
import com.projectprovip.h1eu.giasu.R
import java.io.File

class ComposeFileProvider : FileProvider(
    R.xml.file_paths
) {
    companion object {
        fun getImageUri(context: Context): Uri {
            // 1
            val directory = File(context.filesDir, "images")
            directory.mkdirs()
            // 2
            val file = File.createTempFile(
                "my_images",
                ".jpg",
                directory
            )
            // 3
            val authority = context.packageName + ".fileprovider"
            // 4
            return getUriForFile(
                context,
                authority,
                file,
            )
        }
    }
}