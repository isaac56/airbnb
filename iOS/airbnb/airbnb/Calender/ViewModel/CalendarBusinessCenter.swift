//
//  CalendarViewModel.swift
//  airbnb
//
//  Created by Issac on 2021/05/19.
//

import Foundation

class CalendarBusinessCenter {
    typealias CalendarList = [YearMonthMetadata: [Day]]
    typealias YearMonthDay = (yearMonthMetadata: YearMonthMetadata, day: Day)
    var calendarList: CalendarList
    var durationFieldHandler: (() -> ())?
    var firstSelected: Selected? {
        didSet {
            durationFieldHandler?()
        }
    }
    var secondSelected: Selected? {
        didSet {
            durationFieldHandler?()
        }
    }
    
    init() {
        self.calendarList = CalendarList()
        makeCalendarList()
    }
    //MARK: - make CalendarList
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
                if dayIndex < yearMonthMetadata.startDay - 1 {
                    let day = Day(day: 0, index: dayIndex, invisible: true, isFirstSelected: false, isSecondSelected: false, midRange: false)
                    calendarList[yearMonthMetadata, default: [Day]()].append(day)
                } else {
                    let dayNum = dayIndex - yearMonthMetadata.startDay + 2
                    let invisible = isInvisible(year: yearMonthMetadata.year, month: yearMonthMetadata.month.rawValue, day: dayNum)
                    let day = Day(day: dayNum, index: dayIndex, invisible: invisible, isFirstSelected: false, isSecondSelected: false, midRange: false)
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
    
    //MARK: - Selected Business Logic
    
    func selectedLogic(selected: Selected) {
        guard let day = findDay(selected: selected) else { return }
        guard !day.invisible else {
            initSelectedValue()
            self.firstSelected = nil
            self.secondSelected = nil
            return
        }
        guard let firstSelected = self.firstSelected else {
            assignSelected(selected: selected, whereSelected: .first)
            return
        }
        
        if isBeforeSelected(selected: selected) {
            selectFirst(selected: selected)
        } else if self.firstSelected != nil && self.secondSelected != nil {
            selectFirst(selected: selected)
        } else {
            initSelectedValue()
            self.assignSelected(selected: firstSelected, whereSelected: .first)
            self.assignSelected(selected: selected, whereSelected: .second)
            self.selectMidRange(first: firstSelected, second: selected)
        }
    }
    
    enum WhereSelected {
        case first
        case second
    }
    
    var convertCheckInCheckOutText: String {
        guard let firstSelected = self.firstSelected else { return "" }
        guard let firstMonth = self.calendarList.first(where: { $0.key.index == firstSelected.section }) else { return "" }
        let firstDay = firstMonth.value[firstSelected.row]
        guard let secondSelected = self.secondSelected else { return "\(firstMonth.key.month.rawValue)월 \(firstDay.day)일" }
        guard let secondMonth = self.calendarList.first(where: { $0.key.index == secondSelected.section }) else { return "" }
        let secondDay = secondMonth.value[secondSelected.row]
        return "\(firstMonth.key.month.rawValue)월 \(firstDay.day)일 - \(secondMonth.key.month.rawValue)월 \(secondDay.day)일"
    }
    
    func isFirstSelected() -> Bool {
        return self.firstSelected != nil
    }
    
    func isBeforeSelected(selected: Selected) -> Bool {
        guard let firstSelected = self.firstSelected else { return false }
        if firstSelected.section >= selected.section && firstSelected.row > selected.row {
            return true
        }
        return false
    }
    
    func selectFirst(selected: Selected) {
        initSelectedValue()
        self.assignSelected(selected: selected, whereSelected: .first)
        secondSelected = nil
    }
    
    func initSelectedValue() {
        for yearMonthMetadata in self.calendarList.keys {
            self.calendarList[yearMonthMetadata] =
                self.calendarList[yearMonthMetadata]?.map({ day in
                    day.initSelectedValue()
                    return day
                })
        }
    }
    
    func findDay(selected: Selected) -> Day? {
        guard let month = self.calendarList.first(where: { $0.key.index == selected.section }) else { return nil }
        let days = month.value
        return days[selected.row]
    }
    
    func assignSelected(selected: Selected, whereSelected: WhereSelected) {
        guard let day = findDay(selected: selected) else { return }
        switch whereSelected {
        case .first:
            self.firstSelected = selected
            day.isFirstSelected = true
        case .second:
            self.secondSelected = selected
            day.isSecondSelected = true
        }
    }
    
    func isAfterSelected(selected: Selected) -> Bool {
        guard let firstSelected = self.firstSelected else { return false }
        if firstSelected.section < selected.section && firstSelected.row < selected.row {
            return true
        } else {
            selectFirst(selected: selected)
            return false
        }
    }
    
    func selectMidRange(first: Selected, second: Selected) {
        for yearMonthMetadata in self.calendarList.keys {
            guard let days = self.calendarList[yearMonthMetadata] else { return }
            for dayMetadata in days {
                if first.section == second.section && first.section == yearMonthMetadata.index {
                    if first.row < dayMetadata.index && second.row > dayMetadata.index {
                        dayMetadata.midRange = true
                    }
                } else if (yearMonthMetadata.index == first.section && dayMetadata.index > first.row) ||
                            (yearMonthMetadata.index == second.section && dayMetadata.index < second.row) && !dayMetadata.invisible {
                    dayMetadata.midRange = true
                }
            }
        }
    }
}
