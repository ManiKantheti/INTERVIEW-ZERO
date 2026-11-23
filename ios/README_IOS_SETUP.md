# iOS integration notes

These Swift files are source components to add to an Xcode iOS project:
1. Create an iOS App project named `InterviewZero` (SwiftUI, iOS 17+).
2. Add `InterviewZeroApp.swift` to the app target.
3. Add a Widget Extension named `InterviewZeroWidget`, then replace its generated Swift file with `InterviewZeroWidget.swift`.
4. Ensure the widget target has WidgetKit and SwiftUI; the app target has UserNotifications.
5. Build/sign on macOS with Xcode, install on iPhone, add the widget to Lock Screen customization.

iOS controls widget refresh timing. The timeline requests periodic updates but cannot guarantee per-second updates on the Lock Screen. The app's in-app TimelineView can update each second while visible. Daily notifications use repeating local calendar triggers at 08:00, 13:00 and 20:00 after permission is granted.

The app and widget currently share the fixed challenge deadline (31 Dec 2026, 09:00 local time). For a production version, move the goal/deadline into an App Group shared UserDefaults container and reload the widget after edits.
