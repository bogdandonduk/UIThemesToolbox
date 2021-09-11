package bogdandonduk.uithemestoolboxlib.bulkconfiguration

import android.util.Log

class UIThemesBulkConfigurator(
    vararg configuredBulks: Pair<String, UIThemesConfiguredBulk<out Any>>
) {
    companion object {
        private const val ERROR_MESSAGE_BULK_IS_ALREADY_SAVED = "A configured bulk with this name is already saved"
        private const val ERROR_MESSAGE_NO_BULK_FOUND = "No configured bulk with this name is found in the collection, thus this method invocation has no effect"
    }

    private val bulks = mutableMapOf<String, UIThemesConfiguredBulk<out Any>>()

    init {
        configuredBulks.forEach {
            bulks[it.first] = it.second
        }
    }

    @Synchronized
    fun addConfiguredBulk(name: String, replaceIfPresent: Boolean = true, bulk: UIThemesConfiguredBulk<out Any>?) {
        if(bulk != null)
            if(replaceIfPresent || !bulks.contains(name))
                bulks[name] = bulk
            else logBulkAlreadySaved("addConfiguredBulk -> $name")
    }

    @Synchronized
    fun addConfiguredBulks(name: String, vararg bulks: UIThemesConfiguredBulk<out Any>?, replaceIfPresent: Boolean = true) {
        bulks.forEach {
            if(it != null)
                if(replaceIfPresent || !this.bulks.contains(name))
                    this.bulks[name] = it
                else logBulkAlreadySaved("addConfiguredBulk -> $name")
        }
    }

    @Synchronized
    fun removeConfiguredBulk(name: String) {
        var effect = false

        if(bulks.containsKey(name)) {
            bulks.remove(name)

            effect = true
        }

        if(!effect) logNoBulkFound("removeConfiguredBulk -> $name")
    }

    @Synchronized
    @Suppress("UNCHECKED_CAST")
    fun <T> addItemsToConfiguredBulk(name: String, replaceIfPresent: Boolean = true, vararg items: T?) {
        bulks[name].let {
            if(it != null)
                (it as UIThemesConfiguredBulk<T>).run {
                    items.forEach { item ->
                        if(item != null) {
                            if(replaceIfPresent)
                                removeItemsFromConfiguredBulk(name, items)

                            if(!contains(item)) addItem(item)
                        }
                    }
                }
            else
                logNoBulkFound("addItemToConfiguredBulk -> $name -> ")
        }
    }

    @Synchronized
    @Suppress("UNCHECKED_CAST")
    fun <T> addItemsToConfiguredBulkWithAutoReplacementIfPresent(name: String, vararg items: T?) {
        bulks[name].let {
            if(it != null)
                (it as UIThemesConfiguredBulk<T>).run {
                    items.forEach { item ->
                        if(item != null) {
                            removeItemsFromConfiguredBulk(name, items)

                            if(!contains(item)) addItem(item)
                        }
                    }
                }
            else
                logNoBulkFound("addItemsToConfiguredBulkWithAutoReplacementIfPresent -> $name -> ")
        }
    }

    @Synchronized
    @Suppress("UNCHECKED_CAST")
    fun <T> removeItemsFromConfiguredBulk(name: String, vararg items: T?) {
        bulks[name].let {
            if(it != null)
                (it as UIThemesConfiguredBulk<T>).run {
                    items.forEach { item ->
                        if(item != null)
                            removeItem(item)
                    }
                }
            else
                logNoBulkFound("removeItemsFromConfiguredBulk -> $name -> ")
        }
    }

    fun configure() {
        bulks.forEach {
            it.value.configure()
        }
    }

    fun configureExactBulk(name: String) {
        bulks[name].run {
            if(this != null)
                configure()
            else
                logNoBulkFound("configureExactBulk -> $name -> ")
        }
    }

    private fun logNoBulkFound(prefix: String = "") {
        Log.d(this::class.java.name, if(prefix.isNotEmpty()) "$prefix: " else "" + ERROR_MESSAGE_NO_BULK_FOUND)
    }

    private fun logBulkAlreadySaved(prefix: String = "") {
        Log.d(this::class.java.name, if(prefix.isNotEmpty()) "$prefix: " else "" + ERROR_MESSAGE_BULK_IS_ALREADY_SAVED)
    }
}