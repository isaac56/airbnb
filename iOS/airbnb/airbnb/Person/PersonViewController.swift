//
//  PersonViewController.swift
//  airbnb
//
//  Created by Issac on 2021/05/27.
//

import UIKit

class PersonViewController: UIViewController {
    var selectInfo: SelectInfo!
    private var personDataProcessingCenter: PersonDataProcessingCenter!
    @IBOutlet var minusButtons: [UIButton]!
    @IBOutlet var plusButtons: [UIButton]!
    @IBOutlet var personLabels: [UILabel]!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        self.navigationItem.title = "숙소 찾기"
        self.passingSearchInfoToChildViewController()
        self.personDataProcessingCenter = PersonDataProcessingCenter()
        self.enterPersonInSearchInfo()
        self.bind()
        self.addActions()
    }
    
    private func addActions() {
        for index in 0..<self.plusButtons.count {
            guard let person = PersonDataProcessingCenter.Person(rawValue: index) else { return }
            plusButtons[index].addAction(UIAction(handler: { (_) in
                self.personDataProcessingCenter.increase(of: person)
            }), for: .touchUpInside)
            
            minusButtons[index].addAction(UIAction(handler: { (_) in
                self.personDataProcessingCenter.decrease(of: person)
            }), for: .touchUpInside)
        }
        
    }
    
    private func bind() {
        guard let bottomSearchInfoVC = self.children[0] as? BottomSearchInfoViewController else { return }
        self.personDataProcessingCenter.changePersons = {
            let clearButtonEnable = self.personDataProcessingCenter.persons >= 1 ? true : false
            bottomSearchInfoVC.setWhetherEnableButtonOrNot(isWillEnable: clearButtonEnable)
            bottomSearchInfoVC.writePersonsLabel(text: "게스트 \(self.personDataProcessingCenter.persons)명")
            self.enterPersonInSearchInfo()
        }
        
        self.personDataProcessingCenter.changeAdultHandler = {
            let adultCount = self.personDataProcessingCenter.adult
            self.personLabels[0].text = "\(adultCount)"
            self.minusButtons[0].isEnabled = adultCount == 0 ? false : true
            
        }

        self.personDataProcessingCenter.changeKidHandler = {
            let kidCount = self.personDataProcessingCenter.kid
            self.personLabels[1].text = "\(kidCount)"
            self.minusButtons[1].isEnabled = kidCount == 0 ? false : true
        }
        
        self.personDataProcessingCenter.changeBabyHandler = {
            let babyCount = self.personDataProcessingCenter.baby
            self.personLabels[2].text = "\(babyCount)"
            self.minusButtons[2].isEnabled = babyCount == 0 ? false : true
        }
        bottomSearchInfoVC.clearHandler = {
            bottomSearchInfoVC.writePersonsLabel(text: "")
            bottomSearchInfoVC.setWhetherEnableButtonOrNot(isWillEnable: false)
            self.personDataProcessingCenter.initPersonsCount()
        }

        bottomSearchInfoVC.nextHandler = {
            guard let vc = self.storyboard?.instantiateViewController(identifier: PersonViewController.className) as? PersonViewController else { return }
            vc.setSelectInfo(self.selectInfo)
            self.navigationController?.pushViewController(vc, animated: true)
        }
    }
    
    func setSelectInfo(_ info: SelectInfo) {
        self.selectInfo = info
    }
    
    private func enterPersonInSearchInfo() {
        selectInfo.setPersons(adult: self.personDataProcessingCenter.adult,
                              kid: self.personDataProcessingCenter.kid,
                              baby: self.personDataProcessingCenter.baby)
    }
    
    private func passingSearchInfoToChildViewController() {
        guard let bottomSearchInfoVC = self.children[0] as? BottomSearchInfoViewController else { return }
        bottomSearchInfoVC.configureSearchInfo(self.selectInfo)
    }
}
