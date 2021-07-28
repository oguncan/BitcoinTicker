# BitcoinTicker

> Crypto Coins TRACKER Application

# The project is not yet completed.

## Architecture
MVVM (Model - ViewModel - View) is the design pattern used for making this app. 
MVVM provides a clear separation of concern and has great support in Android SDK in the form of [Architecture Components][1].

## Libraries Used

* [Architecture][1] - A collection of libraries that help you design robust, testable, and
  maintainable apps.
  * [Data Binding][2] - Declaratively bind observable data to UI elements.
  * [LiveData][3] - Build data objects that notify views when the underlying database changes.
  * [Room][4] - Access your app's SQLite database with in-app objects and compile-time checks.
  * [ViewModel][5] - Store UI-related data that isn't destroyed on app rotations. Easily schedule
     asynchronous tasks for optimal execution.
  * [Dagger][6] - For Dependeny Injection 
  * [Kodein][13] - For Dependeny Injection 
  * [Navigation][12] - Handle everything needed for in-app navigation.
* Third party
  * [Glide][7] - For image loading
  * [Kotlin Coroutines][8] - For managing background threads with simplified code and reducing needs for callbacks
  * [MPAndroidChart][9] - To chart the financial data
  * [Retrofit][10] - For making HTTP requests
  * [Firebase][15] - Cloud system where user information is stored
  * [coingecko][16] - API that broadcasts live data about crypto coin
  * [Timber][17] - A logger with a small, extensible API which provides utility
  * [RxJava][14] - For Kotlin RXJava


[1]: https://developer.android.com/jetpack/arch/
[2]: https://developer.android.com/topic/libraries/data-binding/
[3]: https://developer.android.com/topic/libraries/architecture/livedata
[4]: https://developer.android.com/topic/libraries/architecture/room
[5]: https://developer.android.com/topic/libraries/architecture/viewmodel
[6]: https://developer.android.com/training/dependency-injection/dagger-android
[7]: https://bumptech.github.io/glide/
[8]: https://kotlinlang.org/docs/reference/coroutines-overview.html
[9]: https://github.com/PhilJay/MPAndroidChart
[10]: https://github.com/square/retrofit
[11]: https://developer.android.com/training/dependency-injection/hilt-android
[12]: https://developer.android.com/topic/libraries/architecture/navigation/
[13]: https://kodein.org/di/
[14]: https://github.com/ReactiveX/RxJava
[15]: https://firebase.google.com/docs/build
[16]: https://www.coingecko.com/en/api
[17]: https://github.com/JakeWharton/timber

## Authors

* **Ogün Can KAYA**  - [Ogün Can KAYA](https://github.com/oguncan)
