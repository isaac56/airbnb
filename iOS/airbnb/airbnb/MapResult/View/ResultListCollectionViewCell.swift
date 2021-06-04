//
//  ResultListCollectionViewCell.swift
//  airbnb
//
//  Created by Issac on 2021/06/04.
//

import UIKit

class ResultListCollectionViewCell: UICollectionViewCell {
    @IBOutlet var view: UIView!
    @IBOutlet var roomImage: UIImageView!
    @IBOutlet var roomTitle: UILabel!
    @IBOutlet var priceLabel: UILabel!
    @IBOutlet var wishButton: UIButton!
    override func awakeFromNib() {
        super.awakeFromNib()
        self.setlayer()
    }
    
    func setlayer() {
        self.view.layer.cornerRadius = 10
        self.view.layer.masksToBounds = false
        
        self.view.layer.shadowColor = UIColor.black.cgColor
        self.view.layer.shadowOffset = .zero
        self.view.layer.shadowOpacity = 0.5
        self.view.layer.shadowRadius = 2
    }

}
