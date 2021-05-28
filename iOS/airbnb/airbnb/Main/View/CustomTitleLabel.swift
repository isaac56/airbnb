//
//  CustomTitleLabel.swift
//  airbnb
//
//  Created by Issac on 2021/05/28.
//

import UIKit

class CustomTitleLabel: UILabel {
    override func drawText(in rect: CGRect) {
        let inset = UIEdgeInsets(top: 0, left: 20, bottom: 0, right: 0)
        super.drawText(in: rect.inset(by: inset))
    }
    
}
