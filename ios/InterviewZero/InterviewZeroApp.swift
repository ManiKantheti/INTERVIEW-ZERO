import SwiftUI
import UserNotifications

@main
struct InterviewZeroApp: App {
    var body: some Scene { WindowGroup { ContentView() } }
}
struct ContentView: View {
    @State private var permission = "Notifications not configured"
    var body: some View {
        ZStack {
            LinearGradient(colors:[Color(red:0.04,green:0.07,blue:0.17),.indigo,.black],startPoint:.topLeading,endPoint:.bottomTrailing).ignoresSafeArea()
            VStack(spacing:18) {
                Text("INTERVIEW//ZERO").font(.system(size:26,weight:.black,design:.rounded)).foregroundStyle(.white)
                Text("JOB INTERVIEW").font(.headline).foregroundStyle(.cyan)
                TimelineView(.periodic(from:.now,by:1)) { context in
                    let target=Calendar.current.date(from:DateComponents(year:2026,month:12,day:31,hour:9)) ?? .now
                    let c=Calendar.current.dateComponents([.day,.hour,.minute,.second],from:context.date,to:target)
                    VStack { Text("\\(max(0,c.day ?? 0))").font(.system(size:72,weight:.light,design:.rounded));Text("DAYS LEFT").font(.caption).tracking(3);Text(String(format:"%02d:%02d:%02d",max(0,c.hour ?? 0),max(0,c.minute ?? 0),max(0,c.second ?? 0))).font(.title2.monospacedDigit()) }.foregroundStyle(.white)
                }
                Text("31 DECEMBER 2026").foregroundStyle(.white.opacity(0.7))
                Button("Schedule 3 daily reminders") { scheduleReminders() }.buttonStyle(.borderedProminent).tint(.purple)
                Text(permission).font(.caption).foregroundStyle(.white.opacity(0.7))
            }.padding()
        }
    }
    func scheduleReminders() {
        UNUserNotificationCenter.current().requestAuthorization(options:[.alert,.sound,.badge]) { granted,error in
            guard granted else { DispatchQueue.main.async { permission="Notification permission not granted" }; return }
            let times=[(8,0,"Morning preparation check-in"),(13,0,"Midday interview practice"),(20,0,"Evening review and plan")]
            for (i,t) in times.enumerated() {
                var c=DateComponents();c.hour=t.0;c.minute=t.1
                let content=UNMutableNotificationContent();content.title="INTERVIEW//ZERO";content.body=t.2;content.sound = .default
                let trigger=UNCalendarNotificationTrigger(dateMatching:c,repeats:true)
                UNUserNotificationCenter.current().add(UNNotificationRequest(identifier:"interviewzero.daily.\\(i)",content:content,trigger:trigger))
            }
            DispatchQueue.main.async { permission="Daily reminders scheduled" }
        }
    }
}