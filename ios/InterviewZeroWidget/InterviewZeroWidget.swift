import WidgetKit
import SwiftUI

struct CountdownEntry: TimelineEntry { let date: Date }
struct CountdownProvider: TimelineProvider {
    func placeholder(in context: Context) -> CountdownEntry { CountdownEntry(date: .now) }
    func getSnapshot(in context: Context, completion: @escaping (CountdownEntry)->Void) { completion(CountdownEntry(date:.now)) }
    func getTimeline(in context: Context, completion: @escaping (Timeline<CountdownEntry>)->Void) {
        let now=Date()
        // Request periodic refresh; iOS ultimately decides the actual refresh cadence.
        let entries=(0..<12).map { CountdownEntry(date:Calendar.current.date(byAdding:.minute,value:$0*15,to:now) ?? now) }
        completion(Timeline(entries:entries,policy:.after(Calendar.current.date(byAdding:.minute,value:180,to:now) ?? now)))
    }
}
struct CountdownWidgetView: View {
    var entry: CountdownProvider.Entry
    var body: some View {
        let target=Calendar.current.date(from:DateComponents(year:2026,month:12,day:31,hour:9)) ?? .now
        let c=Calendar.current.dateComponents([.day,.hour,.minute],from:entry.date,to:target)
        ZStack {
            LinearGradient(colors:[Color(red:0.04,green:0.08,blue:0.2),.purple.opacity(0.7)],startPoint:.topLeading,endPoint:.bottomTrailing)
            VStack(alignment:.leading,spacing:3) {
                Text("INTERVIEW//ZERO").font(.system(size:10,weight:.bold,design:.rounded)).tracking(1)
                Text("\\(max(0,c.day ?? 0))").font(.system(size:34,weight:.light,design:.rounded)).minimumScaleFactor(0.5)
                Text("DAYS TO JOB INTERVIEW").font(.system(size:8,weight:.bold)).tracking(1)
                Text("31 DEC 2026").font(.system(size:9,weight:.medium))
            }.foregroundStyle(.white).padding(8)
        }.containerBackground(.black,for:.widget)
    }
}
@main struct InterviewZeroWidget: Widget {
    let kind="InterviewZeroCountdown"
    var body: some WidgetConfiguration {
        StaticConfiguration(kind:kind,provider:CountdownProvider()) { entry in CountdownWidgetView(entry:entry) }
        .configurationDisplayName("Interview Zero Countdown")
        .description("Days remaining until your job interview deadline.")
        .supportedFamilies([.accessoryRectangular,.accessoryInline,.systemSmall])
    }
}