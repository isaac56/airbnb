//
//  Date.swift
//  airbnb
//
//  Created by Issac on 2021/05/19.
//

import Foundation

extension Date {
    // get component
    func get(_ components: Calendar.Component..., calendar: Calendar = Calendar.current) -> DateComponents {
        return calendar.dateComponents(Set(components), from: self)
    }
    
    // next month
    func findNextMonth(value: Int) -> Date? {
        return Calendar.current.date(byAdding: .month, value: value, to: self)
    }
    
    // 첫 번째 날은 몇 번째? 1~7
    var findFirstDayWeekDay: Int? {
        let calendar = Calendar.current
        guard let firstDayOfMonth = calendar.date(from: calendar.dateComponents([.year, .month], from: self)) else { return nil }
        return calendar.component(.weekday, from: firstDayOfMonth)
    }
}
