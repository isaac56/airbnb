//
//  BottomInfoView.swift
//  airbnb
//
//  Created by Issac on 2021/05/20.
//

import UIKit

class BottomInfoView: UIView {
    @IBOutlet weak var location: UILabel!
    @IBOutlet weak var duration: UILabel!
    @IBOutlet weak var price: UILabel!
    @IBOutlet weak var persons: UILabel!
    @IBOutlet weak var nextButton: UIButton!
    @IBOutlet weak var clearButton: UIButton!
    
    func setAutolayout(to view: UIView) {
        self.translatesAutoresizingMaskIntoConstraints = false
        self.topAnchor.constraint(equalTo: view.topAnchor).isActive = true
        self.bottomAnchor.constraint(equalTo: view.bottomAnchor).isActive = true
        self.leadingAnchor.constraint(equalTo: view.leadingAnchor).isActive = true
        self.trailingAnchor.constraint(equalTo: view.trailingAnchor).isActive = true
    }
    
    func drawTopBorder() {
        let topBorder: CALayer = CALayer()
        topBorder.frame = CGRect(x: 0.0, y: 0.0, width: self.frame.size.width, height: 1.0)
        topBorder.backgroundColor = UIColor.systemGray2.cgColor
        self.layer.addSublayer(topBorder)
    }
    
    func clearButtonsAction() {
        nextButton.removeTarget(nil, action: nil, for: .allEvents)
        clearButton.removeTarget(nil, action: nil, for: .allEvents)
    }
    
    func SetWhetherEnableButtonOrNot(isWillEnable: Bool) {
        self.nextButton.isEnabled = isWillEnable
        self.clearButton.isEnabled = isWillEnable
        
        if isWillEnable {
            self.nextButton.alpha = 1
            self.clearButton.alpha = 1
        } else {
            self.nextButton.alpha = 0.5
            self.clearButton.alpha = 0.5
        }
    }
    
    func writePriceLabel(of string: String) {
        self.duration.text = string
    }
}
