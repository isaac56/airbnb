//
//  CalenderViewController.swift
//  airbnb
//
//  Created by Issac on 2021/05/18.
//

import UIKit

class CalendarViewController: UIViewController, Storyboarded {
    @IBOutlet weak var calendarCollectionView: UICollectionView!
    var coordinator: MainCoordinator!
    var currentDateStartDay: Int!
    var calendarBusinessCenter: CalendarBusinessCenter!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        self.calendarBusinessCenter = CalendarBusinessCenter()
        collectionViewRegisterNib()
    }
    
    func collectionViewRegisterNib() {
        let nib = UINib(nibName: CalendarCell.className, bundle: nil)
        self.calendarCollectionView.register(nib, forCellWithReuseIdentifier: CalendarCell.className)
        let headerNib = UINib(nibName: CalendarYearMonthHeader.className, bundle: nil)
        self.calendarCollectionView.register(headerNib, forSupplementaryViewOfKind: UICollectionView.elementKindSectionHeader, withReuseIdentifier: CalendarYearMonthHeader.className)
    }
}

extension CalendarViewController: UICollectionViewDelegateFlowLayout {
    func collectionView(_ collectionView: UICollectionView, layout collectionViewLayout: UICollectionViewLayout, sizeForItemAt indexPath: IndexPath) -> CGSize {
        return CGSize(width: self.view.frame.width / 8.0, height: 50)
    }
}

extension CalendarViewController: UICollectionViewDelegate {
    func collectionView(_ collectionView: UICollectionView, didSelectItemAt indexPath: IndexPath) {
        //MARK: - selected
    }
    
    func collectionView(_ collectionView: UICollectionView, layout collectionViewLayout: UICollectionViewLayout, referenceSizeForHeaderInSection section: Int) -> CGSize {
        return CGSize(width: self.view.frame.width, height: 70)
    }
}

extension CalendarViewController: UICollectionViewDataSource {
    func numberOfSections(in collectionView: UICollectionView) -> Int {
        return self.calendarBusinessCenter.calendarList.keys.count
    }
    
    func collectionView(_ collectionView: UICollectionView, viewForSupplementaryElementOfKind kind: String, at indexPath: IndexPath) -> UICollectionReusableView {
        switch kind {
        case UICollectionView.elementKindSectionHeader:
            guard let header = collectionView.dequeueReusableSupplementaryView(ofKind: kind, withReuseIdentifier: CalendarYearMonthHeader.className, for: indexPath) as? CalendarYearMonthHeader else { return UICollectionViewCell() }
            guard let month = self.calendarBusinessCenter.calendarList.first(where: { $0.key.index == indexPath.section }) else { return UICollectionViewCell() }
            header.yearMonthLabel.text = "\(month.key.year)년 \(month.key.month.rawValue)월"
            return header
        default:
            return UICollectionViewCell()
        }
    }

    func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
        guard let month = self.calendarBusinessCenter.calendarList.first(where: { $0.key.index == section }) else { return 0 }
        return month.value.count
    }

    func collectionView(_ collectionView: UICollectionView, cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {
        guard let cell = collectionView.dequeueReusableCell(withReuseIdentifier: CalendarCell.className, for: indexPath) as? CalendarCell else { return UICollectionViewCell() }
        guard let days = self.calendarBusinessCenter.calendarList.first(where: { $0.key.index == indexPath.section })?.value else { return cell }
        cell.configure(dayMetadata: days[indexPath.row])
        return cell
    }
}
