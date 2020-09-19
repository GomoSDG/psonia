(ns psonia.core
  (:require [reitit.ring :as ring]
            [org.httpkit.server :as server]
            [mount.core :refer [defstate start]]
            [psonia.config :refer [config]]
            [psonia.routes :refer [routes]])
  (:gen-class))

(defstate app :start (ring/ring-handler (ring/router routes)))

(defstate server
  :start (server/run-server app {:port (:port config)})
  :stop (server))

(defn -main [& args]
  (println "Starting psonia on port: " (:psonia-port config))
  (start))
