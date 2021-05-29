//
//  PriceViewController.swift
//  airbnb
//
//  Created by Issac on 2021/05/20.
//

import UIKit
import MultiSlider

class PriceViewController: UIViewController {
    private var selectInfo: SelectInfo!
    private var slider: MultiSlider!
    private var priceDataProcessingCenter: PriceDataProcessingCenter!
    @IBOutlet weak var priceRangeLabel: UILabel!
    @IBOutlet weak var averagePriceLabel: UILabel!
    

    override func viewDidLoad() {
        super.viewDidLoad()
        self.navigationItem.title = "숙소 찾기"
        self.priceDataProcessingCenter = PriceDataProcessingCenter(minPriceValue: 10_000, maxPriceValue: 1_000_000)
        self.passingSearchInfoToChildViewController()
        self.makeMultiSlider()
        self.bind()
    }
    
    private func bind() {
        guard let bottomSearchInfoVC = self.children[0] as? BottomSearchInfoViewController else { return }
        self.priceDataProcessingCenter.pricePassingHandler = { priceText in
            self.selectInfo.setPrice(min: self.priceDataProcessingCenter.minPrice, max: self.priceDataProcessingCenter.maxPrice, text: priceText)
            self.priceRangeLabel.text = priceText
            bottomSearchInfoVC.setWhetherEnableButtonOrNot(isWillEnable: true)
            bottomSearchInfoVC.writePriceLabel(of: priceText)
        }
        
        bottomSearchInfoVC.clearHandler = {
            self.priceDataProcessingCenter.initRowPrice()
            bottomSearchInfoVC.setWhetherEnableButtonOrNot(isWillEnable: false)
            self.slider.value = [CGFloat(self.priceDataProcessingCenter.minPrice), CGFloat(self.priceDataProcessingCenter.maxPrice)]
            self.priceRangeLabel.text = " "
        }

        bottomSearchInfoVC.nextHandler = {
            guard let vc = self.storyboard?.instantiateViewController(identifier: PersonViewController.className) as? PersonViewController else { return }
            vc.setSelectInfo(self.selectInfo)
            self.navigationController?.pushViewController(vc, animated: true)
        }
    }
    
    private func makeMultiSlider() {
        slider = MultiSlider()
        slider.minimumValue = CGFloat(self.priceDataProcessingCenter.minPriceValue)
        slider.maximumValue = CGFloat(self.priceDataProcessingCenter.maxPriceValue)
        slider.value = [slider.minimumValue, slider.maximumValue]
        slider.snapStepSize = 1000
        slider.isVertical = false
        slider.outerTrackColor = .systemGray5
        slider.trackWidth = 8
        
        slider.tintColor = .darkGray
        var thumbImage = UIImage(systemName: "pause.circle")
        thumbImage = thumbImage?.resizeImage(targetSize: CGSize(width: 32, height: 32))
        slider.thumbImage = thumbImage
        slider.addTarget(self, action: #selector(sliderDragEnded(_:)), for: .valueChanged) // sent when drag ends
        self.view.addSubview(slider)
        
        slider.translatesAutoresizingMaskIntoConstraints = false
        slider.widthAnchor.constraint(equalTo: self.view.widthAnchor, multiplier: 0.8).isActive = true
        slider.heightAnchor.constraint(equalToConstant: 150).isActive = true
        slider.topAnchor.constraint(equalTo: self.averagePriceLabel.bottomAnchor, constant: 100).isActive = true
        slider.centerXAnchor.constraint(equalTo: self.view.centerXAnchor).isActive = true
    }
    
    @objc func sliderDragEnded(_ sender: MultiSlider) {
        self.priceDataProcessingCenter.setRowPrice(prices: sender.value.map({ Int($0) }))
    }
    
    func setSelectInfo(_ info: SelectInfo) {
        self.selectInfo = info
    }
    
    private func passingSearchInfoToChildViewController() {
        guard let bottomSearchInfoVC = self.children[0] as? BottomSearchInfoViewController else { return }
        bottomSearchInfoVC.configureSearchInfo(self.selectInfo)
    }
}
