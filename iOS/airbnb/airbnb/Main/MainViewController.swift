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
    @IBOutlet weak var scrollviewHeight: NSLayoutConstraint!
    @IBOutlet weak var arroundTextLabel: CustomTitleLabel!
    @IBOutlet weak var livingNowTextLabel: CustomTitleLabel!
    private var livingNowViewModel: LivingNowViewModel!
    private var cityInfoViewModel: CityInfoViewModel!
    private var livingNowDelegate: LivingNowDelegate!
    private let searchController = UISearchController(searchResultsController: nil)
    
    override func viewDidLoad() {
        super.viewDidLoad()
        self.livingNowViewModel = LivingNowViewModel()
        self.cityInfoViewModel = CityInfoViewModel()
        self.setLivingNowCollectionViewDelegate()
        self.registerNib()
        self.setDecelerationRate()
        self.setScrollViewHeight()
        
    }
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        self.navigationItem.title = "메인 화면"
        self.navigationController?.navigationItem.searchController?.hidesNavigationBarDuringPresentation = true
        self.navigationController?.setNavigationBarHidden(true, animated: false)
    }
    
    private func setScrollViewHeight() {
        self.scrollviewHeight.constant = self.livingNowCollectionView.frame.maxY + 20
    }
    
    private func setLivingNowCollectionViewDelegate() {
        self.livingNowDelegate = LivingNowDelegate(livingNowViewModel: self.livingNowViewModel, livingNowCollectionView: self.livingNowCollectionView)
        self.livingNowCollectionView.delegate = self.livingNowDelegate
        self.livingNowCollectionView.dataSource = self.livingNowDelegate
    }
    
    private func setDecelerationRate() {
        self.arroundSectionCollectionView.decelerationRate = .fast
        self.livingNowCollectionView.decelerationRate = .fast
    }
    
    private func registerNib() {
        let arroundSectionNib = UINib(nibName: ArroundSectionCell.className, bundle: nil)
        self.arroundSectionCollectionView.register(arroundSectionNib, forCellWithReuseIdentifier: ArroundSectionCell.className)
        let livingNowSectionNib = UINib(nibName: LivingNowSectionCell.className, bundle: nil)
        self.livingNowCollectionView.register(livingNowSectionNib, forCellWithReuseIdentifier: LivingNowSectionCell.className)
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
        return self.cityInfoViewModel.cityNames.count
    }
    
    func collectionView(_ collectionView: UICollectionView, cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {
        guard let cell = collectionView.dequeueReusableCell(withReuseIdentifier: ArroundSectionCell.className, for: indexPath) as? ArroundSectionCell else { return UICollectionViewCell() }
        let imageName = self.cityInfoViewModel.cityImageNames[indexPath.row]
        let cityName = self.cityInfoViewModel.cityNames[indexPath.row]
        let time = self.cityInfoViewModel.distanceDescriptionBook[indexPath.row]
        cell.configure(cityImageName: imageName, cityName: cityName, distance: time)
        return cell
    }
    
    
}
