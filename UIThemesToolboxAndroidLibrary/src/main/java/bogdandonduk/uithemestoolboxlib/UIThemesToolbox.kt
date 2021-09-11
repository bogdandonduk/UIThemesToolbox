
package bogdandonduk.uithemestoolboxlib

import android.content.Context
import android.content.SharedPreferences
import android.content.res.Configuration
import android.content.res.Resources
import android.view.View
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StyleRes
import androidx.core.content.res.ResourcesCompat

object UIThemesToolbox {
    private const val delimiter = "_"

    private const val LIBRARY_SHARED_PREFS_SUFFIX = "${delimiter}shared${delimiter}prefs${delimiter}bogdandonduk.uilanguagestoolboxlib"
    private const val IS_DARK_THEME_ENABLED = "is${delimiter}dark${delimiter}mode${delimiter}enabled$LIBRARY_SHARED_PREFS_SUFFIX"

    fun isDarkThemeSettingAutomatic(context: Context) = !getPreferences(context).contains(IS_DARK_THEME_ENABLED)

    private fun getPreferences(context: Context) : SharedPreferences =
        context.getSharedPreferences(context.packageName + LIBRARY_SHARED_PREFS_SUFFIX, Context.MODE_PRIVATE)

    fun isDarkThemeEnabled(context: Context) : Boolean =
        getPreferences(context)
            .getBoolean(IS_DARK_THEME_ENABLED, context.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_YES)


    fun setDarkTheme(context: Context, enabled: Boolean, uiThemesHandler: UIThemesHandler?) {
        getPreferences(context).edit().putBoolean(IS_DARK_THEME_ENABLED, enabled).apply()

        uiThemesHandler?.initUITheme()
    }

    fun removeManualSetting(context: Context, uiThemesHandler: UIThemesHandler?) {
        getPreferences(context).edit().remove(IS_DARK_THEME_ENABLED).apply()

        uiThemesHandler?.initUITheme()
    }

    private fun getConfiguredResources(context: Context, darkTheme: Boolean) =
        context.createConfigurationContext(Configuration(context.resources.configuration).apply { uiMode = if(darkTheme) Configuration.UI_MODE_NIGHT_YES else Configuration.UI_MODE_NIGHT_NO }).resources

    fun resolveColorResourceManual(context: Context, @ColorRes colorResId: Int, darkTheme: Boolean, theme: Resources.Theme? = null) =
        ResourcesCompat.getColor(getConfiguredResources(context, darkTheme), colorResId, theme)

    fun resolveColorResourceAuto(context: Context, @ColorRes colorResId: Int, theme: Resources.Theme? = null) =
        ResourcesCompat.getColor(getConfiguredResources(context, isDarkThemeEnabled(context)), colorResId, theme)

    fun resolveColorResourceReverse(context: Context, @ColorRes colorResId: Int, theme: Resources.Theme? = null) =
        ResourcesCompat.getColor(getConfiguredResources(context, !isDarkThemeEnabled(context)), colorResId, theme)

    fun resolveDrawableResourceManual(context: Context, @DrawableRes drawableResId: Int, darkTheme: Boolean, theme: Resources.Theme? = null) =
        ResourcesCompat.getDrawable(getConfiguredResources(context, darkTheme), drawableResId, theme)

    fun resolveDrawableResourceAuto(context: Context, @DrawableRes drawableResId: Int, theme: Resources.Theme? = null) =
        ResourcesCompat.getDrawable(getConfiguredResources(context, isDarkThemeEnabled(context)), drawableResId, theme)

    fun resolveDrawableResourceReverse(context: Context, @DrawableRes drawableResId: Int, theme: Resources.Theme? = null) =
        ResourcesCompat.getDrawable(getConfiguredResources(context, isDarkThemeEnabled(context)), drawableResId, theme)

    fun initViewTheme(context: Context, view: View, @StyleRes darkThemeResId: Int, @StyleRes lightThemeResId: Int) {
        view.context?.setTheme(if(isDarkThemeEnabled(context)) darkThemeResId else lightThemeResId)

        view.invalidate()
    }
}