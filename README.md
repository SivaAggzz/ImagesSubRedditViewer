# ImagesSubRedditViewer
ImagesSubRedditViewer is an unofficial app for browsing a specific subreddit /r/images. The app will load up the subreddit url, and display all the posts as a list of images in the UI.

# Basic setup
No setup is required in order to use TriviaApp. Just Install the application and you are ready to roll.

# Features
- List images from subreddit /r/images and displays them as list.
- View full screen image on clicking.
- Followed Material design standards
- LiveData and DataBinding Methods used (Jetpack)
- No boilerplate code
- Managed the activity data using lifecycles

# Image Loading Library features
- Able to asynchronously load the image onto the ImageView.
- Able to load the images faster by caching it in memory.
- Able to load the images faster by using disk-level caching.
- Able to cancel the on-flight load request in case the loading is not needed anymore.

# Notes
- Used Design Pattern : MVVM
- Better usage of Background Threads using ThreadPoolExecutor
- Commented and explained code

# Permissions Used
- WRITE_EXTERNAL_STORAGE - for caching and downloading images.
- INTERNET - for parsing files from Reddit
