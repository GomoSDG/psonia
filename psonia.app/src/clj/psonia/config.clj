(ns psonia.config
  (:require [cprop.source :as source]
            [mount.core :refer [defstate]]))

(def default-config {:port 3080
                     :s3   {:bucket-name "my-buck-2-3-1"
                            :region      "af-south-1"}})

(defstate config :start (merge
                         (if (.exists (io/file "config.edn"))
                           (source/from-file "config.edn")
                           {})
                         (:psonia (source/from-env))))
