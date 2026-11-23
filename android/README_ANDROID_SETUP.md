# Android native implementation

Open the `android/` folder in Android Studio (JDK 17, Android Gradle Plugin 8.7.x).
The project includes:
- A WallpaperService drawing the live days/hours/minutes/seconds countdown.
- An activity with a button to set the live wallpaper.
- Three daily inexact repeating AlarmManager reminders (08:00, 13:00, 20:00).
- A static wallpaper preview drawable for sharing.

Before a public release:
- Add runtime POST_NOTIFICATIONS permission request for Android 13+ (the current sample catches permission denial but does not show the permission dialog).
- Add a user-facing settings screen for goal/deadline and store them in SharedPreferences.
- Test on the target OEM device. Android manufacturers differ: a Live Wallpaper is normally applied to the home screen; lock-screen live wallpaper support is device-specific.
- Add a FileProvider/content URI flow for robust sharing of the PNG asset if sharing from app storage rather than resource URI.
- Exact alarm behavior is deliberately not used; AlarmManager.setInexactRepeating may be delayed by Doze/battery optimization.
