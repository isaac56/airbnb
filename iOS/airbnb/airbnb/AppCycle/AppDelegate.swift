//
//  AppDelegate.swift
//  airbnb
//
//  Created by Issac on 2021/05/17.
//

import UIKit

@main
class AppDelegate: UIResponder, UIApplicationDelegate {
    var window: UIWindow?
    var coordinator: Coordinator?
    
    func application(_ application: UIApplication, didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey: Any]?) -> Bool {
        if #available(iOS 13, *) { } else {
            let navController = UINavigationController()
            coordinator = MainCoordinator(navigationController: navController)
            coordinator?.start()

            window = UIWindow(frame: UIScreen.main.bounds)
            window?.rootViewController = navController
            window?.makeKeyAndVisible()
        }
        return true
    }
}

