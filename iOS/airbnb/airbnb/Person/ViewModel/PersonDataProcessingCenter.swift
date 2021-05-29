//
//  PersonDataProcessingCenter.swift
//  airbnb
//
//  Created by Issac on 2021/05/29.
//

import Foundation

class PersonDataProcessingCenter {
    var adult: Int {
        didSet {
            changeAdultHandler?()
            changePersons?()
        }
    }
    var kid: Int {
        didSet {
            changeKidHandler?()
            changePersons?()
        }
    }
    var baby: Int {
        didSet {
            changeBabyHandler?()
            changePersons?()
        }
    }
    var persons: Int {
        return adult + kid + baby
    }
    var changeAdultHandler: (() -> ())?
    var changeKidHandler: (() -> ())?
    var changeBabyHandler: (() -> ())?
    var changePersons: (() -> ())?
    
    enum Person: Int {
        case adult = 0
        case kid
        case baby
    }
    
    init() {
        self.adult = 0
        self.kid = 0
        self.baby = 0
    }
    
    func increase(of person: Person) {
        switch person {
        case .adult: self.adult += 1
        case .kid: self.kid += 1
        case .baby: self.baby += 1
        }
    }
    
    func decrease(of person: Person) {
        switch person {
        case .adult: self.adult -= self.adult == 0 ? 0 : 1
        case .kid: self.kid -= self.kid == 0 ? 0 : 1
        case .baby: self.baby -= self.baby == 0 ? 0 : 1
        }
    }
    
    func initPersonsCount() {
        self.adult = 0
        self.baby = 0
        self.kid = 0
    }
}
