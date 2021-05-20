//
//  Day.swift
//  airbnb
//
//  Created by Issac on 2021/05/19.
//

import Foundation

class Day {
    let day: Int
    let index: Int
    let invisible: Bool
    var isFirstSelected: Bool
    var isSecondSelected: Bool
    var midRange: Bool
    
    init(day: Int, index: Int, invisible: Bool, isFirstSelected: Bool, isSecondSelected: Bool, midRange: Bool) {
        self.day = day
        self.index = index
        self.invisible = invisible
        self.isFirstSelected = isFirstSelected
        self.isSecondSelected = isSecondSelected
        self.midRange = midRange
    }
    
    func initSelectedValue() {
        self.isFirstSelected = false
        self.isSecondSelected = false
        self.midRange = false
    }
}
