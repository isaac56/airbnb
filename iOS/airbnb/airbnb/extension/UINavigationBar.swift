//
//  UINavigationBar.swift
//  airbnb
//
//  Created by Issac on 2021/05/20.
//

import UIKit

extension UINavigationBar {
    func shouldRemoveShadow(_ value: Bool) {
        if value {
            self.setValue(true, forKey: "hidesShadow")
        } else {
            self.setValue(false, forKey: "hidesShadow")
        }
    }
}
