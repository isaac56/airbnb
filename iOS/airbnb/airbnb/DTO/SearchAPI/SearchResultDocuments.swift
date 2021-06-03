//
//  SearchResultDocuments.swift
//  airbnb
//
//  Created by Issac on 2021/06/02.
//

import Foundation

struct SearchResultDocuments: Codable {
    var address: Address
    var addressName: String
    var addressType: String?
    var roadAddress: RoadAddress?
    var x: String
    var y: String
    
    enum CodingKeys: String, CodingKey {
        case address, x, y
        case addressName = "address_name"
        case addressType = "address_type"
        case roadAddress = "road_address"
    }
}
