//
//  SearchViewController.swift
//  airbnb
//
//  Created by Issac on 2021/05/17.
//

import UIKit

class SearchViewController: UIViewController {
    @IBOutlet weak var searchBar: UISearchBar!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        self.searchBar.becomeFirstResponder()
        self.removeBorderOfSearchBar()
    }
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        self.navigationController?.setNavigationBarHidden(false, animated: true)
        self.navigationController?.navigationBar.shouldRemoveShadow(true)
        self.navigationController?.navigationBar.barTintColor = .systemGray6
    }
    
    private func removeBorderOfSearchBar() {
        self.searchBar.layer.borderWidth = 1
        self.searchBar.layer.borderColor = UIColor.systemGray6.cgColor
    }
}

