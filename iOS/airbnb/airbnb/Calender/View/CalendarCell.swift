//
//  CalendarCell.swift
//  airbnb
//
//  Created by Issac on 2021/05/18.
//

import UIKit

class CalendarCell: UICollectionViewCell {
    @IBOutlet weak var dayLabel: UILabel!
    override func awakeFromNib() {
        super.awakeFromNib()
    }
    
    override func prepareForReuse() {
        super.prepareForReuse()
        self.dayLabel.font = .systemFont(ofSize: 14, weight: .bold)
        self.dayLabel.textColor = .black
    }
    
    func configure(dayMetadata: Day) {
        self.dayLabel.text = "\(dayMetadata.day)" == "0" ? "" : "\(dayMetadata.day)"
        if dayMetadata.invisible {
            self.dayLabel.font = .systemFont(ofSize: 14, weight: .regular)
            self.dayLabel.textColor = .systemGray2
        }
    }

}
