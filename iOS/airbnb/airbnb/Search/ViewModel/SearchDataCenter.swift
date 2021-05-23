//
//  SearchDataCenter.swift
//  airbnb
//
//  Created by Issac on 2021/05/21.
//

import Foundation

class SearchDataCenter {
    var addressBook: [SelectInfo]
    
    init() {
        self.addressBook = [SelectInfo]()
    }
    
    func makeDummyAddressBook() -> [SelectInfo] {
        var fakeAddressBook = [SelectInfo]()
        let fakeAdministrativeDistrict = ["원당", "양재", "영주", "용산", "양진", "양조동", "양진", "영산", "울진", "울주", "울산", "옹진", "양화", "영선", "서울", "분당", "합정", "복정", "성남", "모란", "성북", "노원", "석계", "회기", "창동", "도봉"]
        for city in fakeAdministrativeDistrict {
            fakeAddressBook.append(SelectInfo(name: city))
        }
        return fakeAddressBook
    }
}
