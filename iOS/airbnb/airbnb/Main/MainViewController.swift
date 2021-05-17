//
//  ViewController.swift
//  airbnb
//
//  Created by Issac on 2021/05/17.
//

import UIKit

class MainViewController: UIViewController {
    var coordinator: MainCoordinator?
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
    }
    @IBAction func touchNext(_ sender: UIButton) {
        coordinator?.showSearch()
    }
}

extension MainViewController: Storyboarded { }
