//
//  MainSceneViewModel.swift
//  airbnb
//
//  Created by Issac on 2021/05/25.
//

import Foundation

class LivingNowViewModel {
    private(set) var livingNowImageNames: [String]
    private(set) var livingNowDescrptionBook: [String]
    
    init() {
        self.livingNowImageNames = [String]()
        self.livingNowDescrptionBook = [String]()
        self.makeDummyModel()
    }
    
    private func makeDummyModel() {
        self.livingNowImageNames = makeDummyLivingNowImageNames()
        self.livingNowDescrptionBook = makeDummyLivingNowDescrptionBook()
    }

    
    private func makeDummyLivingNowImageNames() -> [String] {
        return ["livingNowImage0", "livingNowImage1", "livingNowImage2"]
    }
    
    private func makeDummyLivingNowDescrptionBook() -> [String] {
        return ["자연생활을 만끽할 수 있는 숙소", "독특한 공간", "여기는 어디일까요?"]
    }
    
}
