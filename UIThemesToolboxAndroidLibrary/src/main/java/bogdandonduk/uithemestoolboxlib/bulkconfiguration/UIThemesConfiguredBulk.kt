package bogdandonduk.uithemestoolboxlib.bulkconfiguration

class UIThemesConfiguredBulk<T>(var configuration: (instance: T) -> Unit, vararg items: T) {
    private val items = mutableListOf<T>()

    init {
        items.forEach {
            this.items.add(it)
        }
    }

    @Synchronized
    fun addItem(item: T) {
        items.add(item)
    }

    fun contains(item: T) = items.contains(item)

    @Synchronized
    fun removeItem(item: T) {
        items.remove(item)
    }

    fun configure() {
        items.forEach {
            configuration.invoke(it)
        }
    }
}