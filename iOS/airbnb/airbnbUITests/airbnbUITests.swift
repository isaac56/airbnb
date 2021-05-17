//
//  airbnbUITests.swift
//  airbnbUITests
//
//  Created by Issac on 2021/05/17.
//

import XCTest

class airbnbUITests: XCTestCase {
    var app: XCUIApplication!
    override func setUpWithError() throws {
        try super.setUpWithError()
        continueAfterFailure = false
        app = XCUIApplication()
        app.launch()
    }

    func testLaunch() {
    }
}
