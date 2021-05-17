//
//  AirbnbRouter.swift
//  airbnb
//
//  Created by Issac on 2021/05/17.
//
import Foundation
import Alamofire

public enum AirbnbRouter: URLRequestConvertible {
    enum Constants {
        static let baseURLPath = getHost()
        static let authenticationToken = "Bearer xxx"
        
        static func getHost() -> String {
            guard let path = Bundle.main.path(forResource: "NetworkElements", ofType: "plist") else { return "" }
            let plist = NSDictionary(contentsOfFile: path)
            guard let key = plist?.object(forKey: "Host") as? String else { return "" }
            return key
        }
    }
    
    case login
    case main
    
    var path: String {
        switch self {
        case .login:
            return "/login"
        case .main:
            return "/"
        }
    }
    
    var parameter: [String: Any] {
        switch self {
        default:
            return [:]
        }
    }
    
    var method: HTTPMethod {
        switch self {
        case .login:
            return .post
        default:
            return .get
        }
    }
    
    public func asURLRequest() throws -> URLRequest {
        let url = try Constants.baseURLPath.asURL()
        var request = URLRequest(url: url.appendingPathComponent(path))
        request.httpMethod = method.rawValue
        request.setValue(Constants.authenticationToken, forHTTPHeaderField: "Authorization")
        request.timeoutInterval = TimeInterval(10 * 1000)
        
        return try URLEncoding.default.encode(request, with: parameter)
    }
}

extension AirbnbRouter {
    private func getHOST() -> String {
        guard let path = Bundle.main.path(forResource: "NetworkElements", ofType: "plist") else { return "" }
        let plist = NSDictionary(contentsOfFile: path)
        guard let key = plist?.object(forKey: "Host") as? String else { return "" }
        return key
    }
}
