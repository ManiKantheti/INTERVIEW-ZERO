# INTERVIEW//ZERO — Cross-platform countdown challenge project

## Deliverables
- `web/`: responsive companion app/PWA with live countdown, milestone CRUD, progress, motivational copy, wallpaper export/share and best-effort browser reminders.
- `android/`: native Android Studio project source with a `WallpaperService` live countdown and three daily local reminders.
- `ios/`: SwiftUI app source, WidgetKit Lock Screen widget source and repeating local notification scheduler.
- `assets/`: mountain background and generated 1170 × 2532 wallpaper.
- `submission/HANDSHAKE_SUBMISSION.txt`: title, description, cover guidance and Gemini prompts.

## Important platform behavior
A wallpaper PNG is static. It cannot update itself or send notifications. Android live wallpaper code can redraw a countdown on devices that support live wallpapers; many devices apply it to the home screen, and lock-screen support varies by OEM. iOS uses a WidgetKit Lock Screen widget; iOS decides refresh cadence and does not guarantee a seconds-by-seconds countdown on the Lock Screen. Local notifications require permission and may be delayed or suppressed by system focus/battery policies. The included web reminders are best-effort while the browser/PWA is running; use native builds for scheduled notifications.

## Run the web companion
```bash
cd web
python3 -m http.server 8000
```
Open http://localhost:8000. For PWA install and service worker, use localhost or HTTPS. On hosting, place the `assets/` directory at the project root alongside `web/`, or adjust the relative paths as described below. Easiest deployment: move `web` contents to root and copy `assets/` beside it; update asset references from `../assets/` to `assets/`.

## Android build
Open `android/` in Android Studio with JDK 17. See `android/README_ANDROID_SETUP.md`. The sample uses Android Gradle Plugin 8.7.3 and Kotlin 2.0.21. Install on a real device to test wallpaper and notification behavior. Android 13+ runtime notification permission request should be added before release.

## iOS build
Use macOS and Xcode. Create an iOS app and Widget Extension, then add the source files as described in `ios/README_IOS_SETUP.md`. An Apple Developer account may be needed for device deployment/signing depending on Xcode/device setup. Lock Screen widget refresh is OS-controlled.

## Challenge-specific Gemini workflow
The challenge asks participants to use Gemini to plan milestones, design the countdown lock screen and optionally schedule reminders. The prompts are provided in the submission file, but you must actually run them in your Google Gemini account and review/refine the generated plan/design. This project does not claim an API integration with Gemini.

## Default goal
Job Interview — 31 December 2026 at 09:00 local device time.
