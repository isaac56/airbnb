//
//  ResultCell.swift
//  airbnb
//
//  Created by Issac on 2021/05/21.
//

import UIKit

class ResultCell: UICollectionViewCell {
    @IBOutlet weak var pinImage: UIImageView!
    @IBOutlet weak var addressLabel: UILabel!
    
    override func awakeFromNib() {
        super.awakeFromNib()
        self.setPinImage()
    }
    
    private func setPinImage() {
        self.pinImage.layer.cornerRadius = 10
        self.pinImage.layer.masksToBounds = false
        self.pinImage.layer.borderWidth = 1
        self.pinImage.layer.borderColor = UIColor.black.cgColor
    }
    
}
