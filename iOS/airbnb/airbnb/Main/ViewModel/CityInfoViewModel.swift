//
//  CityInfoViewModel.swift
//  airbnb
//
//  Created by Issac on 2021/05/27.
//

import Foundation

class CityInfoViewModel {
    private(set) var cityImageNames: [String]
    private(set) var cityNames: [String]
    private(set) var distanceDescriptionBook: [String]
    
    init() {
        self.cityImageNames = [String]()
        self.cityNames = [String]()
        self.distanceDescriptionBook = [String]()
        self.makeDummyModel()
    }
    
    private func makeDummyModel() {
        self.cityImageNames = makeDummyCityImageNames()
        self.cityNames = makeDummyCityNames()
        self.distanceDescriptionBook = makeDummyDistanceDescriptionBook()
    }
    
    private func makeDummyCityImageNames() -> [String] {
        var dummyImageNames = [String]()
        for index in 0...7 {
            dummyImageNames.append("arroundImage\(index)")
        }
        return dummyImageNames
    }
    
    private func makeDummyCityNames() -> [String] {
        return ["서울", "광주", "의정부시", "수원시", "대구", "울산", "대전", "부천시"]
    }
    
    private func makeDummyDistanceDescriptionBook() -> [String] {
        return ["30분", "4시간", "30분", "45분", "3.5시간", "4.5시간", "2시간", "30분"]
    }
}
