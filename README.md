# UIThemesToolbox

  Utility library that simplifies light-dark UI theme management on Android. It allows you to make the theme of your application totally independent from the system theme by managing custom preferences and dynamic loading of the default or alternative **night** resources whenever you decide. It also conveniently organizes the dynamic theme switching via corresponding interface and a tool that can configure your views in bulks.
  ![](https://github.com/bogdandonduk/UIThemesToolbox/blob/master/device-2021-09-13-001700.png)![](https://github.com/bogdandonduk/UIThemesToolbox/blob/master/device-2021-09-13-133728.png)
![](https://github.com/bogdandonduk/UIThemesToolbox/blob/master/PicsArt_09-13-01.32.32.png)![](https://github.com/bogdandonduk/UIThemesToolbox/blob/master/PicsArt_09-13-01.33.26.png)
## Include in your project  
**Gradle dependency**  
  
Add this in your **app**-level **build.gradle** file:  
```groovy
dependencies {  
	...  
  
	def latest_version_tag = 2.0
	implementation "com.github.bogdandonduk:ViewDataBindingWrappers:$latest_version_tag"  
  
	...  
}  
```  
You can always find the **latest_version_tag** [here](https://github.com/bogdandonduk/UIThemesToolbox/releases).  
  
Also make sure you have this repository in your **project**-level **build.gradle** file:  
```groovy  
allprojects {  
	repositories {  
		...  
  
		maven { url 'https://jitpack.io' }  
	}  
}  
```  

# Examples of usage
```kotlin 
// Static methods of UIThemesToolbox object provide you various utilities for theme management

// detect if the current theme is dark with this method. it returns the setting of your application or system theme if there is none set
val isDarkTheme: Boolean = UIThemesToolbox.isDarkThemeEnabled(context)

// it is also possible to detect whether the dark theme setting is set manually or automatic (system)
val isDarkThemeSetByMyUser = !UIThemesToolbox.isDarkThemeSettingAutomatic(context)

// set the theme like this
UIThemesToolbox.setDarkTheme(context, enabled = true /* pass false to set light theme */, uiThemesHandler = null)

// you can remove the manual settings of theme and delegate the theme management in your app back to system
UIThemesToolbox.removeManualSetting(context, uiThemesHandler = null)

// the null arguments in the two methods above are of UIThemesHandler, which is interface. 
// You can implement it and its method initUITheme() where you should do configurations of all your views for the new theme (refreshing). 
// Then you can pass your uiThemesHandler to two methods above and whenever the theme settings changes, your whole UI will update instantly.

// you can dynamically and automatically load resources (default or alternative) for the theme setting of your app like this:
val backgroundColor = UIThemesToolbox.resolveColorResourceAuto(context, R.color.background_color)

// you can also explicitly specify configuration:

val backgroundColor = UIThemesToolbox.resolveColorResouseManual(context, R.color.background_color, darkTheme = true)

// or you can even get reverse resources, e.g. dark resources when the theme is light and vice-versa:
val backgroundColor = UIThemesToolbox.resolveColorResourceReverse(context, R.color.background_color)

// there is also a bulk configurator tool that can organize your views into bulks and configure them all by bulk name

// initialize bulk configurator like this
val bulkConfigurator = UIThemesBulkConfigurator(
	Pair("text_views" /* bulk_name */, UIThemesConfiguredBulk<TextView>({ textView: TextView -> // bulk's configuration action lambda
textView.setTextColor(UIThemesToolbox.resolveColorResourceAuto(context, R.color.text))
	}),
	Pair("background_views" /* bulk_name */, UIThemesConfiguredBulk<View>({ View: View -> // bulk's configuration action lambda
view.setBackgroundColor(UIThemesToolbox.resolveColorResourceAuto(context, R.color.text))
	})
	)
)

// add views to your defined bulks 
bulkConfigurator.addItemsToConfiguredBulkWithAutoReplacementIfPresent(
	"text_views",
	findViewById<TextView(R.id.title_text_view),
	findViewById<TextView>(R.id.message_text_view)
)

// and configure a specific bulk by name
bulkConfigurator.configureExactBulk("text_views")

// or all bulks

bulkConfigurator.configure()
```
