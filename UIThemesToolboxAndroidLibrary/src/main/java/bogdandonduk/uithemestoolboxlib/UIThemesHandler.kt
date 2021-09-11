package bogdandonduk.uithemestoolboxlib

import android.view.View
import android.widget.ImageView

interface UIThemesHandler {
    fun initUITheme()

    fun setUIThemeToggle(view: View?, icon: ImageView?, afterAction: ((view: View) -> Unit)? = null) {

    }
}