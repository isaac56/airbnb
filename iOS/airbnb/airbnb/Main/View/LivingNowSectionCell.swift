//
//  LivingNowSectionCell.swift
//  airbnb
//
//  Created by Issac on 2021/05/25.
//

import UIKit

class LivingNowSectionCell: UICollectionViewCell {
    @IBOutlet weak var livingNowImageView: UIImageView!
    @IBOutlet weak var livingNowDescription: UILabel!
    override func awakeFromNib() {
        super.awakeFromNib()
        self.setImageLayer()
    }
    
    func setImageLayer() {
        self.livingNowImageView.layer.cornerRadius = self.livingNowImageView.frame.width / 25
        self.livingNowImageView.layer.masksToBounds = true
    }
    
    func configure(imageName: String, description: String) {
        self.livingNowImageView.image = UIImage(named: imageName)
        self.livingNowDescription.text = description
    }
}
