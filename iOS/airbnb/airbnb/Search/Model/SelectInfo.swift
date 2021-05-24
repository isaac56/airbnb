//
//  Address.swift
//  airbnb
//
//  Created by Issac on 2021/05/21.
//

import Foundation

class SelectInfo: Hashable {
    private(set) var address: String
    private(set) var startDate: Date?
    private(set) var endDate: Date?
    private(set) var price: Int?
    
    init(name: String, startDate: Date? = nil, endDate: Date? = nil, price: Int? = nil) {
        self.address = name
        self.startDate = startDate
        self.endDate = endDate
        self.price = price
    }
    
    static func == (lhs: SelectInfo, rhs: SelectInfo) -> Bool {
        return lhs.address == rhs.address
    }
    
    func hash(into hasher: inout Hasher) {
        hasher.combine(self.address)
    }
}
