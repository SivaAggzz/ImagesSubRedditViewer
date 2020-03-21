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

# Image Loading Library decisions
- It uses ThreadPoolExecutor to load images in background so that its possible to load large images by decoding and resizing them without dropping frames and also queing of new tasks are possible here.

# Image Loading Library assumptions
- It assumes that the image is present in memory, it checks the memory cache.If found good return it
else it checks for filecache,if found good return it. If not found in both caches, it then finally downloads the file from Internet and caches it to the local available caches.

# Notes
- Used Design Pattern : MVVM
- Better usage of Background Threads using ThreadPoolExecutor
- Commented and explained code

# Permissions Used
- WRITE_EXTERNAL_STORAGE - for caching and downloading images.
- INTERNET - for parsing files from Reddit
