//
//  YearMonthMetadata.swift
//  airbnb
//
//  Created by Issac on 2021/05/19.
//

import Foundation

struct YearMonthMetadata: Hashable {
    enum Month: Int, CaseIterable {
        case january = 1
        case february
        case march
        case april
        case may
        case june
        case july
        case august
        case september
        case october
        case november
        case december
        
        var dayCount: Int {
            switch self {
            case .january, .march, .may, .july, .august, .october, .december: return 31
            case .february: return 28
            default: return 30
            }
        }
    }
    let index: Int
    let year: Int
    let month: Month
    let startDay: Int
}
