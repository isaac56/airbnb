//
//  CalendarViewModel.swift
//  airbnb
//
//  Created by Issac on 2021/05/19.
//

import Foundation

class CalendarBusinessCenter {
    typealias CalendarList = [YearMonthMetadata: [Day]]
    var calendarList: CalendarList
    
    init() {
        self.calendarList = CalendarList()
        makeCalendarList()
    }
    
    func makeCalendarList() {
        let currentDate = Date()
        let yearMonthMetadata = makeYearMonthMetadata(currentDate: currentDate)
        self.calendarList = makeEachDayMetaData(yearMonthMetadatas: yearMonthMetadata)
    }
    
    func makeEachDayMetaData(yearMonthMetadatas: [YearMonthMetadata]) -> CalendarList {
        var calendarList = CalendarList()
        yearMonthMetadatas.forEach { (yearMonthMetadata) in
            let indexCount = yearMonthMetadata.month.dayCount + yearMonthMetadata.startDay - 1 - 1
            for dayIndex in 0..<indexCount {
                if dayIndex < yearMonthMetadata.startDay - 2 {
                    let day = Day(day: 0, index: dayIndex, invisible: true)
                    calendarList[yearMonthMetadata, default: [Day]()].append(day)
                } else {
                    let dayNum = dayIndex - yearMonthMetadata.startDay + 3
                    let invisible = isInvisible(year: yearMonthMetadata.year, month: yearMonthMetadata.month.rawValue, day: dayNum)
                    let day = Day(day: dayNum, index: dayIndex, invisible: invisible)
                    calendarList[yearMonthMetadata, default: [Day]()].append(day)
                }
            }
        }
        return calendarList
    }
    
    func makeYearMonthMetadata(currentDate: Date) -> [YearMonthMetadata] {
        (0..<12).map({ (num: Int) -> YearMonthMetadata? in
            let eachDate = currentDate.findNextMonth(value: num)
            let components = eachDate?.get(.year, .month)
            guard let startDay = eachDate?.findFirstDayWeekDay else { return nil }
            guard let year = components?.year else { return nil }
            guard let month = components?.month else { return nil }
            guard let kindMonth = YearMonthMetadata.Month(rawValue: month)  else { return nil }
            return YearMonthMetadata(index: num, year: year, month: kindMonth, startDay: startDay)
        }).compactMap({ $0 })
    }
    
    func isInvisible(year: Int, month: Int, day: Int) -> Bool {
        let currnetDate = Date()
        let components = currnetDate.get(.year, .month, .day)
        guard let currentMonth = components.month,
              let currentDay = components.day,
              let currentYear = components.year else { return false }
        return year <= currentYear && month <= currentMonth && day < currentDay
    }
}
