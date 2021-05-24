//
//  CalendarCell.swift
//  airbnb
//
//  Created by Issac on 2021/05/18.
//

import UIKit

class CalendarCell: UICollectionViewCell {
    @IBOutlet private weak var dayLabel: UILabel!
    @IBOutlet private weak var leftHalfView: UIView!
    @IBOutlet private weak var rightHalfView: UIView!
    override func awakeFromNib() {
        super.awakeFromNib()
    }
    
    override func prepareForReuse() {
        super.prepareForReuse()
        self.dayLabel.font = .systemFont(ofSize: 14, weight: .bold)
        self.dayLabel.backgroundColor = .white
        self.dayLabel.textColor = .black
        self.rightHalfView.backgroundColor = .white
        self.layer.cornerRadius = 0
    }
    
    func configure(dayMetadata: Day) {
        selected(dayMetaData: dayMetadata)
        setText(dayMetadata: dayMetadata)
        halfBackGround(dayMetaData: dayMetadata)
    }
    
    func halfBackGround(dayMetaData: Day) {
        if dayMetaData.isFirstSelected {
            self.rightHalfView.backgroundColor = .systemGray2
        } else if dayMetaData.isSecondSelected {
            self.leftHalfView.backgroundColor = .systemGray2
        }
    }
    
    func setText(dayMetadata: Day) {
        self.dayLabel.text = "\(dayMetadata.day)" == "0" ? "" : "\(dayMetadata.day)"
        if dayMetadata.invisible {
            self.dayLabel.font = .systemFont(ofSize: 14, weight: .regular)
            self.dayLabel.textColor = .systemGray2
        }
    }
    
    func selected(dayMetaData: Day) {
        if dayMetaData.isFirstSelected || dayMetaData.isSecondSelected {
            self.dayLabel.backgroundColor = .black
            self.dayLabel.textColor = .white
            self.layer.cornerRadius = 5
            self.layer.masksToBounds = true
        }
        
        if dayMetaData.midRange {
            self.dayLabel.backgroundColor = .systemGray5
        }
    }

}
