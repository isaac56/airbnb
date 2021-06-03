//
//  NetworkingCenter.swift
//  airbnb
//
//  Created by Issac on 2021/05/17.
//

import Foundation
import Alamofire
import SwiftyJSON

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
    
    func request(query: String, completion: @escaping ((Result<SearchResult, AFError>) -> ())) {
        let url = "https://dapi.kakao.com/v2/local/search/address.json"
        let parameters = ["query": query]
        let headers: HTTPHeaders = ["Authorization": "KakaoAK d3e878a1f79282780e1352824479f2b6"]
        AF.request(url, parameters: parameters, headers: headers).validate().responseDecodable(of: SearchResult.self, completionHandler: { (response) in
            switch response.result {
            case .success(let searchResult):
                completion(.success(searchResult))
            case .failure(let error):
                completion(.failure(error))
            }
        })
    }
}
