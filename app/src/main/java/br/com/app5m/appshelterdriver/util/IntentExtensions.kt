package br.com.app5m.appshelterdriver.util

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.ResolveInfo
import android.os.Build
import android.os.Parcelable

fun Intent.createChooserFiltered(context: Context, filter: (ResolveInfo) -> Boolean): Intent {
    val results = context.packageManager.queryIntentActivities(this, 0)
        .toMutableList()

    return if (results.isNotEmpty() && Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        val intentsToRemove: List<Parcelable> = results.asSequence()
            .filter { filter(it) }
            .map { ComponentName(it.activityInfo.packageName, it.activityInfo.name) }
            .toList()

        val chooserIntent = Intent.createChooser(this, "Escolha um aplicativo de navegação:")
        chooserIntent.putExtra(Intent.EXTRA_EXCLUDE_COMPONENTS, intentsToRemove.toTypedArray())
        chooserIntent
    } else {
        Intent.createChooser(this, "Escolha um aplicativo de navegação:")
    }
}

fun Intent.createMapChooser(context: Context): Intent {
    return this.createChooserFiltered(context) { resInfo ->
        resInfo.activityInfo.packageName.contains("cabify")
                || resInfo.activityInfo.packageName.contains("uber")
                || resInfo.activityInfo.packageName.contains("99")
    }
}