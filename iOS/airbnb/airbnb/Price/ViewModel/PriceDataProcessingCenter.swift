//
//  PriceDataProcessingCenter.swift
//  airbnb
//
//  Created by Issac on 2021/05/27.
//

import Foundation

class PriceDataProcessingCenter {
    private(set) var minPriceValue: Int
    private(set) var maxPriceValue: Int
    var pricePassingHandler: ((String) -> ())?
    private(set) var rowPrice: [Int] {
        didSet {
            let priceText = processPriceData()
            pricePassingHandler?(priceText)
        }
    }
    var minPrice: Int {
        return rowPrice[0]
    }
    
    var maxPrice: Int {
        return rowPrice[1]
    }
    
    init(minPriceValue: Int, maxPriceValue: Int) {
        self.rowPrice = [0, 0]
        self.minPriceValue = minPriceValue
        self.maxPriceValue = maxPriceValue
    }
    
    private func processPriceData() -> String {
        let processingMinPrice = decimalWon(value: minPrice)
        let processingMaxPrice = decimalWon(value: maxPrice)
        return "\(processingMinPrice) - \(processingMaxPrice)"
    }
    
    private func decimalWon(value: Int) -> String {
        let numberFormatter = NumberFormatter()
        numberFormatter.numberStyle = .decimal
        let result = "₩"  + numberFormatter.string(from: NSNumber(value: value))!
        if value == maxPriceValue {
            return "\(result)+"
        } else {
            return result
        }
    }
    
    func setRowPrice(prices: [Int]) {
        self.rowPrice = prices
    }
    
    func initRowPrice() {
        self.rowPrice = [minPriceValue, maxPriceValue]
    }
}

