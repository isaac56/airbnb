//
//  RoadAddress.swift
//  airbnb
//
//  Created by Issac on 2021/06/02.
//

import Foundation

struct RoadAddress: Codable {
    var addressName: String
    var region1DepthName: String
    var region2DepthName: String
    var region3DepthName: String
    var roadName: String
    var undergroundYn: String
    var mainBuildingNo: String
    var subBuildingNo: String
    var buildingName: String
    var zoneNo: String
    var x: String
    var y: String
    
    enum CodingKeys: String, CodingKey {
        case x, y
        case addressName = "address_name"
        case region1DepthName = "region_1depth_name"
        case region2DepthName = "region_2depth_name"
        case region3DepthName = "region_3depth_name"
        case roadName = "road_name"
        case undergroundYn = "underground_yn"
        case mainBuildingNo = "main_building_no"
        case subBuildingNo = "sub_building_no"
        case buildingName = "building_name"
        case zoneNo = "zone_no"
    }
}
