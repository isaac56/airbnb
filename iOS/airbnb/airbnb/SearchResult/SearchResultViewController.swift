//
//  SearchResultViewController.swift
//  airbnb
//
//  Created by Issac on 2021/05/29.
//

import UIKit

class SearchResultViewController: UIViewController {
    var selectInfo: SelectInfo!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        self.navigationItem.title = "숙소 찾기"
    }
    
    func setSelectInfo(_ info: SelectInfo) {
        self.selectInfo = info
    }
    
}
