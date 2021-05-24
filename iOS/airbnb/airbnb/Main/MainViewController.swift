//
//  ViewController.swift
//  airbnb
//
//  Created by Issac on 2021/05/17.
//

import UIKit

class MainViewController: UIViewController {
    @IBOutlet private weak var searchBar: UISearchBar!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        self.removeBorderOfSearchBar()
    }
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        self.navigationItem.title = "메인 화면"
        self.navigationController?.setNavigationBarHidden(true, animated: false)
    }
    
    private func removeBorderOfSearchBar() {
        self.searchBar.layer.borderWidth = 1
        self.searchBar.layer.borderColor = UIColor.systemGray6.cgColor
    }
}

extension MainViewController: UISearchBarDelegate {
    func searchBarTextDidBeginEditing(_ searchBar: UISearchBar) {
        searchBar.resignFirstResponder()
        guard let vc = self.storyboard?.instantiateViewController(identifier: SearchViewController.className) as? SearchViewController else { return }
        self.navigationController?.pushViewController(vc, animated: true)
    }
}
