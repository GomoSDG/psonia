(ns psonia.config
  (:require [cprop.source :as source]
            [mount.core :refer [defstate]]))

(defstate config :start (merge
                         (:psonia (source/from-env))
                         (source/from-file "config.edn")))
