//
//  SearchResult.swift
//  airbnb
//
//  Created by Issac on 2021/06/02.
//

import Foundation

struct SearchResult: Codable {
    var documents: [SearchResultDocuments]
    var meta: SearchResultMeta?
}
