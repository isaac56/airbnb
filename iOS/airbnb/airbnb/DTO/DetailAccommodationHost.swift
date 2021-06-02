//
//  DetailAccommodationHost.swift
//  airbnb
//
//  Created by Issac on 2021/06/01.
//

import Foundation

struct DetailAccommodationHost: Codable {
    var id: Int
    var email: String
    var nickName: String
    
    enum CodingKeys: String, CodingKey {
        case id, email
        case nickName = "nick_name"
    }
}
