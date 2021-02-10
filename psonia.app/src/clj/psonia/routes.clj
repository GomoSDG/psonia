(ns psonia.routes)

(def routes [["/"  {:get (fn [req]
                           {:status 200
                            :body   {:message "Healthyyy"}})}]
             ["/users" {:get  (fn [req]
                                {:status 200
                                 :body   {:message "Hello, world!"}})
                        :post (fn [req]
                                {:status 200
                                 :body   {:created true}})}]])
