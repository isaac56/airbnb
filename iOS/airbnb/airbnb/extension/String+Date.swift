//
//  String+Date.swift
//  airbnb
//
//  Created by Issac on 2021/05/24.
//

import Foundation

extension String {
    func getDate(format: String) -> Date? {
        let dateFormatter = DateFormatter()
        dateFormatter.dateFormat = format
        return dateFormatter.date(from: self)
    }
}
