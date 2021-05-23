//
//  SearchViewController.swift
//  airbnb
//
//  Created by Issac on 2021/05/17.
//

import UIKit

class SearchViewController: UIViewController {
    enum Section {
        case main
    }
    
    @IBOutlet weak var resultCollectionView: UICollectionView!
    var searchDataCenter = SearchDataCenter()
    lazy var dataSource = makeDataSource()
    private var searchController = UISearchController(searchResultsController: nil)
    typealias DataSource = UICollectionViewDiffableDataSource<Section, SelectInfo>
    typealias Snapshot = NSDiffableDataSourceSnapshot<Section, SelectInfo>
    
    override func viewDidLoad() {
        super.viewDidLoad()
        self.searchDataCenter.addressBook = self.searchDataCenter.makeDummyAddressBook()
        self.registerCell()
        self.setupSearchController()
        self.applySnapshot(query: nil)
    }
    
    private func setupSearchController() {
        self.navigationItem.searchController = self.searchController
        self.navigationItem.searchController?.searchResultsUpdater = self
        self.navigationItem.searchController?.obscuresBackgroundDuringPresentation = false
        self.navigationItem.title = "숙소 찾기"
        self.navigationItem.hidesSearchBarWhenScrolling = false
        self.navigationItem.searchController?.becomeFirstResponder()
    }
    
    private func makeDataSource() -> DataSource {
        DataSource.init(collectionView: self.resultCollectionView) { (collectionView, indexPath, address) -> UICollectionViewCell? in
            let cell = collectionView.dequeueReusableCell(withReuseIdentifier: ResultCell.className, for: indexPath) as? ResultCell
            cell?.addressLabel.text = address.address
            return cell
        }
    }
    
    private func applySnapshot(query: String?, animatingDifferences: Bool = true) {
        let addressBook = self.searchDataCenter.addressBook.filter({ $0.address.hasPrefix(query ?? "") })
        var snapshot = Snapshot()
        snapshot.appendSections([.main])
        snapshot.appendItems(addressBook, toSection: .main)
        dataSource.apply(snapshot, animatingDifferences: animatingDifferences)
    }
    
    private func registerCell() {
        let nib = UINib(nibName: ResultCell.className, bundle: nil)
        self.resultCollectionView.register(nib, forCellWithReuseIdentifier: ResultCell.className)
    }
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        self.navigationController?.setNavigationBarHidden(false, animated: true)
        self.navigationController?.navigationBar.shouldRemoveShadow(true)
        self.navigationController?.navigationBar.barTintColor = .systemGray6
    }
}

extension SearchViewController: UICollectionViewDelegate {
    func collectionView(_ collectionView: UICollectionView, didSelectItemAt indexPath: IndexPath) {
        let selectInfo = self.searchDataCenter.addressBook[indexPath.row]
        guard let vc = self.storyboard?.instantiateViewController(identifier: CalendarViewController.className) as? CalendarViewController else { return }
        vc.setSelectInfo(selectInfo)
        self.navigationController?.pushViewController(vc, animated: true)
    }
}

extension SearchViewController: UICollectionViewDelegateFlowLayout {
    func collectionView(_ collectionView: UICollectionView, layout collectionViewLayout: UICollectionViewLayout, sizeForItemAt indexPath: IndexPath) -> CGSize {
        return CGSize(width: self.view.bounds.width, height: 100.0)
    }
}

extension SearchViewController: UISearchResultsUpdating {
    func updateSearchResults(for searchController: UISearchController) {
        let query = searchController.searchBar.text
        //MARK: - 이 위치에 네트워크 get 추정
        self.applySnapshot(query: query)
    }
}
