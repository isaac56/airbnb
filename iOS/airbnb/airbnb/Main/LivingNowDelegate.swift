//
//  LivingNowDelegate.swift
//  airbnb
//
//  Created by Issac on 2021/05/25.
//

import UIKit

class LivingNowDelegate: NSObject {
    private let mainSceneViewModel: MainSceneViewModel
    private let livingNowCollectionView: UICollectionView
    
    init(mainSceneViewModel: MainSceneViewModel, livingNowCollectionView: UICollectionView) {
        self.mainSceneViewModel = mainSceneViewModel
        self.livingNowCollectionView = livingNowCollectionView
    }
}

extension LivingNowDelegate: UICollectionViewDelegateFlowLayout {
    func collectionView(_ collectionView: UICollectionView, layout collectionViewLayout: UICollectionViewLayout, sizeForItemAt indexPath: IndexPath) -> CGSize {
        return CGSize(width: 250, height: 350)
    }
}

extension LivingNowDelegate: UICollectionViewDelegate {
    func scrollViewWillEndDragging(_ scrollView: UIScrollView, withVelocity velocity: CGPoint, targetContentOffset: UnsafeMutablePointer<CGPoint>) {
        guard let collectionViewFlowLayout = self.livingNowCollectionView.collectionViewLayout as? UICollectionViewFlowLayout else { return }
        let cellWidth = 250 + collectionViewFlowLayout.minimumLineSpacing
        
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
    }
}

extension LivingNowDelegate: UICollectionViewDataSource {
    func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
        return self.mainSceneViewModel.cityNames.count
    }
    
    func collectionView(_ collectionView: UICollectionView, cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {
        guard let cell = collectionView.dequeueReusableCell(withReuseIdentifier: LivingNowSectionCell.className, for: indexPath) as? LivingNowSectionCell else { return UICollectionViewCell() }
        cell.configure(imageName: self.mainSceneViewModel.livingNowImageNames[indexPath.row],
                       description: self.mainSceneViewModel.livingNowDescrptionBook[indexPath.row])
        return cell
    }
    
    
}
