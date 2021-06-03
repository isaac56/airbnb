//
//  SearchDataCenter.swift
//  airbnb
//
//  Created by Issac on 2021/05/21.
//

import Foundation
import SwiftyJSON

class SearchDataCenter {
    @Published private(set) var addressBook: [SelectInfo]
    private(set) var networkCenter: NetworkingCenter
    
    init() {
        self.addressBook = [SelectInfo]()
        self.networkCenter = NetworkingCenter()
    }
    
    func requestQuery(query: String) {
        self.networkCenter.request(query: query) { (result) in
            switch result {
            case .success(let searchResult):
                self.addressBook = self.makeSelectInfo(searchResult: searchResult)
            case .failure(let error):
                print(error)
            }
        }
    }
    
    func makeSelectInfo(searchResult: SearchResult) -> [SelectInfo] {
        var addressBook = [SelectInfo]()
        for document in searchResult.documents {
            let selectInfo = SelectInfo(name: document.addressName)
            selectInfo.setcoordinator(x: Double(document.x), y: Double(document.y))
            addressBook.append(selectInfo)
        }
        return addressBook
    }
}
