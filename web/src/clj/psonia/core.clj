(ns psonia.core
  (:require [reitit.ring :as ring]
            [org.httpkit.server :as server]
            [mount.core :refer [defstate]]
            [psonia.config :refer [config]]
            [psonia.routes :refer [routes]]))

(defstate app :start (ring/ring-handler (ring/router routes)))

(defstate server :start (server/run-server app {:port (:psonia-port config)})
                 :stop (server))
