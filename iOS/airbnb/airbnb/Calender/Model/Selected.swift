//
//  Selected.swift
//  airbnb
//
//  Created by Issac on 2021/05/20.
//

import Foundation

class Selected {
    private(set) var section: Int
    private(set) var row: Int
    
    init(section: Int, row: Int) {
        self.section = section
        self.row = row
    }
}
