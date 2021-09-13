

# UIThemesToolbox

  Utility library that simplifies light-dark UI theme management on Android. It allows you to make the theme of your application totally independent from the system theme by managing custom preferences and dynamic loading of the alternative **night** resources whenever you decide.
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

```
