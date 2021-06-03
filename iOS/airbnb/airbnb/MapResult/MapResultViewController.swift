//
//  MapResultViewController.swift
//  airbnb
//
//  Created by Issac on 2021/06/02.
//

import UIKit


class MapResultViewController: UIViewController {
    var mapView: MTMapView!
    override func viewDidLoad() {
        super.viewDidLoad()

        mapView = MTMapView(frame: self.view.bounds)
        mapView.delegate = self
        mapView.baseMapType = .standard
        self.view.addSubview(mapView)
    }
    

}


extension MapResultViewController: MTMapViewDelegate {
    
}
