//
//  DetailAccommodationBasicInfo.swift
//  airbnb
//
//  Created by Issac on 2021/06/01.
//

import Foundation

struct DetailAccommodationBasicInfo: Codable {
    var id: Int
    var name: String
    var totalFee: Int
    var dailyFee: Int
    var addressName: String
    var roadAddressName: String
    var x: Double
    var y: Double
    var maxOfPeople: Int
    var type: String
    var numberOfRoom: Int
    var numberOfToilet: Int
    var options: [String]
    var wished: Bool
    
    enum CodingKeys: String, CodingKey {
        case id, name, x, y, type, options, wished
        case totalFee = "total_fee"
        case dailyFee = "daily_fee"
        case addressName = "address_name"
        case roadAddressName = "road_address_name"
        case maxOfPeople = "max_of_people"
        case numberOfRoom = "number_of_room"
        case numberOfToilet = "number_of_toilet"
    }
}
