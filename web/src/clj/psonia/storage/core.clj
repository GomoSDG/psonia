(ns psonia.storage.core
  (:require [cognitect.aws.credentials :as creds]
            [cognitect.aws.client.api :as aws]
            [psonia.storage.s3 :as s3]
            [psonia.config :refer [config]]
            [mount.core :refer [defstate]]))

(defn put-object! [path file]
  (aws/invoke s3/client (s3/put-object path file)))

(defn get-object [path]
  (aws/invoke s3/client (s3/put-object path)))

