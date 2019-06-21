# Java-todo
Todo application test for android developer role

TODO v.1.0.0:
* Create an Android application, using Java or Kotlin.
* You can create an iOS application too if you are skilled in both technologies.
* When the app starts for the first time, it should have a screen showing 3 swipe-through walkthrough views (image and text for each view).
* The app should have a login screen, that presents itself after swiping through the walkthrough screens.
* Store the username and password in database, using SharedPreferences or SQLite or any database of your choice, so that when the app gets launched for the second time onwards, the user will go straight to the “feed” screen. Think of a way to make this part secure.
* The app should have a “feed” screen, which will consume this feed (http://feeds.news24.com/articles/Fin24/Tech/rss) and display the title, description and an image if there is one. You may use a ListView or RecyclerView.
* The app should have a “details” screen, that should appear when an item from “feed” screen is tapped. This “details” screen will display the whole article in a WebView. You may want to clean up the HTML so that only the article is shown to the user, removing unnecessary sections like the website’s navigation.
* The “feed” screen’s ListView/RecyclerView should have a pull-to-refresh functionality, to update the list when pulled.
* A sign-out button/icon should be present in the “feed” screen (top-right of the screen, in the navigation bar), to logout the user and go to login screen.
* Consider caching the data in local database, so the app is offline-capable.
