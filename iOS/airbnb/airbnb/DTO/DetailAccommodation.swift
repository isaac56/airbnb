//
//  DetailAccommodation.swift
//  airbnb
//
//  Created by Issac on 2021/06/01.
//

import Foundation

struct DetailAccommodation: Codable {
    var basicInfo: DetailAccommodationBasicInfo
    var images: [String]
    var description: String
    var host: DetailAccommodationHost
    
    enum CodingKeys: String, CodingKey {
        case images, description, host
        case basicInfo = "basic_info"
    }
}
