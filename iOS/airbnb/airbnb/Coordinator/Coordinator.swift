//
//  Coordinator.swift
//  airbnb
//
//  Created by Issac on 2021/05/17.
//

import UIKit

protocol Coordinator {
    var childCoordinators: [Coordinator] { get set }
    var navigationController: UINavigationController { get set }
    
    func start()
}

class MainCoordinator: Coordinator {
    var childCoordinators = [Coordinator]()
    var navigationController: UINavigationController
    
    init(navigationController: UINavigationController) {
        self.navigationController = navigationController
    }
    
    func start() {
//        let mainVC = MainViewController.instantiate()
//        mainVC.coordinator = self
//        navigationController.setViewControllers([mainVC], animated: false)
        showCalendar()
    }
    
    func showSearch() {
        let searchVC = SearchViewController.instantiate()
        searchVC.coordinator = self
        navigationController.pushViewController(searchVC, animated: true)
    }
    
    func showAuth() {
        let authVC = UIStoryboard(name: "Main", bundle: Bundle.main).instantiateViewController(identifier: MainViewController.className) as! MainViewController //MARK: - 수정 필요
        authVC.coordinator = self
        navigationController.setViewControllers([authVC], animated: true)
    }
    
    func showCalendar() {
        let calendarVC = UIStoryboard(name: "Main", bundle: Bundle.main).instantiateViewController(identifier: CalendarViewController.className) as! CalendarViewController //MARK: - 팝업이니 수정 필요
        calendarVC.coordinator = self
        navigationController.pushViewController(calendarVC, animated: true)
    }
}

//extension MainCoordinator: MainViewControllerDelegate {
//    func didLogOut() {
//        User.logout()
//        showAuth()
//    }
//}
