//
//  PersonViewController.swift
//  airbnb
//
//  Created by Issac on 2021/05/27.
//

import UIKit

class PersonViewController: UIViewController {
    var selectInfo: SelectInfo!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        self.navigationItem.title = "숙소 찾기"
        self.passingSearchInfoToChildViewController()
    }
    
    func setSelectInfo(_ info: SelectInfo) {
        self.selectInfo = info
    }
    
    private func passingSearchInfoToChildViewController() {
        guard let bottomSearchInfoVC = self.children[0] as? BottomSearchInfoViewController else { return }
        bottomSearchInfoVC.configureSearchInfo(self.selectInfo)
    }
}
