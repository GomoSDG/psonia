(ns psonia.core
  (:require [reitit.ring :as ring]
            [org.httpkit.server :as server]))

(defonce server (atom nil))

(def app
  (ring/ring-handler
   (ring/router
    [["/"  {:get (fn [req]
                   {:status 200
                    :body {:message "Healthy"}})}]
     ["/users" {:get (fn [req]
                       {:status 200
                        :body {:message "Hello, world!"}})
                :post (fn [req]
                        {:status 200
                         :body {:created true}})}]])))

(defn start-server []
  (when (nil? @server)
    (reset! server (server/run-server #'app {:port 3000}))))


(defn stop-server []
  (when-not (nil? @server)
    (@server)))
