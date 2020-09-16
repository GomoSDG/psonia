(ns psonia.config
  (:require [cprop.core :refer [load-config]]
            [cprop.source :as source]))

(def config (load-config
             :merge
             [(source/from-env)]))



