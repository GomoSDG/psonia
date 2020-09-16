(ns psonia.users.cognito
  (:require [cognitect.aws.credentials :as creds]
            [cognitect.aws.client.api :as aws]))

(def credentials (creds/default-credentials-provider (aws/default-http-client)))

(def cognito (aws/client {:api :cognito-idp}))
