//
//  CalendarCell.swift
//  airbnb
//
//  Created by Issac on 2021/05/18.
//

import UIKit

protocol SelectedRecognizable: class {
    func didSelectedFirstDayAndSecondDay() -> Bool
}

class CalendarCell: UICollectionViewCell {
    @IBOutlet private var circleBackgroundView: UIView!
    @IBOutlet private weak var dayLabel: UILabel!
    @IBOutlet private weak var leftHalfView: UIView!
    @IBOutlet private weak var rightHalfView: UIView!
    weak var delegate: SelectedRecognizable?
    override func awakeFromNib() {
        super.awakeFromNib()
    }
    
    override func prepareForReuse() {
        super.prepareForReuse()
        self.dayLabel.font = .systemFont(ofSize: 14, weight: .bold)
        self.dayLabel.textColor = .black
        self.rightHalfView.backgroundColor = .white
        self.circleBackgroundView.backgroundColor = .white
        self.circleBackgroundView.layer.cornerRadius = 0
        self.rightHalfView.backgroundColor = .clear
        self.leftHalfView.backgroundColor = .clear
    }
    
    func configure(dayMetadata: Day) {
        selected(dayMetaData: dayMetadata)
        setText(dayMetadata: dayMetadata)
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
            self.dayLabel.textColor = .white
            self.circleBackgroundView.backgroundColor = .black
            self.circleBackgroundView.layer.masksToBounds = false
            self.circleBackgroundView.layer.cornerRadius = self.frame.width / 2
        }
        
        if dayMetaData.midRange {
            self.circleBackgroundView.backgroundColor = .systemGray5
        }
        
        guard let delegate = delegate else { return }
        if delegate.didSelectedFirstDayAndSecondDay() {
            if dayMetaData.isFirstSelected {
                self.rightHalfView.backgroundColor = .systemGray5
            } else {
                self.leftHalfView.backgroundColor = .systemGray5
            }
        }
    }

}
