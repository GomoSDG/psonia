(ns psonia.storage.s3
  (:require [cognitect.aws.client.api :as aws]
            [mount.core :refer [defstate]]
            [psonia.config :refer [config]]))

(defstate region :start (get-in config [:s3 :region]))

(defstate bucket :start (get-in config [:s3 :bucket-name]))

(def client (aws/client {:api :s3
                         :region region}))

(defn put-object [key file]
  {:op      :PutObject
   :request {:Bucket bucket
             :Key    key
             :Body   file}})

(defn get-object [key]
  {:op      :GetObject
   :request {:Bucket bucket
             :Key    key}})
