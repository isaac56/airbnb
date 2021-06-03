//
//  Address.swift
//  airbnb
//
//  Created by Issac on 2021/06/02.
//

import Foundation

struct Address: Codable {
    var addressName: String
    var region1DepthName: String
    var region2DepthName: String
    var region3DepthName: String
    var region3DepthHName: String
    var hCode: String
    var bCode: String
    var mountainYn: String
    var mainAddressNo: String
    var subAddressNo: String
    var zipCode: String?
    var x: String
    var y: String
    
    enum CodingKeys: String, CodingKey {
        case x, y
        case addressName = "address_name"
        case region1DepthName = "region_1depth_name"
        case region2DepthName = "region_2depth_name"
        case region3DepthName = "region_3depth_name"
        case region3DepthHName = "region_3depth_h_name"
        case hCode = "h_code"
        case bCode = "b_code"
        case mountainYn = "mountain_yn"
        case mainAddressNo = "main_address_no"
        case subAddressNo = "sub_address_no"
        case zipCode = "zip_code"
    }
}
