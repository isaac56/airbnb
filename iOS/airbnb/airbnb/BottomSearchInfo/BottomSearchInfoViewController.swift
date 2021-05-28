//
//  BottomSearchInfoViewController.swift
//  airbnb
//
//  Created by Issac on 2021/05/24.
//

import UIKit

class BottomSearchInfoViewController: UIViewController {
    @IBOutlet weak var location: UILabel!
    @IBOutlet weak var duration: UILabel!
    @IBOutlet weak var price: UILabel!
    @IBOutlet weak var persons: UILabel!
    @IBOutlet weak var clearButton: UIButton!
    @IBOutlet weak var nextButton: UIButton!
    var clearHandler: (() -> ())?
    var nextHandler: (() -> ())?
    
    override func viewDidLoad() {
        super.viewDidLoad()
        self.drawTopBorder()
        self.setButtonAction()
    }
    
    func setButtonAction() {
        self.clearButton.addAction(UIAction(handler: { (_) in
            self.clearHandler?()
        }), for: .touchUpInside)
        self.nextButton.addAction(UIAction(handler: { (_) in
            self.nextHandler?()
        }), for: .touchUpInside)
    }
    
    func configureSearchInfo(_ info: SelectInfo) {
        self.location.text = info.address
        self.duration.text = info.displayDuration
        self.price.text = info.priceText == nil ? "" : info.priceText
        self.persons.text = info.persons == nil ? "" : "\(info.persons ?? 0)명"
    }
    
    func drawTopBorder() {
        let topBorder: CALayer = CALayer()
        topBorder.frame = CGRect(x: 0.0, y: 0.0, width: self.view.frame.size.width, height: 1.0)
        topBorder.backgroundColor = UIColor.systemGray2.cgColor
        self.view.layer.addSublayer(topBorder)
    }

    func clearButtonsAction() {
        nextButton.removeTarget(nil, action: nil, for: .allEvents)
        clearButton.removeTarget(nil, action: nil, for: .allEvents)
    }

    func setWhetherEnableButtonOrNot(isWillEnable: Bool) {
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
        self.clearButton.isEnabled = true
        self.nextButton.isEnabled = true
        self.price.text = string
    }

    func writeDurationLabel(of string: String) {
        self.duration.text = string
    }
}
