//
//  Address.swift
//  airbnb
//
//  Created by Issac on 2021/05/21.
//

import Foundation

class SelectInfo: Hashable {
    enum DateKind {
        case startDate
        case endDate
    }
    
    private(set) var address: String
    private(set) var x: Double?
    private(set) var y: Double?
    private(set) var startDate: Date?
    private(set) var endDate: Date?
    private(set) var minPrice: Int?
    private(set) var maxPrice: Int?
    private(set) var priceText: String?
    private(set) var adult: Int?
    private(set) var kid: Int?
    private(set) var baby: Int?
    var persons: Int {
        return (adult ?? 0) + (kid ?? 0) + (baby ?? 0)
    }
    
    init(name: String, x: Double? = nil, y: Double? = nil, startDate: Date? = nil, endDate: Date? = nil, minPrice: Int? = nil, maxPrice: Int? = nil, priceText: String? = nil, adult: Int? = nil, kid: Int? = nil, baby: Int? = nil) {
        self.address = name
        self.x = x
        self.y = y
        self.startDate = startDate
        self.endDate = endDate
        self.minPrice = minPrice
        self.maxPrice = maxPrice
        self.priceText = priceText
        self.adult = adult
        self.kid = kid
        self.baby = baby
    }
    
    static func == (lhs: SelectInfo, rhs: SelectInfo) -> Bool {
        return lhs.address == rhs.address
    }
    
    func hash(into hasher: inout Hasher) {
        hasher.combine(self.address)
    }
    
    var displayDuration: String {
        guard let startDateComponents = startDate?.get(.month, .day),
              let startMonth = startDateComponents.month,
              let startDay = startDateComponents.day else { return "" }
        guard let endDateComponents = endDate?.get(.month, .day),
              let endMonth = endDateComponents.month,
              let endDay = endDateComponents.day else { return "\(startMonth)월 \(startDay)일" }
        return "\(startMonth)월 \(startDay)일 - \(endMonth)월 \(endDay)일 "
    }

    func setDate(startDate: Date?, endDate: Date?) {
        self.startDate = startDate
        self.endDate = endDate
    }
    
    func setPrice(min: Int, max: Int, text: String) {
        self.minPrice = min
        self.maxPrice = max
        self.priceText = text
    }
    
    func setPersons(adult: Int, kid: Int, baby: Int) {
        self.adult = adult
        self.kid = kid
        self.baby = baby
    }
    
    func setcoordinator(x: Double?, y: Double?) {
        self.x = x
        self.y = y
    }
}
