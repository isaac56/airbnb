//
//  MapResultViewModel.swift
//  airbnb
//
//  Created by Issac on 2021/06/03.
//

import Foundation

class MapResultViewModel {
    var selectInfo: SelectInfo
    var networkingCenter: NetworkingCenter
    @Published var searchByRegion: [SearchByRegion]
    
    init(selectInfo: SelectInfo) {
        self.selectInfo = selectInfo
        self.networkingCenter = NetworkingCenter()
        self.searchByRegion = [SearchByRegion]()
    }
    
    func requestAccommodation() {
        self.networkingCenter.searchRequest(selectInfo: selectInfo) { (result) in
            switch result {
            case .success(let searchByRegionData):
                self.searchByRegion = searchByRegionData.data
            case .failure(let error):
                print(error)
            }
        }
    }
    
    func stringDailyFeeForResultCell(region: SearchByRegion) -> String {
        return "\(region.stringTotalFee()) / 박"
    }
}
