//
//  CalenderViewController.swift
//  airbnb
//
//  Created by Issac on 2021/05/18.
//

import UIKit

class CalendarViewController: UIViewController {
    @IBOutlet private weak var calendarCollectionView: UICollectionView!
    @IBOutlet private weak var bottomSection: UIView!
    private var calendarBusinessCenter: CalendarBusinessCenter!
    private var selectInfo: SelectInfo!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        self.navigationItem.title = "숙소 찾기"
        self.calendarBusinessCenter = CalendarBusinessCenter()
        collectionViewRegisterNib()
        passingSearchInfoToChildViewController()
        defineHandlerOfSearchInfoViewController()
    }
    
    private func passingSearchInfoToChildViewController() {
        guard let bottomSearchInfoVC = self.children[0] as? BottomSearchInfoViewController else { return }
        bottomSearchInfoVC.configureSearchInfo(self.selectInfo)
    }
    
    private func defineHandlerOfSearchInfoViewController() {
        guard let bottomSearchInfoVC = self.children[0] as? BottomSearchInfoViewController else { return }
        bottomSearchInfoVC.clearHandler = {
            self.calendarBusinessCenter.initSelectedValue()
            bottomSearchInfoVC.setWhetherEnableButtonOrNot(isWillEnable: false)
            bottomSearchInfoVC.writeDurationLabel(of: "")
            self.calendarCollectionView.reloadData()
        }
        
        bottomSearchInfoVC.nextHandler = {
            guard let vc = self.storyboard?.instantiateViewController(identifier: PriceViewController.className) as? PriceViewController else { return }
            vc.setSelectInfo(self.selectInfo)
            self.navigationController?.pushViewController(vc, animated: true)
        }
        
        self.calendarBusinessCenter.durationFieldHandler = {
            let startDate = self.calendarBusinessCenter.convertDateToSelected(whereSelected: .first)
            let endDate = self.calendarBusinessCenter.convertDateToSelected(whereSelected: .second)
            self.selectInfo.setDate(startDate: startDate, endDate: endDate)
            bottomSearchInfoVC.writeDurationLabel(of: self.selectInfo.displayDuration)
            self.calendarBusinessCenter.firstSelected == nil
                ? bottomSearchInfoVC.setWhetherEnableButtonOrNot(isWillEnable: false)
                : bottomSearchInfoVC.setWhetherEnableButtonOrNot(isWillEnable: true)
        }
    }
    
    func setSelectInfo(_ info: SelectInfo) {
        self.selectInfo = info
    }
    
    private func collectionViewRegisterNib() {
        let nib = UINib(nibName: CalendarCell.className, bundle: nil)
        self.calendarCollectionView.register(nib, forCellWithReuseIdentifier: CalendarCell.className)
        let headerNib = UINib(nibName: CalendarYearMonthHeader.className, bundle: nil)
        self.calendarCollectionView.register(headerNib, forSupplementaryViewOfKind: UICollectionView.elementKindSectionHeader, withReuseIdentifier: CalendarYearMonthHeader.className)
    }
}

extension CalendarViewController: UICollectionViewDelegateFlowLayout {
    func collectionView(_ collectionView: UICollectionView, layout collectionViewLayout: UICollectionViewLayout, sizeForItemAt indexPath: IndexPath) -> CGSize {
        return CGSize(width: floor(self.view.frame.width / 7.0), height: self.view.frame.width / 7.0)
    }
}

extension CalendarViewController: UICollectionViewDelegate {
    func collectionView(_ collectionView: UICollectionView, didSelectItemAt indexPath: IndexPath) {
        self.calendarBusinessCenter.selectedLogic(selected: Selected(section: indexPath.section, row: indexPath.row))
        collectionView.reloadData()
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
        cell.delegate = self
        cell.configure(dayMetadata: days[indexPath.row])
        return cell
    }
}

extension CalendarViewController: SelectedRecognizable {
    func didSelectedFirstDayAndSecondDay() -> Bool {
        return self.calendarBusinessCenter.firstSelected != nil && self.calendarBusinessCenter.secondSelected != nil
    }
}
