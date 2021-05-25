//
//  ViewController.swift
//  airbnb
//
//  Created by Issac on 2021/05/17.
//

import UIKit

class MainViewController: UIViewController {
    @IBOutlet weak var arroundSectionCollectionView: UICollectionView!
    @IBOutlet weak var livingNowCollectionView: UICollectionView!
    @IBOutlet private weak var searchBar: UISearchBar!
    @IBOutlet weak var scrollviewHeight: NSLayoutConstraint!
    private var mainSceneViewModel: MainSceneViewModel!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        self.removeBorderOfSearchBar()
        self.registerNib()
        self.mainSceneViewModel = MainSceneViewModel()
        self.setLivingNowCollectionViewDelegate()
        self.setDecelerationRate()
        self.setScrollViewHeight()
    }
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        self.navigationItem.title = "메인 화면"
        self.navigationController?.setNavigationBarHidden(true, animated: false)
    }
    
    private func setScrollViewHeight() {
        self.scrollviewHeight.constant = self.livingNowCollectionView.frame.maxY + 20
    }
    
    private func setLivingNowCollectionViewDelegate() {
        let livingNowDelegate = LivingNowDelegate(mainSceneViewModel: self.mainSceneViewModel, livingNowCollectionView: self.livingNowCollectionView)
        self.livingNowCollectionView.delegate = livingNowDelegate
        self.livingNowCollectionView.dataSource = livingNowDelegate
    }
    
    private func setDecelerationRate() {
        self.arroundSectionCollectionView.decelerationRate = .fast
    }
    
    private func registerNib() {
        let arroundSectionNib = UINib(nibName: ArroundSectionCell.className, bundle: nil)
        self.arroundSectionCollectionView.register(arroundSectionNib, forCellWithReuseIdentifier: ArroundSectionCell.className)
        let livingNowSectionNib = UINib(nibName: LivingNowSectionCell.className, bundle: nil)
        self.arroundSectionCollectionView.register(livingNowSectionNib, forCellWithReuseIdentifier: LivingNowSectionCell.className)
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

extension MainViewController: UICollectionViewDelegateFlowLayout {
    func collectionView(_ collectionView: UICollectionView, layout collectionViewLayout: UICollectionViewLayout, sizeForItemAt indexPath: IndexPath) -> CGSize {
        return CGSize(width: self.view.bounds.width / 1.4, height: 90)
    }
}

extension MainViewController: UICollectionViewDelegate {
    func scrollViewWillEndDragging(_ scrollView: UIScrollView, withVelocity velocity: CGPoint, targetContentOffset: UnsafeMutablePointer<CGPoint>) {
        guard let collectionViewFlowLayout = self.arroundSectionCollectionView.collectionViewLayout as? UICollectionViewFlowLayout else { return }
        // CollectionView Item Size
        let cellWidth = self.view.bounds.width / 1.4 + collectionViewFlowLayout.minimumLineSpacing
        
        var offset = targetContentOffset.pointee
        
        // 이동한 x좌표 값과 item의 크기를 비교 후 페이징 값 설정
        let index = (offset.x + scrollView.contentInset.left) / cellWidth
        var roundedIndex = round(index)
      
        // 스크롤 방향 체크
        // item 절반 사이즈 만큼 스크롤로 판단하여 올림, 내림 처리
        if scrollView.contentOffset.x > targetContentOffset.pointee.x {
            roundedIndex = floor(index)
        } else {
            roundedIndex = ceil(index)
        }
        
        // 위 코드를 통해 페이징 될 좌표 값을 targetContentOffset에 대입
        offset = CGPoint(x: roundedIndex * cellWidth - scrollView.contentInset.left, y: -scrollView.contentInset.top)
        targetContentOffset.pointee = offset
    }
}

extension MainViewController: UICollectionViewDataSource {
    func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
        return self.mainSceneViewModel.cityNames.count
    }
    
    func collectionView(_ collectionView: UICollectionView, cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {
        guard let cell = collectionView.dequeueReusableCell(withReuseIdentifier: ArroundSectionCell.className, for: indexPath) as? ArroundSectionCell else { return UICollectionViewCell() }
        let imageName = self.mainSceneViewModel.cityImageNames[indexPath.row]
        let cityName = self.mainSceneViewModel.cityNames[indexPath.row]
        let time = self.mainSceneViewModel.distanceDescriptionBook[indexPath.row]
        cell.configure(cityImageName: imageName, cityName: cityName, distance: time)
        return cell
    }
    
    
}
