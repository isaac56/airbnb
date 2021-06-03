//
//  SearchResultMeta.swift
//  airbnb
//
//  Created by Issac on 2021/06/02.
//

import Foundation

struct SearchResultMeta: Codable {
    var totalCount: Int
    var pageableCount: Int
    var isEnd: Bool
    
    enum CodingKeys: String, CodingKey {
        case totalCount = "total_count"
        case pageableCount = "pageable_count"
        case isEnd = "is_end"
    }
}
