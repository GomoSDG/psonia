(ns psonia.users.cognito
  (:require [cognitect.aws.credentials :as creds]
            [cognitect.aws.client.api :as aws]
            [psonia.config :refer [config]]
            [mount.core :refer [defstate]]))

(defstate user-pool-id :start (:psonia-user-pool-id config))

(defstate user-pool-region :start (:psonia-user-pool-region config))

(def client (aws/client {:api :cognito-idp
                         :region user-pool-region}))

(def credentials (creds/default-credentials-provider (aws/default-http-client)))

(defn create-default-user [username]
  {:op      :AdminCreateUser
   :request {:UserPoolId user-pool-id
             :Username   username}})

(defn delete-user [username]
  {:op      :AdminDeleteUser
   :request {:UserPoolId user-pool-id
             :Username   username}})

(defn add-user-to-group [username group]
  {:op      :AdminAddUserToGroup
   :request {:Username   username
             :GroupName  group
             :UserPoolId user-pool-id}})

(defn create-group [name]
  {:op      :CreateGroup
   :request {:GroupName  name
             :UserPoolId user-pool-id}})

(defn remove-user-from-group [username group]
  {:op      :AdminRemoveUserFromGroup
   :request {:Username   username
             :GroupName  group
             :UserPoolId user-pool-id}})


