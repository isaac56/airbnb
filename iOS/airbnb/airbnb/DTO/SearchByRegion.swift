//
//  SearchByRegion.swift
//  airbnb
//
//  Created by Issac on 2021/06/01.
//

import Foundation

struct SearchByRegion: Codable {
    var id: Int
    var name: String
    var totalFee: Int
    var dailyFee: Int
    var cleaningFee: Int
    var addressName: String
    var roadAddressName: String
    var region1: String
    var region2: String
    var region3: String
    var x: Double
    var y: Double
    var maxOfPeople: Int
    var type: String
    var numberOfRoom: Int
    var numberOfToilet: Int
    var options: [String]
    var tags: [String]
    var titleImageURL: String
    var wished: Bool
    
    enum CodingKeys: String, CodingKey {
        case id, name, region1, region2, region3, x, y, type, options, tags, wished
        case totalFee = "total_fee"
        case dailyFee = "daily_fee"
        case cleaningFee = "cleaning_fee"
        case addressName = "address_name"
        case roadAddressName = "road_address_name"
        case maxOfPeople = "max_of_people"
        case numberOfRoom = "number_of_room"
        case numberOfToilet = "number_of_toilet"
        case titleImageURL = "title_image"
    }
    
    func stringTotalFee() -> String {
        let numberFormatter = NumberFormatter()
        numberFormatter.numberStyle = .decimal
        let stringFee = "₩" + numberFormatter.string(from: NSNumber(value: self.totalFee))!
        return stringFee
    }
}
