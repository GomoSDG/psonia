(ns psonia.users.core
  (:require [psonia.users.cognito :as cognito]
            [cognitect.aws.client.api :as aws]))

(defn create-user! [username]
  (aws/invoke cognito/client (cognito/create-default-user username)))

(defn delete-user! [username]
  (aws/invoke cognito/client (cognito/delete-user username)))

(defn create-group! [name]
  (aws/invoke cognito/client (cognito/create-group name)))

(defn add-user-to-group! [username group-name]
  (aws/invoke cognito/client (cognito/add-user-to-group username group-name)))


