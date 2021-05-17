//
//  NetworkingCenter.swift
//  airbnb
//
//  Created by Issac on 2021/05/17.
//

import Foundation
import Alamofire

final class NetworkingCenter {
    func request<T: Decodable>(path: AirbnbRouter, completion: @escaping ((Result<T, AFError>) -> ())) {
        AF.request(path)
            .validate(statusCode: 200..<300)
            .responseDecodable(of: T.self, queue: .main) { (response) in
                switch response.result {
                case .success(let result):
                    completion(.success(result))
                case .failure(let error):
                    completion(.failure(error))
                }
            }
    }
}
