//
//  ArroundSectionCell.swift
//  airbnb
//
//  Created by Issac on 2021/05/25.
//

import UIKit

class ArroundSectionCell: UICollectionViewCell {
    @IBOutlet weak var cityImage: UIImageView!
    @IBOutlet weak var cityName: UILabel!
    @IBOutlet weak var distanceLabel: UILabel!
    
    override func awakeFromNib() {
        super.awakeFromNib()
    }
    
    func configure(cityImageName: String, cityName: String, distance: String) {
        self.cityImage.image = UIImage(named: cityImageName)
        self.cityName.text = cityName
        self.distanceLabel.text = "차로 \(distance) 거리"
    }

}
