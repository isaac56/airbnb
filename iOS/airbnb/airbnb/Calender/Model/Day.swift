//
//  Day.swift
//  airbnb
//
//  Created by Issac on 2021/05/19.
//

import Foundation

class Day {
    enum SelectedPosition {
        case first
        case second
        case mid
    }
    
    private(set) var day: Int
    private(set) var index: Int
    private(set) var invisible: Bool
    private(set) var isFirstSelected: Bool
    private(set) var isSecondSelected: Bool
    private(set) var midRange: Bool
    
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
    
    func setSelectedDay(position: SelectedPosition, isSelect: Bool) {
        switch position {
        case .first: self.isFirstSelected = isSelect
        case .second: self.isSecondSelected = isSelect
        case .mid: self.midRange = isSelect
        }
    }
}
