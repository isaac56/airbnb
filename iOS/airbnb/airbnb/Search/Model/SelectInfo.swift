//
//  Address.swift
//  airbnb
//
//  Created by Issac on 2021/05/21.
//

import Foundation

class SelectInfo: Hashable {
    let address: String
    let startDate: Date?
    let endDate: Date?
    let price: Int?
    
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
