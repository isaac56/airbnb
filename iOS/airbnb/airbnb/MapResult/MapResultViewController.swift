//
//  MapResultViewController.swift
//  airbnb
//
//  Created by Issac on 2021/06/02.
//

import UIKit
import Combine

class MapResultViewController: UIViewController {
    @IBOutlet var resultListCollectionView: UICollectionView!
    @IBOutlet var hamburgerButton: UIButton!
    private(set) var mapView: MTMapView!
    private(set) var selectInfo: SelectInfo!
    private(set) var mapResultViewModel: MapResultViewModel!
    private var cancelBag = Set<AnyCancellable>()
    override func viewDidLoad() {
        super.viewDidLoad()
        self.navigationController?.setNavigationBarHidden(true, animated: false)
        self.mapResultViewModel = MapResultViewModel(selectInfo: selectInfo)
        self.makeMapView()
        self.registerNib()
        self.resultListCollectionView.decelerationRate = .fast
        self.bind()
        self.mapResultViewModel.requestAccommodation()
        self.setHamburgerButton()
    }
    
    private func setHamburgerButton() {
        self.hamburgerButton.layer.cornerRadius = self.hamburgerButton.frame.width / 2
        self.hamburgerButton.layer.masksToBounds = false
        self.hamburgerButton.layer.shadowColor = UIColor.black.cgColor
        self.hamburgerButton.layer.shadowOffset = CGSize(width: 1, height: 1)
        self.hamburgerButton.layer.shadowOpacity = 0.5
        self.hamburgerButton.layer.shadowRadius = 2
    }
    
    func registerNib() {
        let nib = UINib(nibName: ResultListCollectionViewCell.className, bundle: nil)
        self.resultListCollectionView.register(nib, forCellWithReuseIdentifier: ResultListCollectionViewCell.className)
    }
    
    func makeMapView() {
        mapView = MTMapView(frame: self.view.bounds)
        mapView.delegate = self
        mapView.baseMapType = .standard
        self.view.insertSubview(mapView, at: 0)
    }
    
    func bind() {
        self.mapResultViewModel.$searchByRegion.sink { (searchByRegions) in
            self.setMapCenter(searchByRegions: searchByRegions)
            self.makePoiItems(searchByRegions: searchByRegions)
            self.resultListCollectionView.reloadData()
        }.store(in: &cancelBag)
    }
    
    func setMapCenter(searchByRegions: [SearchByRegion]) {
        guard let region = searchByRegions.first else { return }
        mapView.setMapCenter(MTMapPoint(geoCoord: MTMapPointGeo(latitude: region.y, longitude: region.x)), animated: true)
    }
    
    func makePoiItems(searchByRegions: [SearchByRegion]) {
        var poiItems = [MTMapPOIItem]()
        for region in searchByRegions {
            let poiItemCustomImage = makePoiItemCustomImage(price: region.stringTotalFee())
            let item = MTMapPOIItem()
            item.itemName = region.name
            item.mapPoint = MTMapPoint(geoCoord: MTMapPointGeo(latitude: region.y, longitude: region.x))
            item.markerType = .customImage
            item.customImage = poiItemCustomImage
            item.customSelectedImage = poiItemCustomImage
            item.showAnimationType = .dropFromHeaven
            poiItems.append(item)
        }
        mapView.addPOIItems(poiItems)
        mapView.fitAreaToShowAllPOIItems()
    }
    
    func makePoiItemCustomImage(price: String) -> UIImage {
        let label = UILabel(frame: CGRect(x: 0, y: 0, width: 80, height: 25))
        label.text = price
        label.backgroundColor = .white
        label.layer.cornerRadius = label.frame.width / 15
        label.layer.masksToBounds = false
        
        label.layer.borderWidth = 1
        label.layer.borderColor = UIColor.black.cgColor
        
        label.font = UIFont.systemFont(ofSize: 14)
        label.textAlignment = .center
        let image = UIImage.imageWithLabel(label: label)
        return image
    }
    
    func setSelectInfo(_ info: SelectInfo) {
        self.selectInfo = info
    }
}

extension MapResultViewController: MTMapViewDelegate {
    func mapView(_ mapView: MTMapView!, selectedPOIItem poiItem: MTMapPOIItem!) -> Bool {
        mapView.setMapCenter(poiItem.mapPoint, animated: true)
        return false
    }
}

extension MapResultViewController: UICollectionViewDataSource {
    func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
        return self.mapResultViewModel.searchByRegion.count
    }
    
    func collectionView(_ collectionView: UICollectionView, cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {
        guard let cell = collectionView.dequeueReusableCell(withReuseIdentifier: ResultListCollectionViewCell.className, for: indexPath) as? ResultListCollectionViewCell else { return UICollectionViewCell() }
        let region = self.mapResultViewModel.searchByRegion[indexPath.row]
        cell.roomTitle.text = region.name
        cell.priceLabel.text = self.mapResultViewModel.stringDailyFeeForResultCell(region: region)
        guard let imageData = try? Data(contentsOf: URL(string: region.titleImageURL)!) else { return cell }
        cell.roomImage.image = UIImage(data: imageData)
        return cell
    }
}

extension MapResultViewController: UICollectionViewDelegateFlowLayout {
    func collectionView(_ collectionView: UICollectionView, layout collectionViewLayout: UICollectionViewLayout, sizeForItemAt indexPath: IndexPath) -> CGSize {
        return CGSize(width: self.view.frame.width - 40, height: 140)
    }
}

extension MapResultViewController: UICollectionViewDelegate {
    func scrollViewWillEndDragging(_ scrollView: UIScrollView, withVelocity velocity: CGPoint, targetContentOffset: UnsafeMutablePointer<CGPoint>) {
        guard let collectionViewFlowLayout = self.resultListCollectionView.collectionViewLayout as? UICollectionViewFlowLayout else { return }
        let cellWidth = self.view.bounds.width - 40 + collectionViewFlowLayout.minimumLineSpacing
        
        var offset = targetContentOffset.pointee
        
        let index = (offset.x + scrollView.contentInset.left) / cellWidth
        var roundedIndex = round(index)
      
        if scrollView.contentOffset.x > targetContentOffset.pointee.x {
            roundedIndex = floor(index)
        } else {
            roundedIndex = ceil(index)
        }
        
        offset = CGPoint(x: roundedIndex * cellWidth - scrollView.contentInset.left, y: -scrollView.contentInset.top)
        targetContentOffset.pointee = offset
        
        let region = self.mapResultViewModel.searchByRegion[Int(roundedIndex)]
        mapView.setMapCenter(MTMapPoint(geoCoord: MTMapPointGeo(latitude: region.y, longitude: region.x)), animated: true)
    }
}
