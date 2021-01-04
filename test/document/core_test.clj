(ns document.core-test
  (:require [cheshire.core :as cheshire]
            [clojure.test :refer :all]
            [document.handler :refer :all]
            [ring.mock.request :as mock]))

(defn parse-body [body]
  (cheshire/parse-string (slurp body) true))

(deftest a-test

  (testing "Test GET request to /hello?name={a-name} returns expected response"
    (let [response (app (-> (mock/request :get "/api/plus?x=1&y=2")))
          body (parse-body (:body response))]
      (is (= (:status response) 200))
      (is (= (:result body) 3)))))

(deftest test-get-documents

  (testing "Test GET of documents"
    (let [response (app (-> (mock/request :get "/api/documents?id=123")))
          body (parse-body (:body response))]
      (is (= (:status response) 200))
      (is (= (:id body) 123)))
    (let [response (app (-> (mock/request :post "/api/documents")
                            (mock/json-body {:id 123})))
          body (parse-body (:body response))]
      (is (= (:status response) 201))
      (is (= (:id body) 123)))
    ))